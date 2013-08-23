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

package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.dao.SolutionDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class ZodiacSolutionEvaluator extends AbstractSolutionEvaluatorBase implements
		SolutionEvaluator {

	private static Logger log = Logger.getLogger(ZodiacSolutionEvaluator.class);
	HashMap<String, List<Ciphertext>> ciphertextKey;
	private int totalMatchThreshold;
	private int uniqueMatchThreshold;
	private int adjacencyThreshold;
	private SolutionDao solutionDao;

	/**
	 * Default no-args constructor
	 */
	public ZodiacSolutionEvaluator() {
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
	public int determineConfidenceLevel(SolutionChromosome solution) {
		clearHasMatchValues(solution);

		Plaintext plaintext = null;
		int total = 0;
		int totalUnique = 0;
		int maxMatches = 0;
		String bestMatch = null;
		boolean uniqueMatch = false;
		String currentValue = null;
		List<PlaintextSequence> plaintextCharacters = solution.getPlaintextCharacters();
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
				plaintext = plaintextCharacters.get(ciphertextIndice.getCiphertextId());

				currentValue = plaintext.getValue().toLowerCase();

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
					&& plaintextCharacters.get(ct.getCiphertextId()).getHasMatch()) {
				countAdjacent = true;
			} else if (countAdjacent == true
					&& plaintextCharacters.get(ct.getCiphertextId()).getHasMatch()) {
				adjacentMatchCount++;
			} else {
				countAdjacent = false;
			}
		}

		solution.setAdjacentMatchCount(adjacentMatchCount);

		log.debug("Solution " + solution.getId() + " has a confidence level of: " + total);

		if (solution.getTotalMatches() >= totalMatchThreshold) {
			log.info("Found solution with " + solution.getTotalMatches()
					+ " total matches.  Persisting to solution table.");
			solutionDao.insert(solution);
		} else if (solution.getUniqueMatches() >= uniqueMatchThreshold) {
			log.info("Found solution with " + solution.getUniqueMatches()
					+ " unique matches.  Persisting to solution table.");
			solutionDao.insert(solution);
		} else if (solution.getAdjacentMatchCount() >= adjacencyThreshold) {
			log.info("Found solution with " + solution.getAdjacentMatchCount()
					+ " adjacent matches.  Persisting to solution table.");
			solutionDao.insert(solution);
		}

		return total;
	}

	@Override
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
		this.ciphertextKey = createKeyFromCiphertext();
	}

	/**
	 * @param totalMatcheThreshold
	 *            the totalMatcheThreshold to set
	 */
	@Required
	public void setTotalMatchThreshold(int totalMatchThreshold) {
		this.totalMatchThreshold = totalMatchThreshold;
	}

	/**
	 * @param uniqueMatchThreshold
	 *            the uniqueMatchThreshold to set
	 */
	@Required
	public void setUniqueMatchThreshold(int uniqueMatchThreshold) {
		this.uniqueMatchThreshold = uniqueMatchThreshold;
	}

	/**
	 * @param solutionDao
	 *            the solutionDao to set
	 */
	@Required
	public void setSolutionDao(SolutionDao solutionDao) {
		this.solutionDao = solutionDao;
	}

	/**
	 * @param adjacencyThreshold
	 *            the adjacencyThreshold to set
	 */
	@Required
	public void setAdjacencyThreshold(int adjacencyThreshold) {
		this.adjacencyThreshold = adjacencyThreshold;
	}
}