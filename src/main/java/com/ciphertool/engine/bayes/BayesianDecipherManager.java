/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.bayes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.nevec.rjm.BigDecimalMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.task.TaskExecutor;

import com.ciphertool.engine.dao.CipherDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.Ciphertext;
import com.ciphertool.sherlock.MathConstants;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class BayesianDecipherManager {
	private Logger							log							= LoggerFactory.getLogger(getClass());

	private static final List<Character>	LOWERCASE_LETTERS			= Arrays.asList(new Character[] { 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z' });

	private String							cipherName;
	private PlaintextEvaluator				plaintextEvaluator;
	private CipherDao						cipherDao;
	private Cipher							cipher;
	private MarkovModel						letterMarkovModel;
	private int								samplerIterations;
	private double							sourceModelPrior;
	private BigDecimal						alphaHyperparameter;
	private double							channelModelPrior;
	private BigDecimal						betaHyperparameter;
	private int								annealingTemperatureMax;
	private int								annealingTemperatureMin;
	private int								cipherKeySize;
	private static List<LetterProbability>	letterUnigramProbabilities	= new ArrayList<>();
	private BigDecimal						ciphertextProbability;
	private KnownPlaintextEvaluator			knownPlaintextEvaluator;
	private TaskExecutor					taskExecutor;

	@PostConstruct
	public void setUp() {
		alphaHyperparameter = BigDecimal.valueOf(sourceModelPrior);
		betaHyperparameter = BigDecimal.valueOf(channelModelPrior);

		this.cipher = cipherDao.findByCipherName(cipherName);
		plaintextEvaluator.setCipher(cipher);

		cipherKeySize = (int) cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().count();
		// Use a uniform distribution for ciphertext probabilities
		ciphertextProbability = BigDecimal.ONE.divide(BigDecimal.valueOf(cipherKeySize), MathConstants.PREC_10_HALF_UP);

		for (Map.Entry<Character, NGramIndexNode> entry : letterMarkovModel.getRootNode().getTransitions().entrySet()) {
			letterUnigramProbabilities.add(new LetterProbability(entry.getKey(),
					entry.getValue().getTerminalInfo().getConditionalProbability()));
		}

		// Initialize the solution key
		CipherSolution initialSolution = new CipherSolution(cipher, cipherKeySize);

		RouletteSampler<LetterProbability> rouletteSampler = new RouletteSampler<>();
		rouletteSampler.reIndex(letterUnigramProbabilities);

		cipher.getCiphertextCharacters().stream().map(ciphertext -> ciphertext.getValue()).distinct().forEach(ciphertext -> {
			// Pick a plaintext at random according to the language model
			String nextPlaintext = letterUnigramProbabilities.get(rouletteSampler.getNextIndex(letterUnigramProbabilities)).getValue().toString();

			initialSolution.putMapping(ciphertext, new Plaintext(nextPlaintext));
		});

		EvaluationResults score = plaintextEvaluator.evaluate(initialSolution);
		initialSolution.setProbability(score.getProbability());
		initialSolution.setLogProbability(score.getLogProbability());

		BigDecimal maxTemp = BigDecimal.valueOf(annealingTemperatureMax);
		BigDecimal minTemp = BigDecimal.valueOf(annealingTemperatureMin);
		BigDecimal iterations = BigDecimal.valueOf(samplerIterations);
		BigDecimal temperature;

		CipherSolution next = initialSolution;
		CipherSolution maxBayes = null;
		int maxBayesIteration = 0;
		Double maxKnown = 0.0;
		int maxKnownIteration = 0;

		log.info("Running Gibbs sampler for " + samplerIterations + " iterations.");
		long start = System.currentTimeMillis();

		Double knownProximity = null;
		int i;
		for (i = 0; i < samplerIterations; i++) {
			long iterationStart = System.currentTimeMillis();

			/*
			 * Set temperature as a ratio of the max temperature to the number of iterations left, offset by the min
			 * temperature so as not to go below it
			 */
			temperature = maxTemp.subtract(minTemp, MathConstants.PREC_10_HALF_UP).multiply(iterations.subtract(BigDecimal.valueOf(i), MathConstants.PREC_10_HALF_UP).divide(iterations, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP).add(minTemp, MathConstants.PREC_10_HALF_UP);

			next = runGibbsSampler(temperature, next);

			if (knownPlaintextEvaluator != null) {
				knownProximity = knownPlaintextEvaluator.evaluate(next);

				if (maxKnown < knownProximity) {
					maxKnown = knownProximity;
					maxKnownIteration = i + 1;
				}
			}

			if (maxBayes == null || maxBayes.getProbability().compareTo(next.getProbability()) < 0) {
				maxBayes = next;
				maxBayesIteration = i + 1;
			}

			log.info("Iteration " + (i + 1) + " complete.  [elapsed=" + (System.currentTimeMillis() - iterationStart)
					+ "ms, temp=" + String.format("%1$,.2f", temperature) + ", proximity="
					+ String.format("%1$,.2f", knownProximity) + "]");
		}

		log.info("Gibbs sampling completed in " + (System.currentTimeMillis() - start) + "ms.  [average="
				+ ((double) (System.currentTimeMillis() - start) / (double) i) + "ms, bestKnownProximityIter="
				+ maxKnownIteration + ", bestKnownProximity=" + String.format("%1$,.2f", maxKnown)
				+ ", bestProbabilityIter=" + maxBayesIteration + ", bestProbability= " + maxBayes.getProbability()
				+ "]");
		log.info(next.toString());
	}

	protected CipherSolution runGibbsSampler(BigDecimal temperature, CipherSolution solution) {
		BigDecimal acceptanceProbability = null;
		RouletteSampler<LetterProbability> rouletteSampler = new RouletteSampler<>();

		// For each cipher symbol type, run the gibbs sampling
		for (Map.Entry<String, Plaintext> entry : solution.getMappings().entrySet()) {
			List<LetterProbability> plaintextDistribution = efficientlyComputeDistribution(entry.getKey(), solution);

			rouletteSampler.reIndex(plaintextDistribution);

			Character proposedLetter = plaintextDistribution.get(rouletteSampler.getNextIndex(plaintextDistribution)).getValue();

			CipherSolution proposedSolution = solution.clone();
			proposedSolution.replaceMapping(entry.getKey(), new Plaintext(proposedLetter.toString()));
			EvaluationResults score = plaintextEvaluator.evaluate(proposedSolution);
			proposedSolution.setProbability(score.getProbability());
			proposedSolution.setLogProbability(score.getLogProbability());

			/*
			 * For now, we're not doing anything to prevent the same mapping from being chosen
			 * 
			 * The log probability is not really interpolated accurately, so we do the comparison on the real
			 * probability, and we use the log probability for acceptance probability calculation
			 */
			if (proposedSolution.getLogProbability().compareTo(solution.getLogProbability()) > 0) {
				log.debug("Better solution found");
				solution = proposedSolution;
			} else {
				// Need to convert to log probabilities in order for the acceptance probability calculation to be useful
				acceptanceProbability = BigDecimalMath.exp(solution.getLogProbability().subtract(proposedSolution.getLogProbability(), MathConstants.PREC_10_HALF_UP).divide(temperature, MathConstants.PREC_10_HALF_UP).negate());

				log.debug("Acceptance probability: {}", acceptanceProbability);

				if (acceptanceProbability.compareTo(BigDecimal.ZERO) < 0) {
					throw new IllegalStateException(
							"Acceptance probability was calculated to be less than zero.  Please review the math as this should not happen.");
				}

				if (acceptanceProbability.compareTo(BigDecimal.ONE) > 0
						|| ThreadLocalRandom.current().nextDouble() < acceptanceProbability.doubleValue()) {
					solution = proposedSolution;
				}
			}
		}

		return solution;
	}

	protected List<LetterProbability> computeDistribution(String ciphertextKey, CipherSolution solution) {
		List<LetterProbability> plaintextDistribution = new ArrayList<>();
		String lastCharacter = null;
		BigDecimal sumOfProbabilities = BigDecimal.ZERO;

		// Calculate the full conditional probability for each possible plaintext substitution
		for (Character letter : LOWERCASE_LETTERS) {
			BigDecimal jointProbabilityNumerator = BigDecimal.ONE;
			BigDecimal jointProbabilityDenominator = BigDecimal.ONE;
			BigDecimal numerator;
			BigDecimal denominator;
			String currentCharacter = "";
			CiphertextMapping ciphertextMapping;
			Map<String, BigDecimal> unigramCounts = new HashMap<>();
			Map<String, BigDecimal> nGramCounts = new HashMap<>();
			Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts = new HashMap<>();

			for (Ciphertext ciphertext : cipher.getCiphertextCharacters()) {
				lastCharacter = currentCharacter;

				if (ciphertext.getValue().equals(ciphertextKey)) {
					currentCharacter = letter.toString();
				} else {
					currentCharacter = solution.getMappings().get(ciphertext.getValue()).getValue();
				}

				ciphertextMapping = new CiphertextMapping(ciphertext.getValue(), new Plaintext(currentCharacter));

				if (lastCharacter.isEmpty()) {
					jointProbabilityNumerator = jointProbabilityNumerator.multiply(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(new LetterProbability(
							currentCharacter.charAt(0),
							BigDecimal.ZERO))).getProbability(), MathConstants.PREC_10_HALF_UP).multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP);

					if (!ciphertext.getValue().equals(ciphertextKey)) {
						jointProbabilityDenominator = jointProbabilityDenominator.multiply(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(new LetterProbability(
								currentCharacter.charAt(0),
								BigDecimal.ZERO))).getProbability(), MathConstants.PREC_10_HALF_UP).multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP);
					}
				} else {
					BigDecimal nGramPriorProbability = letterMarkovModel.findLongest(lastCharacter
							+ currentCharacter).getTerminalInfo().getConditionalProbability();
					// Any sufficient corpus should contain every possible bigram, so no need to check for unknowns
					BigDecimal unigramCount = unigramCounts.get(lastCharacter);
					BigDecimal nGramCount = nGramCounts.get(lastCharacter + currentCharacter);
					numerator = alphaHyperparameter.multiply(nGramPriorProbability, MathConstants.PREC_10_HALF_UP).add((nGramCount == null ? BigDecimal.ZERO : nGramCount), MathConstants.PREC_10_HALF_UP);
					denominator = alphaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

					// Multiply by the source model probability
					jointProbabilityNumerator = jointProbabilityNumerator.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);

					if (!ciphertext.getValue().equals(ciphertextKey)) {
						jointProbabilityDenominator = jointProbabilityDenominator.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);
					}

					BigDecimal ciphertextMappingCount = ciphertextMappingCounts.get(ciphertextMapping);
					unigramCount = unigramCounts.get(currentCharacter);
					numerator = betaHyperparameter.multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP).add((ciphertextMappingCount == null ? BigDecimal.ZERO : ciphertextMappingCount), MathConstants.PREC_10_HALF_UP);
					denominator = betaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

					// Multiply by the channel model probability
					jointProbabilityNumerator = jointProbabilityNumerator.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);

					if (!ciphertext.getValue().equals(ciphertextKey)) {
						jointProbabilityDenominator = jointProbabilityDenominator.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);
					}

					if (nGramCounts.get(lastCharacter + currentCharacter) == null) {
						nGramCounts.put(lastCharacter + currentCharacter, BigDecimal.ZERO);
					}

					nGramCounts.put(currentCharacter, nGramCounts.get(lastCharacter
							+ currentCharacter).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));
				}

				if (ciphertextMappingCounts.get(ciphertextMapping) == null) {
					ciphertextMappingCounts.put(ciphertextMapping, BigDecimal.ZERO);
				}

				ciphertextMappingCounts.put(ciphertextMapping, ciphertextMappingCounts.get(ciphertextMapping).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));

				if (unigramCounts.get(currentCharacter) == null) {
					unigramCounts.put(currentCharacter, BigDecimal.ZERO);
				}

				unigramCounts.put(currentCharacter, unigramCounts.get(currentCharacter).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));
			}

			plaintextDistribution.add(new LetterProbability(letter,
					jointProbabilityNumerator.divide(jointProbabilityDenominator, MathConstants.PREC_10_HALF_UP)));
			sumOfProbabilities = sumOfProbabilities.add(jointProbabilityNumerator.divide(jointProbabilityDenominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);
		}

		for (LetterProbability letterProbability : plaintextDistribution) {
			letterProbability.setProbability(letterProbability.getProbability().divide(sumOfProbabilities, MathConstants.PREC_10_HALF_UP));
		}

		return plaintextDistribution;
	}

	protected List<LetterProbability> efficientlyComputeDistribution(String ciphertextKey, CipherSolution solution) {
		List<LetterProbability> plaintextDistribution = new ArrayList<>();
		String lastCharacter = null;
		BigDecimal sumOfProbabilities = BigDecimal.ZERO;
		BigDecimal partialProbability;
		BigDecimal jointProbabilityDenominator = BigDecimal.ONE;
		BigDecimal numerator;
		BigDecimal denominator;
		String currentCharacter = "";
		CiphertextMapping ciphertextMapping;
		Map<String, BigDecimal> unigramCounts = new HashMap<>();
		Map<String, BigDecimal> nGramCounts = new HashMap<>();
		Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts = new HashMap<>();
		List<BigDecimal> partialProbabilities = new ArrayList<>();

		for (Ciphertext ciphertext : cipher.getCiphertextCharacters()) {
			partialProbability = BigDecimal.ONE;
			lastCharacter = currentCharacter;

			currentCharacter = solution.getMappings().get(ciphertext.getValue()).getValue();

			ciphertextMapping = new CiphertextMapping(ciphertext.getValue(), new Plaintext(currentCharacter));

			if (lastCharacter.isEmpty()) {
				partialProbability = partialProbability.multiply(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(new LetterProbability(
						currentCharacter.charAt(0),
						BigDecimal.ZERO))).getProbability(), MathConstants.PREC_10_HALF_UP).multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP);
			} else {
				BigDecimal nGramPriorProbability = letterMarkovModel.findLongest(lastCharacter
						+ currentCharacter).getTerminalInfo().getConditionalProbability();
				// Any sufficient corpus should contain every possible bigram, so no need to check for unknowns
				BigDecimal unigramCount = unigramCounts.get(lastCharacter);
				BigDecimal nGramCount = nGramCounts.get(lastCharacter + currentCharacter);
				numerator = alphaHyperparameter.multiply(nGramPriorProbability, MathConstants.PREC_10_HALF_UP).add((nGramCount == null ? BigDecimal.ZERO : nGramCount), MathConstants.PREC_10_HALF_UP);
				denominator = alphaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

				// Multiply by the source model probability
				partialProbability = partialProbability.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);

				BigDecimal ciphertextMappingCount = ciphertextMappingCounts.get(ciphertextMapping);
				unigramCount = unigramCounts.get(currentCharacter);
				numerator = betaHyperparameter.multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP).add((ciphertextMappingCount == null ? BigDecimal.ZERO : ciphertextMappingCount), MathConstants.PREC_10_HALF_UP);
				denominator = betaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

				// Multiply by the channel model probability
				partialProbability = partialProbability.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);

				if (nGramCounts.get(lastCharacter + currentCharacter) == null) {
					nGramCounts.put(lastCharacter + currentCharacter, BigDecimal.ZERO);
				}

				nGramCounts.put(currentCharacter, nGramCounts.get(lastCharacter
						+ currentCharacter).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));
			}

			partialProbabilities.add(partialProbability);

			// Don't include in the joint probability if this is the cipher type we are sampling for
			if (!ciphertext.getValue().equals(ciphertextKey)) {
				jointProbabilityDenominator = jointProbabilityDenominator.multiply(partialProbability, MathConstants.PREC_10_HALF_UP);
			}

			if (ciphertextMappingCounts.get(ciphertextMapping) == null) {
				ciphertextMappingCounts.put(ciphertextMapping, BigDecimal.ZERO);
			}

			ciphertextMappingCounts.put(ciphertextMapping, ciphertextMappingCounts.get(ciphertextMapping).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));

			if (unigramCounts.get(currentCharacter) == null) {
				unigramCounts.put(currentCharacter, BigDecimal.ZERO);
			}

			unigramCounts.put(currentCharacter, unigramCounts.get(currentCharacter).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));
		}

		List<FutureTask<LetterProbability>> futures = new ArrayList<>(26);
		FutureTask<LetterProbability> task;

		// Calculate the full conditional probability for each possible plaintext substitution
		for (Character letter : LOWERCASE_LETTERS) {
			task = new FutureTask<>(new JointProbabilityTask(solution, letter, ciphertextKey,
					jointProbabilityDenominator, unigramCounts, nGramCounts, ciphertextMappingCounts));
			futures.add(task);
			this.taskExecutor.execute(task);
		}

		for (FutureTask<LetterProbability> future : futures) {
			try {
				LetterProbability letterProbability = future.get();
				plaintextDistribution.add(letterProbability);
				sumOfProbabilities = sumOfProbabilities.add(letterProbability.getProbability(), MathConstants.PREC_10_HALF_UP);
			} catch (InterruptedException ie) {
				log.error("Caught InterruptedException while waiting for JointProbabilityTask ", ie);
			} catch (ExecutionException ee) {
				log.error("Caught ExecutionException while waiting for JointProbabilityTask ", ee);
			}
		}

		for (LetterProbability letterProbability : plaintextDistribution) {
			letterProbability.setProbability(letterProbability.getProbability().divide(sumOfProbabilities, MathConstants.PREC_10_HALF_UP));
		}

		return plaintextDistribution;
	}

	protected LetterProbability computeJointDistributionAsync(CipherSolution solution, Character letter, String ciphertextKey, BigDecimal jointProbabilityDenominator, Map<String, BigDecimal> unigramCounts, Map<String, BigDecimal> nGramCounts, Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts) {
		BigDecimal partialProbability;
		String lastCharacter = null;
		BigDecimal numerator = null;
		BigDecimal denominator = null;
		String currentCharacter = "";
		CiphertextMapping ciphertextMapping = null;
		// unigramCounts = new HashMap<>(); // TODO: need to deep copy
		// nGramCounts = new HashMap<>(); // TODO: need to deep copy
		// ciphertextMappingCounts = new HashMap<>(); // TODO: need to deep copy
		BigDecimal jointProbabilityNumerator = jointProbabilityDenominator;

		for (Ciphertext ciphertext : cipher.getCiphertextCharacters()) {
			partialProbability = BigDecimal.ONE;
			lastCharacter = currentCharacter;

			currentCharacter = solution.getMappings().get(ciphertext.getValue()).getValue();

			ciphertextMapping = new CiphertextMapping(ciphertext.getValue(), new Plaintext(currentCharacter));

			// Only include in the joint probability if this is the cipher type we are sampling for
			if (ciphertext.getValue().equals(ciphertextKey)) {
				if (lastCharacter.isEmpty()) {
					partialProbability = partialProbability.multiply(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(new LetterProbability(
							currentCharacter.charAt(0),
							BigDecimal.ZERO))).getProbability(), MathConstants.PREC_10_HALF_UP).multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP);
				} else {
					BigDecimal nGramPriorProbability = letterMarkovModel.findLongest(lastCharacter
							+ currentCharacter).getTerminalInfo().getConditionalProbability();
					// Any sufficient corpus should contain every possible bigram, so no need to check for unknowns
					BigDecimal unigramCount = unigramCounts.get(lastCharacter);
					BigDecimal nGramCount = nGramCounts.get(lastCharacter + currentCharacter);
					numerator = alphaHyperparameter.multiply(nGramPriorProbability, MathConstants.PREC_10_HALF_UP).add((nGramCount == null ? BigDecimal.ZERO : nGramCount), MathConstants.PREC_10_HALF_UP);
					denominator = alphaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

					// Multiply by the source model probability
					partialProbability = partialProbability.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);

					BigDecimal ciphertextMappingCount = ciphertextMappingCounts.get(ciphertextMapping);
					unigramCount = unigramCounts.get(currentCharacter);
					numerator = betaHyperparameter.multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP).add((ciphertextMappingCount == null ? BigDecimal.ZERO : ciphertextMappingCount), MathConstants.PREC_10_HALF_UP);
					denominator = betaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

					// Multiply by the channel model probability
					partialProbability = partialProbability.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);

					// TODO: update counts in an incremental fashion
					// if (nGramCounts.get(lastCharacter + currentCharacter) == null) {
					// nGramCounts.put(lastCharacter + currentCharacter, BigDecimal.ZERO);
					// }
					//
					// nGramCounts.put(currentCharacter, nGramCounts.get(lastCharacter
					// + currentCharacter).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));
				}

				jointProbabilityNumerator = jointProbabilityNumerator.multiply(partialProbability, MathConstants.PREC_10_HALF_UP);

				// TODO: update counts in an incremental fashion
				// if (ciphertextMappingCounts.get(ciphertextMapping) == null) {
				// ciphertextMappingCounts.put(ciphertextMapping, BigDecimal.ZERO);
				// }
				//
				// ciphertextMappingCounts.put(ciphertextMapping,
				// ciphertextMappingCounts.get(ciphertextMapping).add(BigDecimal.ONE, MathConstants.PREC_10_HALF_UP));
				//
				// if (unigramCounts.get(currentCharacter) == null) {
				// unigramCounts.put(currentCharacter, BigDecimal.ZERO);
				// }
				//
				// unigramCounts.put(currentCharacter, unigramCounts.get(currentCharacter).add(BigDecimal.ONE,
				// MathConstants.PREC_10_HALF_UP));
			}
		}

		return new LetterProbability(letter,
				jointProbabilityNumerator.divide(jointProbabilityDenominator, MathConstants.PREC_10_HALF_UP));
	}

	/**
	 * A concurrent task for calculating the joint probability.
	 */
	protected class JointProbabilityTask implements Callable<LetterProbability> {
		private CipherSolution						solution;
		private Character							letter;
		private String								ciphertextKey;
		private BigDecimal							jointProbabilityDenominator;
		private Map<String, BigDecimal>				unigramCounts;
		private Map<String, BigDecimal>				nGramCounts;
		private Map<CiphertextMapping, BigDecimal>	ciphertextMappingCounts;

		public JointProbabilityTask(CipherSolution solution, Character letter, String ciphertextKey,
				BigDecimal jointProbabilityDenominator, Map<String, BigDecimal> unigramCounts,
				Map<String, BigDecimal> nGramCounts, Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts) {
			this.solution = solution;
			this.letter = letter;
			this.ciphertextKey = ciphertextKey;
			this.jointProbabilityDenominator = jointProbabilityDenominator;
			this.unigramCounts = unigramCounts;
			this.nGramCounts = nGramCounts;
			this.ciphertextMappingCounts = ciphertextMappingCounts;
		}

		@Override
		public LetterProbability call() throws Exception {
			return computeJointDistributionAsync(solution, letter, ciphertextKey, jointProbabilityDenominator, unigramCounts, nGramCounts, ciphertextMappingCounts);
		}
	}

	/**
	 * @param plaintextEvaluator
	 *            the plaintextEvaluator to set
	 */
	@Required
	public void setPlaintextEvaluator(PlaintextEvaluator plaintextEvaluator) {
		this.plaintextEvaluator = plaintextEvaluator;
	}

	/**
	 * @param cipherName
	 *            the cipherName to set
	 */
	@Required
	public void setCipherName(String cipherName) {
		this.cipherName = cipherName;
	}

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		this.cipherDao = cipherDao;
	}

	/**
	 * @param letterMarkovModel
	 *            the letterMarkovModel to set
	 */
	@Required
	public void setLetterMarkovModel(MarkovModel letterMarkovModel) {
		this.letterMarkovModel = letterMarkovModel;
	}

	/**
	 * @param samplerIterations
	 *            the samplerIterations to set
	 */
	@Required
	public void setSamplerIterations(int samplerIterations) {
		this.samplerIterations = samplerIterations;
	}

	/**
	 * @param sourceModelPrior
	 *            the sourceModelPrior to set
	 */
	@Required
	public void setSourceModelPrior(double sourceModelPrior) {
		this.sourceModelPrior = sourceModelPrior;
	}

	/**
	 * @param channelModelPrior
	 *            the channelModelPrior to set
	 */
	@Required
	public void setChannelModelPrior(double channelModelPrior) {
		this.channelModelPrior = channelModelPrior;
	}

	/**
	 * @param annealingTemperatureMax
	 *            the annealingTemperatureMax to set
	 */
	@Required
	public void setAnnealingTemperatureMax(int annealingTemperatureMax) {
		this.annealingTemperatureMax = annealingTemperatureMax;
	}

	/**
	 * @param annealingTemperatureMin
	 *            the annealingTemperatureMin to set
	 */
	@Required
	public void setAnnealingTemperatureMin(int annealingTemperatureMin) {
		this.annealingTemperatureMin = annealingTemperatureMin;
	}

	/**
	 * @param taskExecutor
	 *            the taskExecutor to set
	 */
	@Required
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * This is NOT required. We will not always know the solution. In fact, that should be the rare case.
	 * 
	 * @param knownPlaintextEvaluator
	 *            the knownPlaintextEvaluator to set
	 */
	public void setKnownPlaintextEvaluator(KnownPlaintextEvaluator knownPlaintextEvaluator) {
		this.knownPlaintextEvaluator = knownPlaintextEvaluator;
	}
}
