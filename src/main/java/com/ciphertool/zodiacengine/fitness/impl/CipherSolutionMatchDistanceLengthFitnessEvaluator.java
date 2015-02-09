/**
 * Copyright 2013 George Belden
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

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.ComplexGene;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;
import com.ciphertool.zodiacengine.fitness.SolutionTruncatedEvaluatorBase;

public class CipherSolutionMatchDistanceLengthFitnessEvaluator extends
		SolutionTruncatedEvaluatorBase implements FitnessEvaluator {

	private Logger log = Logger.getLogger(getClass());
	private static final int ACCEPTABLE_DISTANCE = 3;
	private static final double MATCH_DISTANCE_BONUS = 2.0;
	private static final double AVERAGE_WORD_LENGTH_BONUS = 100.0;
	private static final double WORD_LENGTH_LOW_AVERAGE = 3.5;
	private static final double WORD_LENGTH_HIGH_AVERAGE = 6.5;

	/**
	 * Default no-args constructor
	 */
	public CipherSolutionMatchDistanceLengthFitnessEvaluator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.util.SolutionEvaluator#determineConfidenceLevel
	 * (com.ciphertool.zodiacengine.entities.Solution)
	 * 
	 * Calculates the confidence level as the number of instances that a
	 * ciphertext character has the same plaintext character mapped to it. If a
	 * ciphertext character has multiple matches, then select the plaintext
	 * character with the most matches (or if there's a tie, then the first one
	 * wins).
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
		List<PlaintextSequence> plaintextCharacters = solution.getPlaintextCharacters();
		Map<String, List<PlaintextSequence>> plaintextMatchMap;

		/*
		 * Iterate for each List of occurrences of the same Ciphertext
		 */
		for (List<Ciphertext> ciphertextIndices : ciphertextKey.values()) {
			maxMatches = 0;
			uniqueMatch = false;
			bestMatch = null;
			plaintextMatchMap = new HashMap<String, List<PlaintextSequence>>();

			/*
			 * Now iterate for each occurrence of the current Ciphertext
			 * character
			 */
			for (Ciphertext ciphertextIndice : ciphertextIndices) {
				/*
				 * This just returns the Plaintext character that corresponds to
				 * the given Ciphertext character. The usage of List.get()
				 * assumes that the ArrayList is properly sorted by CiphertextId
				 * 
				 * We could also make this into a map with the ciphertextId as
				 * the key. Then we would no longer have to worry about order
				 * and or subtracting one from the id. It does come with a
				 * performance hit though.
				 */
				plaintext = plaintextCharacters.get(ciphertextIndice.getCiphertextId());

				currentValue = plaintext.getValue().toLowerCase();

				if (!plaintextMatchMap.containsKey(currentValue)) {
					plaintextMatchMap.put(currentValue, new ArrayList<PlaintextSequence>());
				} else {
					uniqueMatch = true;
				}

				plaintextMatchMap.get(currentValue).add(plaintext);

				if (plaintextMatchMap.get(currentValue).size() > maxMatches) {
					/*
					 * Subtract by one when setting maxMatches so that a match
					 * on just a pair does not count as two matches.
					 */
					maxMatches = plaintextMatchMap.get(currentValue).size() - 1;

					bestMatch = currentValue;
				}
			}

			/*
			 * If there was a match on this Ciphertext, set the hasMatch
			 * property to true on all the Plaintext matches. Use the bestMatch
			 * value so that only the Plaintext with the optimal number of
			 * matches is set.
			 */
			if (bestMatch != null) {
				for (PlaintextSequence pt : plaintextMatchMap.get(bestMatch)) {
					pt.setHasMatch(true);
				}
			}

			/*
			 * Add the Plaintext matches on this Ciphertext character to the
			 * overall confidence value, represented by total
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

		/*
		 * We don't care to evaluate past the last row since it is likely to be
		 * filler.
		 */
		int lastSequenceToCheck = (cipher.getColumns() * (cipher.getRows() - 1));
		double matchDistanceFactor = determineMatchDistanceFactor(solution, lastSequenceToCheck);
		double wordLengthFactor = determineWordLengthFactor(solution, lastSequenceToCheck);

		double fitness = ((double) (total)) + matchDistanceFactor + wordLengthFactor;

		if (log.isDebugEnabled()) {
			log.debug("Solution " + solution.getId() + " has a confidence level of: " + fitness);
		}

		return fitness;
	}

	/**
	 * This awards extra points towards fitness due to sufficient gaps among
	 * identical words. The beginning and end of the cipher are used as
	 * endpoints for gap checking regardless of the existence of another match.
	 * 
	 * @param solution
	 *            the solution to evaluate
	 * @param lastSequenceToCheck
	 *            the sequence for which we don't want to evaluate further than
	 * @return the extra points awarded
	 */
	private static double determineMatchDistanceFactor(SolutionChromosome solution,
			int lastSequenceToCheck) {
		int numberOfGenes = solution.getGenes().size();
		Map<String, List<Integer>> genePositionMap = new HashMap<String, List<Integer>>();

		String nextWord = null;
		for (int x = 0; x < numberOfGenes; x++) {
			if (((WordGene) solution.getGenes().get(x)).getSequences().get(0).getSequenceId() < lastSequenceToCheck) {
				nextWord = ((WordGene) solution.getGenes().get(x)).getWordString().toLowerCase();

				if (!genePositionMap.containsKey(nextWord)) {
					genePositionMap.put(nextWord, new ArrayList<Integer>());
				}

				genePositionMap.get(nextWord).add(x);
			}
		}

		double extraPoints = 0;

		/*
		 * We don't care about the Strings themselves anymore. Just their
		 * positions.
		 */
		for (List<Integer> positionList : genePositionMap.values()) {
			int index = 0;

			for (Integer position : positionList) {
				/*
				 * Give extra points towards the fitness for each gap between
				 * identical words greater than an acceptable distance. If index
				 * is zero, then this is the first word, so there is nothing to
				 * compare with yet.
				 */
				if (index > 0 && (position - index) > ACCEPTABLE_DISTANCE) {
					extraPoints += MATCH_DISTANCE_BONUS;
				}

				index = position;
			}

			/*
			 * No need to check if there is an acceptable distance between this
			 * word and the end of the solution. It's meaningless as the whole
			 * point of this evaluator is to check the distance of words among
			 * each other.
			 */
		}

		return extraPoints;
	}

	/**
	 * This awards extra points towards fitness due to the average word length.
	 * 
	 * @param solution
	 *            the solution to evaluate
	 * @param lastSequenceToCheck
	 *            the sequence for which we don't want to evaluate further than
	 * @return the extra points awarded
	 */
	private static double determineWordLengthFactor(SolutionChromosome solution,
			int lastSequenceToCheck) {

		/*
		 * Get the average word length.
		 */
		int validWordCount = 0;
		for (Gene gene : solution.getGenes()) {
			if (((ComplexGene) gene).getSequences().get(0).getSequenceId() < lastSequenceToCheck) {
				validWordCount++;
			}
		}

		double averageWordLength = ((double) lastSequenceToCheck / (double) (validWordCount));

		double extraPoints = 0;

		if (averageWordLength > WORD_LENGTH_LOW_AVERAGE
				&& averageWordLength < WORD_LENGTH_HIGH_AVERAGE) {
			extraPoints += AVERAGE_WORD_LENGTH_BONUS;
		}

		return extraPoints;
	}
}