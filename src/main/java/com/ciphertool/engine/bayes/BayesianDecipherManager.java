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
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.dao.CipherDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.Ciphertext;
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
	// Use a uniform distribution for ciphertext probabilities
	BigDecimal								ciphertextProbability		= BigDecimal.ONE.divide(BigDecimal.valueOf(cipherKeySize), MathContext.DECIMAL128);

	@PostConstruct
	public void setUp() {
		alphaHyperparameter = BigDecimal.valueOf(sourceModelPrior);
		betaHyperparameter = BigDecimal.valueOf(channelModelPrior);

		this.cipher = cipherDao.findByCipherName(cipherName);

		cipherKeySize = (int) cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().count();

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

		initialSolution.setScore(plaintextEvaluator.evaluate(initialSolution));

		BigDecimal maxTemp = BigDecimal.valueOf(annealingTemperatureMax);
		BigDecimal minTemp = BigDecimal.valueOf(annealingTemperatureMin);
		BigDecimal iterations = BigDecimal.valueOf(samplerIterations);
		BigDecimal temperature;

		CipherSolution next = initialSolution;

		for (int i = 0; i < samplerIterations; i++) {
			/*
			 * Set temperature as a ratio of the max temperature to the number of iterations left, offset by the min
			 * temperature so as not to go below it
			 */
			temperature = maxTemp.subtract(minTemp).multiply(iterations.subtract(BigDecimal.valueOf(i)).divide(iterations, MathContext.DECIMAL128)).add(minTemp);

			next = runGibbsSampler(temperature, next);
		}

		log.info("Solution: " + next);
	}

	protected CipherSolution runGibbsSampler(BigDecimal temperature, CipherSolution solution) {
		BigDecimal acceptanceProbability;
		RouletteSampler<LetterProbability> rouletteSampler = new RouletteSampler<>();

		// For each cipher symbol type, run the gibbs sampling
		for (Map.Entry<String, Plaintext> entry : solution.getMappings().entrySet()) {
			List<LetterProbability> plaintextDistribution = computeDistribution(entry.getKey(), solution);

			rouletteSampler.reIndex(plaintextDistribution);

			Character proposedLetter = plaintextDistribution.get(rouletteSampler.getNextIndex(plaintextDistribution)).getValue();

			CipherSolution proposedSolution = solution.clone();
			proposedSolution.getMappings().put(entry.getKey(), new Plaintext(proposedLetter.toString()));
			proposedSolution.setScore(plaintextEvaluator.evaluate(proposedSolution));

			// For now, we're not doing anything if the same mapping is chosen
			if (proposedSolution.getScore().compareTo(solution.getScore()) > 1) {
				solution = proposedSolution;
			} else {
				// Need to convert to log probabilities in order for the acceptance probability calculation to be useful
				// TODO: use a better implementation of exponent that does not lose precision
				acceptanceProbability = BigDecimal.valueOf(Math.exp(convertToLogProbability(solution.getScore()).subtract(convertToLogProbability(proposedSolution.getScore())).divide(temperature, MathContext.DECIMAL128).negate().doubleValue()));

				if (ThreadLocalRandom.current().nextDouble() < acceptanceProbability.doubleValue()) {
					solution = proposedSolution;
				}
			}
		}

		return solution;
	}

	protected List<LetterProbability> computeDistribution(String ciphertextKey, CipherSolution solution) {
		List<LetterProbability> plaintextDistribution = new ArrayList<>();
		String lastCharacter = null;

		// Calculate the full conditional probability for each possible plaintext substitution
		for (Character letter : LOWERCASE_LETTERS) {
			BigDecimal fullConditionalProbability = BigDecimal.ONE;
			BigDecimal numerator;
			BigDecimal denominator;
			String currentCharacter = "";
			CiphertextMapping ciphertextMapping;
			Map<String, BigDecimal> unigramCounts = new HashMap<>();
			Map<String, BigDecimal> bigramCounts = new HashMap<>();
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
					fullConditionalProbability = fullConditionalProbability.multiply(letterUnigramProbabilities.get(letterUnigramProbabilities.indexOf(lastCharacter.charAt(0))).getProbability()).multiply(ciphertextProbability);

					continue;
				}

				BigDecimal bigramPriorProbability = letterMarkovModel.findLongest(lastCharacter
						+ currentCharacter).getTerminalInfo().getConditionalProbability();
				// Any sufficient corpus should contain every possible bigram, so no need to check for unknowns
				BigDecimal unigramCount = unigramCounts.get(lastCharacter);
				BigDecimal bigramCount = bigramCounts.get(lastCharacter + currentCharacter);
				numerator = alphaHyperparameter.multiply(bigramPriorProbability).add(bigramCount == null ? BigDecimal.ZERO : bigramCount);
				denominator = alphaHyperparameter.add(unigramCount == null ? BigDecimal.ZERO : unigramCount);

				// Multiply by the source model probability
				fullConditionalProbability = fullConditionalProbability.multiply(numerator.divide(denominator, MathContext.DECIMAL128));

				BigDecimal ciphertextMappingCount = ciphertextMappingCounts.get(ciphertextMapping);
				unigramCount = unigramCounts.get(currentCharacter);
				numerator = betaHyperparameter.multiply(ciphertextProbability).add(ciphertextMappingCount == null ? BigDecimal.ZERO : ciphertextMappingCount);
				denominator = betaHyperparameter.add(unigramCount == null ? BigDecimal.ZERO : unigramCount);

				// Multiply by the channel model probability
				fullConditionalProbability = fullConditionalProbability.multiply(numerator.divide(denominator, MathContext.DECIMAL128));

				if (ciphertextMappingCounts.get(ciphertextMapping) == null) {
					ciphertextMappingCounts.put(ciphertextMapping, BigDecimal.ZERO);
				}

				ciphertextMappingCounts.put(ciphertextMapping, ciphertextMappingCounts.get(ciphertextMapping).add(BigDecimal.ONE));

				if (unigramCounts.get(currentCharacter) == null) {
					unigramCounts.put(currentCharacter, BigDecimal.ZERO);
				}

				unigramCounts.put(currentCharacter, unigramCounts.get(currentCharacter).add(BigDecimal.ONE));

				if (bigramCounts.get(lastCharacter + currentCharacter) == null) {
					bigramCounts.put(lastCharacter + currentCharacter, BigDecimal.ZERO);
				}

				bigramCounts.put(currentCharacter, unigramCounts.get(lastCharacter
						+ currentCharacter).add(BigDecimal.ONE));
			}

			plaintextDistribution.add(new LetterProbability(letter, fullConditionalProbability));
		}

		return plaintextDistribution;
	}

	public BigDecimal convertToLogProbability(BigDecimal probability) {
		if (probability == null) {
			return BigDecimal.ZERO;
		}

		// TODO: use a better implementation of logarithm that does not lose precision
		return BigDecimal.valueOf(Math.log10(probability.doubleValue()));
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
}
