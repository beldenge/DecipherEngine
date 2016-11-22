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

package com.ciphertool.engine.fitness.cipherkey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class ConstrainedTieredSamplingMarkovFitnessEvaluator implements FitnessEvaluator {
	private static final int				GRACE_WINDOW_SIZE		= 1;
	private double							nGramWeight;
	private double							frequencyWeight;
	private static final List<Character>	LOWERCASE_LETTERS		= Arrays.asList(new Character[] { 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z' });

	protected Cipher						cipher;

	private MarkovModel						letterMarkovModel;

	private int								lastRowBegin;
	private int								minimumOrder;
	private int								sampleStepSize;

	private Map<Character, Double>			expectedLetterFrequencies;
	private Map<Character, Integer>			expectedLetterCounts	= new HashMap<Character, Integer>(
			LOWERCASE_LETTERS.size());

	@PostConstruct
	public void init() {
		double weightTotal = (nGramWeight + frequencyWeight);

		if (Math.abs(1.0 - weightTotal) > 0.0001) {
			throw new IllegalArgumentException(
					"The sum of nGramWeight and frequencyWeight must equal exactly 1.0, but nGramWeight=" + nGramWeight
							+ " and frequencyWeight=" + frequencyWeight + " sums to " + weightTotal);
		}
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		CipherKeyChromosome cipherKeyChromosome = (CipherKeyChromosome) chromosome;

		Map<Character, Integer> actualLetterCounts = new HashMap<Character, Integer>(LOWERCASE_LETTERS.size());

		for (Map.Entry<String, Gene> entry : cipherKeyChromosome.getGenes().entrySet()) {
			char key = ((CipherKeyGene) entry.getValue()).getValue().charAt(0);

			Integer value = actualLetterCounts.get(key);

			if (value == null) {
				actualLetterCounts.put(key, 0);

				value = actualLetterCounts.get(key);
			}

			actualLetterCounts.put(key, value + 1);
		}

		int numCorrect = 0;

		for (char letter : LOWERCASE_LETTERS) {
			int actualCount = actualLetterCounts.containsKey(letter) ? actualLetterCounts.get(letter) : 0;
			int expectedCount = expectedLetterCounts.get(letter);

			if (expectedCount + GRACE_WINDOW_SIZE >= actualCount && expectedCount - GRACE_WINDOW_SIZE <= actualCount) {
				numCorrect += 1;
			} else if (expectedCount + GRACE_WINDOW_SIZE < actualCount) {
				numCorrect -= (actualCount - (expectedCount + GRACE_WINDOW_SIZE));
			} else if (expectedCount - GRACE_WINDOW_SIZE > actualCount) {
				numCorrect -= ((expectedCount + GRACE_WINDOW_SIZE) - actualCount);
			}
		}

		double frequencyProbability = (double) numCorrect / (double) LOWERCASE_LETTERS.size();

		if (frequencyProbability < 0.0) {
			frequencyProbability = 0.0;
		}

		String currentSolutionString = WordGraphUtils.getSolutionAsString(cipherKeyChromosome).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();
		int offset = ThreadLocalRandom.current().nextInt(sampleStepSize);

		double matches = 0.0;
		NGramIndexNode match = null;
		for (int i = offset; i < currentSolutionString.length() - order; i += sampleStepSize) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order - 1));
			}

			if (match == null) {
				match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));
			}

			if (match == null) {
				continue;
			}

			if (match.getLevel() >= minimumOrder) {
				matches += (double) match.getLevel() / (double) order;
			}

			if (match.getLevel() != order) {
				match = null;
			}
		}

		double nGramProbability = (matches / ((lastRowBegin - order) / sampleStepSize));

		if (nGramProbability < 0.0) {
			nGramProbability = 0.0;
		}

		return (nGramProbability * nGramWeight) + (frequencyProbability * frequencyWeight);
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));

		int cipherKeySize = (int) this.cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().count();

		for (Map.Entry<Character, Double> entry : expectedLetterFrequencies.entrySet()) {
			expectedLetterCounts.put(entry.getKey(), (int) Math.round(entry.getValue() * cipherKeySize));
		}
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
	 * @param expectedLetterFrequencies
	 *            the expectedLetterFrequencies to set
	 */
	@Required
	public void setExpectedLetterFrequencies(Map<Character, Double> expectedLetterFrequencies) {
		this.expectedLetterFrequencies = expectedLetterFrequencies;
	}

	/**
	 * @param nGramWeight
	 *            the nGramWeight to set
	 */
	public void setnGramWeight(double nGramWeight) {
		this.nGramWeight = nGramWeight;
	}

	/**
	 * @param frequencyWeight
	 *            the frequencyWeight to set
	 */
	public void setFrequencyWeight(double frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * @param minimumOrder
	 *            the minimumOrder to set
	 */
	@Required
	public void setMinimumOrder(int minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	/**
	 * @param sampleStepSize
	 *            the sampleStepSize to set
	 */
	@Required
	public void setSampleStepSize(int sampleStepSize) {
		this.sampleStepSize = sampleStepSize;
	}

	@Override
	public String getDisplayName() {
		return "Constrained Tiered Sampling Markov";
	}
}
