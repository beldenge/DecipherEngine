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
		int totalCharacters = this.cipher.getCiphertextCharacters().size();
		int lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));

		// Remove the last row altogether
		for (int i = lastRowBegin; i < totalCharacters; i++) {
			this.cipher.removeCiphertextCharacter(this.cipher.getCiphertextCharacters().get(lastRowBegin));
		}

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
		Double wordBoundaryProbability = (double) 1.0 / (double) LanguageConstants.AVERAGE_WORD_SIZE;

		cipher.getCiphertextCharacters().stream().map(ciphertext -> ciphertext.getValue()).distinct().forEach(ciphertext -> {
			// Pick a plaintext at random according to the language model
			String nextPlaintext = letterUnigramProbabilities.get(rouletteSampler.getNextIndex(letterUnigramProbabilities)).getValue().toString();

			initialSolution.putMapping(ciphertext, new Plaintext(nextPlaintext));
		});

		for (int i = 0; i < cipher.getCiphertextCharacters().size() - 1; i++) {
			if (ThreadLocalRandom.current().nextDouble() < wordBoundaryProbability) {
				initialSolution.addWordBoundary(i);
			}
		}

		computeDerivationProbability(null, null, null, initialSolution);

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

			next = runGibbsLetterSampler(temperature, next);
			next = runGibbsWordBoundarySampler(temperature, next);

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
					+ String.format("%1$,.2f", knownProximity) + ", probability=" + next.getProbability() + "]");
		}

		log.info("Gibbs sampling completed in " + (System.currentTimeMillis() - start) + "ms.  [average="
				+ ((double) (System.currentTimeMillis() - start) / (double) i) + "ms, bestKnownProximityIter="
				+ maxKnownIteration + ", bestKnownProximity=" + String.format("%1$,.2f", maxKnown)
				+ ", bestProbabilityIter=" + maxBayesIteration + ", bestProbability= " + maxBayes.getProbability()
				+ "]");
		log.info(next.toString());
	}

	protected CipherSolution runGibbsLetterSampler(BigDecimal temperature, CipherSolution solution) {
		RouletteSampler<SolutionProbability> rouletteSampler = new RouletteSampler<>();
		CipherSolution next = solution.clone();
		CipherSolution proposal = null;

		// For each cipher symbol type, run the gibbs sampling
		for (Map.Entry<String, Plaintext> entry : solution.getMappings().entrySet()) {
			int affectedCount = moveAffectedWindowsToEnd(entry.getKey(), next);

			List<SolutionProbability> plaintextDistribution = computeDistribution(affectedCount, entry.getKey(), next);

			rouletteSampler.reIndex(plaintextDistribution);

			proposal = plaintextDistribution.get(rouletteSampler.getNextIndex(plaintextDistribution)).getValue();

			next = selectNext(temperature, next, proposal);

			// Reset to the original cipher, since it was modified by moveAffectedWindowsToEnd()
			next.setCipher(this.cipher);
		}

		return solution;
	}

	protected List<SolutionProbability> computeDistribution(int affectedCount, String ciphertextKey, CipherSolution solution) {
		List<SolutionProbability> plaintextDistribution = new ArrayList<>();
		BigDecimal sumOfProbabilities = BigDecimal.ZERO;
		CipherSolution conditionalSolution = null;
		PartialDerivation partialDerivation = computePartialDerivationProbability(affectedCount, ciphertextKey, solution);

		// Calculate the full conditional probability for each possible plaintext substitution
		for (Character letter : LOWERCASE_LETTERS) {
			conditionalSolution = solution.clone();
			conditionalSolution.replaceMapping(ciphertextKey, new Plaintext(letter.toString()));

			computeDerivationProbability(partialDerivation, affectedCount, ciphertextKey, conditionalSolution);

			plaintextDistribution.add(new SolutionProbability(conditionalSolution,
					conditionalSolution.getProbability()));
			sumOfProbabilities = sumOfProbabilities.add(conditionalSolution.getProbability(), MathConstants.PREC_10_HALF_UP);
		}

		// Normalize the probabilities
		// TODO: should we also somehow normalize the log probabilities?
		for (SolutionProbability solutionProbability : plaintextDistribution) {
			solutionProbability.setProbability(solutionProbability.getProbability().divide(sumOfProbabilities, MathConstants.PREC_10_HALF_UP));
		}

		return plaintextDistribution;
	}

	protected PartialDerivation computePartialDerivationProbability(int affectedCount, String ciphertextKey, CipherSolution derivation) {
		BigDecimal productOfProbabilities = BigDecimal.ONE;
		BigDecimal sumOfProbabilities = BigDecimal.ZERO;
		Map<String, BigDecimal> unigramCounts = new HashMap<>();
		Map<String, BigDecimal> bigramCounts = new HashMap<>();
		Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts = new HashMap<>();
		String lastCharacter = null;
		String ciphertext = null;
		PartialProbabilities partialProbabilities;

		for (int i = 0; i < derivation.getCipher().getCiphertextCharacters().size() - affectedCount; i++) {
			ciphertext = derivation.getCipher().getCiphertextCharacters().get(i).getValue();

			partialProbabilities = computePosition(productOfProbabilities, sumOfProbabilities, unigramCounts, bigramCounts, ciphertextMappingCounts, lastCharacter, ciphertext, derivation);

			productOfProbabilities = partialProbabilities.getProductOfProbabilities();
			sumOfProbabilities = partialProbabilities.getSumOfProbabilities();

			lastCharacter = derivation.getMappings().get(ciphertext).getValue();
		}

		return new PartialDerivation(productOfProbabilities, sumOfProbabilities, unigramCounts, bigramCounts,
				ciphertextMappingCounts);
	}

	protected void computeDerivationProbability(PartialDerivation partialDerivation, Integer affectedCount, String ciphertextKey, CipherSolution derivation) {
		BigDecimal productOfProbabilities = (partialDerivation == null ? BigDecimal.ONE : partialDerivation.getProductOfProbabilities());
		BigDecimal sumOfProbabilities = (partialDerivation == null ? BigDecimal.ZERO : partialDerivation.getSumOfProbabilities());
		Map<String, BigDecimal> unigramCounts = (partialDerivation == null ? new HashMap<>() : new HashMap<>(
				partialDerivation.getUnigramCounts()));
		Map<String, BigDecimal> bigramCounts = (partialDerivation == null ? new HashMap<>() : new HashMap<>(
				partialDerivation.getBigramCounts()));
		Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts = (partialDerivation == null ? new HashMap<>() : new HashMap<>(
				partialDerivation.getCiphertextMappingCounts()));
		String lastCharacter = null;
		EvaluationResults derivationResults = null;
		String ciphertext = null;
		PartialProbabilities partialProbabilities;
		int start = (affectedCount == null ? 0 : derivation.getCipher().getCiphertextCharacters().size()
				- affectedCount);

		for (int i = start; i < derivation.getCipher().getCiphertextCharacters().size(); i++) {
			ciphertext = derivation.getCipher().getCiphertextCharacters().get(i).getValue();

			partialProbabilities = computePosition(productOfProbabilities, sumOfProbabilities, unigramCounts, bigramCounts, ciphertextMappingCounts, lastCharacter, ciphertext, derivation);

			productOfProbabilities = partialProbabilities.getProductOfProbabilities();
			sumOfProbabilities = partialProbabilities.getSumOfProbabilities();

			lastCharacter = derivation.getMappings().get(ciphertext).getValue();
		}

		derivationResults = plaintextEvaluator.evaluate(ciphertextKey, derivation);

		// Multiply by the prior to satisfy bayes' rule
		productOfProbabilities = productOfProbabilities.multiply(derivationResults.getProbability(), MathConstants.PREC_10_HALF_UP);
		sumOfProbabilities = sumOfProbabilities.add(derivationResults.getLogProbability(), MathConstants.PREC_10_HALF_UP);

		derivation.setProbability(productOfProbabilities);
		derivation.setLogProbability(sumOfProbabilities);
	}

	protected PartialProbabilities computePosition(BigDecimal productOfProbabilities, BigDecimal sumOfProbabilities, Map<String, BigDecimal> unigramCounts, Map<String, BigDecimal> bigramCounts, Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts, String lastCharacter, String ciphertext, CipherSolution derivation) {
		String currentCharacter = derivation.getMappings().get(ciphertext).getValue();

		CiphertextMapping ciphertextMapping = new CiphertextMapping(ciphertext, new Plaintext(currentCharacter));

		if (lastCharacter == null) {
			productOfProbabilities = productOfProbabilities.multiply(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(new LetterProbability(
					currentCharacter.charAt(0),
					BigDecimal.ZERO))).getProbability(), MathConstants.PREC_10_HALF_UP).multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP);

			sumOfProbabilities = sumOfProbabilities.add(BigDecimalMath.log(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(new LetterProbability(
					currentCharacter.charAt(0),
					BigDecimal.ZERO))).getProbability()), MathConstants.PREC_10_HALF_UP).add(ciphertextProbability, MathConstants.PREC_10_HALF_UP);
		} else {
			BigDecimal nGramPriorProbability = letterMarkovModel.findLongest(lastCharacter
					+ currentCharacter).getTerminalInfo().getConditionalProbability();
			// Any sufficient corpus should contain every possible bigram, so no need to check for unknowns
			BigDecimal unigramCount = unigramCounts.get(lastCharacter);
			BigDecimal nGramCount = bigramCounts.get(lastCharacter + currentCharacter);
			BigDecimal numerator = alphaHyperparameter.multiply(nGramPriorProbability, MathConstants.PREC_10_HALF_UP).add((nGramCount == null ? BigDecimal.ZERO : nGramCount), MathConstants.PREC_10_HALF_UP);
			BigDecimal denominator = alphaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

			// Multiply by the source model probability
			productOfProbabilities = productOfProbabilities.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);
			sumOfProbabilities = sumOfProbabilities.add(BigDecimalMath.log(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP)), MathConstants.PREC_10_HALF_UP);

			BigDecimal ciphertextMappingCount = ciphertextMappingCounts.get(ciphertextMapping);
			unigramCount = unigramCounts.get(currentCharacter);
			numerator = betaHyperparameter.multiply(ciphertextProbability, MathConstants.PREC_10_HALF_UP).add((ciphertextMappingCount == null ? BigDecimal.ZERO : ciphertextMappingCount), MathConstants.PREC_10_HALF_UP);
			denominator = betaHyperparameter.add((unigramCount == null ? BigDecimal.ZERO : unigramCount), MathConstants.PREC_10_HALF_UP);

			// Multiply by the channel model probability
			productOfProbabilities = productOfProbabilities.multiply(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);
			sumOfProbabilities = sumOfProbabilities.add(BigDecimalMath.log(numerator.divide(denominator, MathConstants.PREC_10_HALF_UP)), MathConstants.PREC_10_HALF_UP);

			if (bigramCounts.get(lastCharacter + currentCharacter) == null) {
				bigramCounts.put(lastCharacter + currentCharacter, BigDecimal.ZERO);
			}

			bigramCounts.put(lastCharacter + currentCharacter, bigramCounts.get(lastCharacter
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

		return new PartialProbabilities(productOfProbabilities, sumOfProbabilities);
	}

	protected CipherSolution runGibbsWordBoundarySampler(BigDecimal temperature, CipherSolution solution) {
		int nextBoundary;
		BigDecimal sumOfLogProbabilities = null;
		List<BoundaryProbability> boundaryProbabilities = null;
		boolean isAddBoundary = false;
		CipherSolution addProposal = null;
		CipherSolution removeProposal = null;
		CipherSolution proposal = null;

		for (int i = 0; i < cipher.getCiphertextCharacters().size() - 1; i++) {
			sumOfLogProbabilities = null;
			boundaryProbabilities = new ArrayList<>();
			nextBoundary = i;

			addProposal = solution.clone();
			addProposal.addWordBoundary(nextBoundary);
			computeDerivationProbability(null, null, null, addProposal);

			removeProposal = solution.clone();
			removeProposal.removeWordBoundary(nextBoundary);
			computeDerivationProbability(null, null, null, removeProposal);

			sumOfLogProbabilities = addProposal.getLogProbability().add(removeProposal.getLogProbability());

			boundaryProbabilities.add(new BoundaryProbability(true,
					addProposal.getLogProbability().divide(sumOfLogProbabilities, MathConstants.PREC_10_HALF_UP)));
			boundaryProbabilities.add(new BoundaryProbability(false,
					removeProposal.getLogProbability().divide(sumOfLogProbabilities, MathConstants.PREC_10_HALF_UP)));

			RouletteSampler<BoundaryProbability> rouletteSampler = new RouletteSampler<>();
			rouletteSampler.reIndex(boundaryProbabilities);

			isAddBoundary = boundaryProbabilities.get(rouletteSampler.getNextIndex(boundaryProbabilities)).getValue();

			if (isAddBoundary) {
				proposal = addProposal;
			} else {
				proposal = removeProposal;
			}

			solution = selectNext(temperature, solution, proposal);
		}

		return solution;
	}

	protected CipherSolution selectNext(BigDecimal temperature, CipherSolution solution, CipherSolution proposal) {
		BigDecimal acceptanceProbability = null;

		if (proposal.getLogProbability().compareTo(solution.getLogProbability()) > 0) {
			log.debug("Better solution found");
			return proposal;
		} else {
			// Need to convert to log probabilities in order for the acceptance probability calculation to be useful
			acceptanceProbability = BigDecimalMath.exp(solution.getLogProbability().subtract(proposal.getLogProbability(), MathConstants.PREC_10_HALF_UP).divide(temperature, MathConstants.PREC_10_HALF_UP).negate());

			log.debug("Acceptance probability: {}", acceptanceProbability);

			if (acceptanceProbability.compareTo(BigDecimal.ZERO) < 0) {
				throw new IllegalStateException(
						"Acceptance probability was calculated to be less than zero.  Please review the math as this should not happen.");
			}

			if (acceptanceProbability.compareTo(BigDecimal.ONE) > 0
					|| ThreadLocalRandom.current().nextDouble() < acceptanceProbability.doubleValue()) {
				return proposal;
			}
		}

		return solution;
	}

	protected int moveAffectedWindowsToEnd(String ciphertextKey, CipherSolution solution) {
		Cipher cloned = cipher.clone();
		solution.setCipher(cloned);

		if (ciphertextKey == null) {
			// Nothing to do
			return 0;
		}

		List<Ciphertext> allWindows = new ArrayList<>();

		Integer begin = 0;
		boolean affected = false;

		for (int i = 0; i < cloned.getCiphertextCharacters().size(); i++) {
			if (ciphertextKey.equals(cloned.getCiphertextCharacters().get(i).getValue())) {
				affected = true;
			}

			if (solution.getWordBoundaries().contains(i)) {
				if (affected) {
					for (int j = begin; j <= i; j++) {
						allWindows.add(cloned.getCiphertextCharacters().get(j));
					}
				}

				begin = i + 1;
				affected = false;
			}
		}

		if (affected) {
			for (int j = begin; j < cloned.getCiphertextCharacters().size(); j++) {
				allWindows.add(cloned.getCiphertextCharacters().get(j));
			}
		}

		for (int i = 0; i < allWindows.size(); i++) {
			// Remove it
			cloned.removeCiphertextCharacter(allWindows.get(i));

			// And add it to the end of the List
			cloned.addCiphertextCharacter(allWindows.get(i));
		}

		return allWindows.size();
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
