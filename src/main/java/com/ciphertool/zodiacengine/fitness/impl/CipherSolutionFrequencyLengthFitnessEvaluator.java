/**
 * Copyright 2015 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.zodiacengine.fitness.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.fitness.SolutionEvaluatorBase;

/**
 * This class was modeled after CipherSolutionFrequencyFitnessEvaluator, with additional evaluation based on average
 * word length.
 * 
 * @author george
 */
public class CipherSolutionFrequencyLengthFitnessEvaluator extends SolutionEvaluatorBase implements FitnessEvaluator {

	private static Logger log = Logger.getLogger(CipherSolutionFrequencyLengthFitnessEvaluator.class);
	private Map<Character, Double> expectedLetterFrequencies;
	private Double averageWordLength;

	/**
	 * Default no-args constructor
	 */
	public CipherSolutionFrequencyLengthFitnessEvaluator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.util.SolutionEvaluator#determineConfidenceLevel
	 * (com.ciphertool.zodiacengine.entities.Solution)
	 * 
	 * Calculates the confidence level as the number of instances that a ciphertext character has the same plaintext
	 * character mapped to it. If a ciphertext character has multiple matches, then select the plaintext character with
	 * the most matches (or if there's a tie, then the first one wins).
	 */
	@Override
	public Double evaluate(Chromosome chromosome) {
		clearHasMatchValues((SolutionChromosome) chromosome);

		SolutionChromosome solution = (SolutionChromosome) chromosome;
		PlaintextSequence plaintext = null;
		int total = 0;
		int totalUnique = 0;
		int maxMatches = 0;
		String bestMatch = null;
		boolean uniqueMatch = false;
		String currentValue = null;
		Character currentCharacter = null;
		List<PlaintextSequence> plaintextCharacters = solution.getPlaintextCharacters();
		Map<String, List<PlaintextSequence>> plaintextMatchMap;
		Map<Character, Double> actualLetterFrequencies = new HashMap<Character, Double>();
		Double currentFrequency = 0.0;

		/*
		 * Initialize the actualLetterFrequencies Map
		 */
		for (Character letter : expectedLetterFrequencies.keySet()) {
			actualLetterFrequencies.put(letter, 0.0);
		}

		Double oneCharacterFrequency = 1.0 / cipher.length();

		/*
		 * Iterate for each List of occurrences of the same Ciphertext
		 */
		for (List<Ciphertext> ciphertextIndices : ciphertextKey.values()) {
			maxMatches = 0;
			uniqueMatch = false;
			bestMatch = null;
			plaintextMatchMap = new HashMap<String, List<PlaintextSequence>>();

			/*
			 * Now iterate for each occurrence of the current Ciphertext character
			 */
			for (Ciphertext ciphertextIndice : ciphertextIndices) {
				/*
				 * This just returns the Plaintext character that corresponds to the given Ciphertext character. The
				 * usage of List.get() assumes that the ArrayList is properly sorted by CiphertextId
				 * 
				 * We could also make this into a map with the ciphertextId as the key. Then we would no longer have to
				 * worry about order and or subtracting one from the id. It does come with a performance hit though.
				 */
				try {
					plaintext = plaintextCharacters.get(ciphertextIndice.getCiphertextId());
				} catch (IndexOutOfBoundsException ioobe) {
					log.error(
							"Caught IndexOutOfBoundsException for index " + (ciphertextIndice.getCiphertextId())
									+ " and size " + plaintextCharacters.size() + " while evaluating Chromosome: "
									+ chromosome, ioobe);
				}

				currentValue = plaintext.getValue().toLowerCase();

				currentCharacter = currentValue.charAt(0);

				/*
				 * Put the new value in the HashMap. It will overwrite the current entry, which is fine.
				 */
				currentFrequency = actualLetterFrequencies.get(currentCharacter);
				if (currentFrequency != null) {
					actualLetterFrequencies.put(currentCharacter, currentFrequency + oneCharacterFrequency);
				} else {
					log.debug("Found non-alpha character in Plaintext: " + currentValue.charAt(0));
				}

				if (!plaintextMatchMap.containsKey(currentValue)) {
					plaintextMatchMap.put(currentValue, new ArrayList<PlaintextSequence>());
				} else {
					uniqueMatch = true;
				}

				plaintextMatchMap.get(currentValue).add(plaintext);

				if (plaintextMatchMap.get(currentValue).size() > maxMatches) {
					/*
					 * Subtract by one when setting maxMatches so that a match on just a pair does not count as two
					 * matches.
					 */
					maxMatches = plaintextMatchMap.get(currentValue).size() - 1;

					bestMatch = currentValue;
				}
			}

			/*
			 * If there was a match on this Ciphertext, set the hasMatch property to true on all the Plaintext matches.
			 * Use the bestMatch value so that only the Plaintext with the optimal number of matches is set.
			 */
			if (bestMatch != null) {
				for (PlaintextSequence pt : plaintextMatchMap.get(bestMatch)) {
					pt.setHasMatch(true);
				}
			}

			/*
			 * Add the Plaintext matches on this Ciphertext character to the overall confidence value, represented by
			 * total
			 */
			total += maxMatches;

			/*
			 * Increment the unique matches by converting a boolean to an int
			 */
			totalUnique += (uniqueMatch ? 1 : 0);
		}

		solution.setTotalMatches(total);
		solution.setUniqueMatches(totalUnique);
		solution.setAdjacentMatches(calculateAdjacentMatches(plaintextCharacters));

		Double totalDifference = 0.0;
		for (Character letter : expectedLetterFrequencies.keySet()) {
			totalDifference += Math.abs(expectedLetterFrequencies.get(letter) - actualLetterFrequencies.get(letter));
		}

		Double fitness = (double) total;

		double frequencyReductionFactor = (1 - totalDifference);

		fitness = fitness * frequencyReductionFactor;

		double solutionAverageWordLength = cipher.length() / solution.getGenes().size();

		double wordLengthReductionFactor;

		if (solutionAverageWordLength > averageWordLength) {
			wordLengthReductionFactor = averageWordLength / solutionAverageWordLength;
		} else {
			wordLengthReductionFactor = solutionAverageWordLength / averageWordLength;
		}

		fitness = fitness * wordLengthReductionFactor;

		if (log.isDebugEnabled()) {
			log.debug("Solution " + solution.getId() + " has a confidence level of: " + fitness);
		}

		return fitness;
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
	 * @param averageWordLength
	 *            the averageWordLength to set
	 */
	public void setAverageWordLength(Double averageWordLength) {
		this.averageWordLength = averageWordLength;
	}

	@Override
	public String getDisplayName() {
		return "Cipher Solution Frequency Length";
	}
}
