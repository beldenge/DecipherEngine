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

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.util.LetterUtils;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class RandomCharacterSolutionGenerator implements SolutionGenerator {
	private Cipher cipher;
	private Logger log = Logger.getLogger(getClass());

	/**
	 * Default no-args constructor
	 */
	public RandomCharacterSolutionGenerator() {
	}

	/**
	 * Generates a solution by calling the helper method getSentences() and
	 * passing the result to convertSentencesToPlaintext(List<Sentence>)
	 */
	@Override
	public SolutionChromosome generateSolution() {
		/*
		 * Set confidence levels to lowest possible
		 */
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher
				.getRows(), cipher.getColumns());

		getCharacters(solution);

		log.debug(solution);

		return solution;
	}

	public void getCharacters(SolutionChromosome solution) {
		Plaintext nextPlaintext;
		int id = 0;
		char randomChar;

		do {
			id++;

			randomChar = LetterUtils.getRandomLetter();

			nextPlaintext = new Plaintext(id, String.valueOf(randomChar));

			solution.addPlaintext(nextPlaintext);
		} while (id < cipher.length());
	}

	@Override
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}
}