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

import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class RandomWordSolutionGenerator implements SolutionGenerator {
	private Cipher cipher;
	private WordListDao wordListDao;
	private Logger log = Logger.getLogger(getClass());

	/**
	 * Default no-args constructor
	 */
	public RandomWordSolutionGenerator() {
	}

	@Override
	public SolutionChromosome generateSolution() {
		/*
		 * Set confidence levels to lowest possible
		 */
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher
				.getRows(), cipher.getColumns());

		List<Word> words = getWords(solution);

		convertWordsToPlaintext(solution, words);

		log.debug(solution);

		return solution;
	}

	private List<Word> getWords(SolutionChromosome solution) {
		List<Word> wordList = new ArrayList<Word>();
		Word nextWord;
		int length = 0;
		do {
			nextWord = wordListDao.findRandomWord();

			length += nextWord.getId().getWord().length();

			/*
			 * Truncate the last Word if it exceeds the Cipher length.
			 */
			if (length > cipher.length()) {
				int endIndex = (nextWord.getId().getWord().length() - (length - cipher.length()));
				nextWord.getId().setWord(nextWord.getId().getWord().substring(0, endIndex));
			}

			wordList.add(nextWord);
		} while (length < cipher.length());
		return wordList;
	}

	public void convertWordsToPlaintext(SolutionChromosome solution, List<Word> wordList) {
		StringBuffer rawText = new StringBuffer();
		for (Word w : wordList) {
			rawText.append(w.getId().getWord());
		}
		char[] chars = new char[cipher.length()];
		rawText.getChars(0, cipher.length(), chars, 0);
		Integer id = 0;

		for (char c : chars) {
			WordGene gene = new WordGene(new Word(new WordId(String.valueOf(c), '*')), solution, id);

			solution.addGene(gene);
			id++;
		}
	}

	@Override
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	/**
	 * @param wordListDao
	 *            the wordListDao to set
	 */
	@Required
	public void setWordListDao(WordListDao wordListDao) {
		this.wordListDao = wordListDao;
	}
}