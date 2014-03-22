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
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;
import com.ciphertool.zodiacengine.fitness.AbstractSolutionTruncatedEvaluatorBase;

public class CipherSolutionUniqueWordFitnessEvaluator extends
		AbstractSolutionTruncatedEvaluatorBase implements FitnessEvaluator {

	private Logger log = Logger.getLogger(getClass());
	private static final double MATCH_THRESHOLD = 0.0;

	/**
	 * Default no-args constructor
	 */
	public CipherSolutionUniqueWordFitnessEvaluator() {
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
			 * overall confidence value, represented by total. Each successive
			 * match gives increasingly more points to the fitness value.
			 */
			int subtotal = 0;
			for (int i = 1; i <= maxMatches; i++) {
				subtotal += i;
			}
			total += subtotal;

			/*
			 * Increment the unique matches by converting a boolean to an int
			 */
			totalUnique += (uniqueMatch ? 1 : 0);
		}
		solution.setTotalMatches(total);
		solution.setUniqueMatches(totalUnique);

		boolean countAdjacent = false;
		int adjacentMatchCount = 0;
		for (Ciphertext ct : cipher.getCiphertextCharacters()) {
			if (countAdjacent == false
					&& plaintextCharacters.get(ct.getCiphertextId()).getHasMatch()) {
				countAdjacent = true;
			} else if (countAdjacent == true
					&& plaintextCharacters.get(ct.getCiphertextId()).getHasMatch()) {
				adjacentMatchCount++;
			} else {
				countAdjacent = false;
			}
		}

		solution.setAdjacentMatches(adjacentMatchCount);

		/*
		 * We don't care to evaluate past the last row since it is likely to be
		 * filler.
		 */
		int lastSequenceToCheck = (cipher.getColumns() * (cipher.getRows() - 1));
		double uniquenessFactor = determineUniquenessFactor(solution, lastSequenceToCheck);

		double fitness = ((double) (total)) - uniquenessFactor;

		/*
		 * if fitness is less than zero, we will get
		 * ArrayIndexOutOfBoundsException, and if it is equal to zero, then that
		 * could mess up selector methods.
		 */
		fitness = fitness > 0 ? fitness : 1;

		if (log.isDebugEnabled()) {
			log.debug("Solution " + solution.getId() + " has a confidence level of: " + fitness);
		}

		return fitness;
	}

	/**
	 * This awards extra points towards fitness due to the uniqueness of words
	 * in the cipher.
	 * 
	 * @param solution
	 *            the solution to evaluate
	 * @param lastSequenceToCheck
	 *            the sequence for which we don't want to evaluate further than
	 * @return the extra points awarded
	 */
	private static double determineUniquenessFactor(SolutionChromosome solution,
			int lastSequenceToCheck) {
		int numberOfGenes = solution.getGenes().size();
		Map<String, Integer> geneOccurrenceMap = new HashMap<String, Integer>();

		String nextWord = null;
		/*
		 * Count the number of occurrences of each word and stick it in a map.
		 */
		WordGene nextWordGene;
		for (int x = 0; x < numberOfGenes; x++) {
			nextWordGene = (WordGene) solution.getGenes().get(x);
			if (nextWordGene.getSequences().get(0).getSequenceId() < lastSequenceToCheck) {
				if (nextWordGene.countMatches() == 0) {
					/*
					 * We only want to consider this for duplication if it
					 * contains plaintext matches. Otherwise it's not affecting
					 * anything.
					 */
					continue;
				}

				nextWord = nextWordGene.getWordString().toLowerCase();

				if (!geneOccurrenceMap.containsKey(nextWord)) {
					geneOccurrenceMap.put(nextWord, 0);
				}

				geneOccurrenceMap.put(nextWord, geneOccurrenceMap.get(nextWord) + 1);
			}
		}

		double penalty = 0;

		/*
		 * We don't care about the Strings themselves anymore. Just their
		 * numbers of occurrences.
		 */
		for (Integer numOccurrences : geneOccurrenceMap.values()) {
			int pointsToSubtract = 1;
			/*
			 * For each successive occurrence over the threshold, add
			 * increasingly more points to the penalty.
			 */
			for (int i = numOccurrences; i > MATCH_THRESHOLD; i--) {
				penalty += pointsToSubtract;
				pointsToSubtract++;
			}
		}

		return penalty;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
		this.ciphertextKey = createKeyFromCiphertext();
	}
}