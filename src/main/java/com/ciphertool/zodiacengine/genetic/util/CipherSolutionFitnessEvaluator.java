/**
 * Copyright 2012 George Belden
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

package com.ciphertool.zodiacengine.genetic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.singleton.CipherSingleton;
import com.ciphertool.zodiacengine.util.AbstractSolutionEvaluatorBase;

public class CipherSolutionFitnessEvaluator extends AbstractSolutionEvaluatorBase implements
		FitnessEvaluator {

	private Logger log = Logger.getLogger(getClass());
	HashMap<String, List<Ciphertext>> ciphertextKey;

	/**
	 * @param cipherName
	 * @param cipherDao
	 */
	public CipherSolutionFitnessEvaluator(CipherSingleton cipherSingleton) {
		cipher = cipherSingleton.getInstance();
		ciphertextKey = createKeyFromCiphertext();
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
		Plaintext plaintext = null;
		int total = 0;
		int totalUnique = 0;
		int maxMatches = 0;
		String bestMatch = null;
		boolean uniqueMatch = false;
		String currentValue = null;
		List<Plaintext> plaintextCharacters = solution.getPlaintextCharacters();
		Map<String, List<Plaintext>> plaintextMatchMap;

		/*
		 * Iterate for each List of occurrences of the same Ciphertext
		 */
		for (List<Ciphertext> ciphertextIndices : ciphertextKey.values()) {
			maxMatches = 0;
			uniqueMatch = false;
			bestMatch = null;
			plaintextMatchMap = new HashMap<String, List<Plaintext>>();

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
				plaintext = plaintextCharacters.get(ciphertextIndice.getCiphertextId().getId() - 1);

				currentValue = plaintext.getValue();

				if (!plaintextMatchMap.containsKey(currentValue)) {
					plaintextMatchMap.put(currentValue, new ArrayList<Plaintext>());
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
				for (Plaintext pt : plaintextMatchMap.get(bestMatch)) {
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

		boolean countAdjacent = false;
		int adjacentMatchCount = 0;
		for (Ciphertext ct : cipher.getCiphertextCharacters()) {
			if (countAdjacent == false
					&& plaintextCharacters.get(ct.getCiphertextId().getId() - 1).getHasMatch()) {
				countAdjacent = true;
			} else if (countAdjacent == true
					&& plaintextCharacters.get(ct.getCiphertextId().getId() - 1).getHasMatch()) {
				adjacentMatchCount++;
			} else {
				countAdjacent = false;
			}
		}

		solution.setAdjacentMatchCount(adjacentMatchCount);

		if (log.isDebugEnabled()) {
			log.debug("Solution " + solution.getSolutionId() + " has a confidence level of: "
					+ total);
		}

		solution.setFitness((double) total);

		return (double) total;
	}
}
