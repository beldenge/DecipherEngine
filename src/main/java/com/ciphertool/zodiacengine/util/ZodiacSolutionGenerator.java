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
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.util.SentenceHelper;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class ZodiacSolutionGenerator implements SolutionGenerator {
	private SentenceHelper sentenceHelper;
	private Cipher cipher;
	private Logger log = Logger.getLogger(getClass());
	int cipherLength;

	/**
	 * Default no-args constructor
	 */
	public ZodiacSolutionGenerator() {
	}

	/**
	 * @return
	 * 
	 *         Generates a solution by calling the helper method getSentences()
	 *         and passing the result to
	 *         convertSentencesToPlaintext(List<Sentence>)
	 */
	@Override
	public SolutionChromosome generateSolution() {
		/*
		 * Set confidence levels to lowest possible
		 */
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher
				.getRows(), cipher.getColumns());

		convertSentencesToPlaintext(solution, getSentences());

		log.debug(solution);

		return solution;
	}

	private List<Sentence> getSentences() {
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		Sentence nextSentence;
		int length = 0;
		do {
			nextSentence = sentenceHelper.generateRandomSentence();
			length += nextSentence.length();
			sentenceList.add(nextSentence);
		} while (length < cipherLength);
		return sentenceList;
	}

	private void convertSentencesToPlaintext(SolutionChromosome solution,
			List<Sentence> sentenceList) {
		StringBuilder rawText = new StringBuilder();
		for (Sentence sentence : sentenceList) {
			for (Word w : sentence.getWords()) {
				rawText.append(w.getId().getWord());
			}
		}
		char[] chars = new char[cipherLength];
		rawText.getChars(0, cipherLength, chars, 0);
		int id = 0;

		for (char c : chars) {
			/*
			 * It is very important to convert to lowercase here, since map
			 * lookups within the evaluator are case-sensitive.
			 */
			WordGene gene = new WordGene(
					new Word(new WordId(String.valueOf(c).toLowerCase(), '*')), solution, id);

			solution.addGene(gene);
			id++;
		}
	}

	@Override
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
		this.cipherLength = cipher.length();
	}

	@Required
	public void setSentenceHelper(SentenceHelper sentenceHelper) {
		this.sentenceHelper = sentenceHelper;
	}
}
