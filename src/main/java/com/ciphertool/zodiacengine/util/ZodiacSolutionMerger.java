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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dao.SolutionDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.singleton.CipherSingleton;

public class ZodiacSolutionMerger implements SolutionMerger {
	private static Logger log = Logger.getLogger(ZodiacSolutionMerger.class);
	private SolutionDao solutionDao;
	private String cipherName;
	private CipherSingleton cipherSingleton;
	private SolutionEvaluator solutionEvaluator;

	private static ApplicationContext context;

	private static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		log.info("Spring context created successfully!");
	}

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		setUp();
		ZodiacSolutionMerger zodiacSolutionMerger = (ZodiacSolutionMerger) context
				.getBean("solutionMerger");
		zodiacSolutionMerger.mergeSolutions();
	}

	@Override
	public Solution mergeSolutions() {
		Cipher zodiacCipher = cipherSingleton.getInstance();

		Solution bestFitSolution = null;
		List<Solution> solutions = new ArrayList<Solution>();
		solutions.addAll(solutionDao.findByCipherName(cipherName));

		if (solutions == null || solutions.size() == 0) {
			log.info("No solutions to merge.  Returning null.");

			return null;
		}

		Map<Integer, Map<String, List<Plaintext>>> plaintextHistogramMap = new HashMap<Integer, Map<String, List<Plaintext>>>();
		/*
		 * Initialize the map
		 */
		for (Ciphertext ciphertext : zodiacCipher.getCiphertextCharacters()) {
			if (plaintextHistogramMap.containsKey(ciphertext.getCiphertextId().getId())) {
				log.warn("Found duplicate ciphertext key in cipher.  This should not be possible.  Returning null.");

				return null;
			}

			plaintextHistogramMap.put(ciphertext.getCiphertextId().getId(),
					new HashMap<String, List<Plaintext>>());
		}

		for (Solution solution : solutions) {
			if (solution.getPlaintextCharacters().size() != zodiacCipher.length()) {
				log.warn("Cipher has " + zodiacCipher.length()
						+ " characters, but found solution with "
						+ solution.getPlaintextCharacters().size()
						+ " characters.  This should not be possible. Returning null.");

				return null;
			}

			for (Plaintext plaintext : solution.getPlaintextCharacters()) {
				if (!plaintextHistogramMap
						.containsKey(plaintext.getPlaintextId().getCiphertextId())) {
					log.warn("Encountered plaintextId which does not match any ciphertext character.  This should not be possible.  Returning null.");

					return null;
				}

				if (!plaintextHistogramMap.get(plaintext.getPlaintextId().getCiphertextId())
						.containsKey(plaintext.getValue())) {
					plaintextHistogramMap.get(plaintext.getPlaintextId().getCiphertextId()).put(
							plaintext.getValue(), new ArrayList<Plaintext>());
				}

				plaintextHistogramMap.get(plaintext.getPlaintextId().getCiphertextId()).get(
						plaintext.getValue()).add(plaintext);
			}
		}

		bestFitSolution = new Solution(zodiacCipher.getId(), 0, 0, 0);
		bestFitSolution.setCipher(zodiacCipher);

		for (int ciphertextId = 1; ciphertextId <= zodiacCipher.length(); ciphertextId++) {
			log.info("ciphertextId: " + ciphertextId);
			String bestMatch = "";
			int mostMatches = 0;

			for (String plaintextCharacter : plaintextHistogramMap.get(ciphertextId).keySet()) {
				Map<String, List<Plaintext>> plaintextMap = plaintextHistogramMap.get(ciphertextId);

				log.info("Plaintext character : " + plaintextCharacter + ", count: "
						+ plaintextMap.get(plaintextCharacter).size());

				if (plaintextMap.get(plaintextCharacter).size() > mostMatches) {
					bestMatch = plaintextCharacter;
					mostMatches = plaintextMap.get(plaintextCharacter).size();
				}
			}

			bestFitSolution.addPlaintext(new Plaintext(new PlaintextId(bestFitSolution,
					ciphertextId), bestMatch));
		}

		solutionEvaluator.determineConfidenceLevel(bestFitSolution);

		log.info("bestFitSolution: " + bestFitSolution);

		return bestFitSolution;
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
	 * @param cipherName
	 *            the cipherName to set
	 */
	@Required
	public void setCipherName(String cipherName) {
		this.cipherName = cipherName;
	}

	/**
	 * @param solutionEvaluator
	 *            the solutionEvaluator to set
	 */
	@Required
	public void setSolutionEvaluator(SolutionEvaluator solutionEvaluator) {
		this.solutionEvaluator = solutionEvaluator;
	}

	/**
	 * @param cipherSingleton
	 *            the cipherSingleton to set
	 */
	@Required
	public void setCipherSingleton(CipherSingleton cipherSingleton) {
		this.cipherSingleton = cipherSingleton;
	}
}
