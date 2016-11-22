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

package com.ciphertool.engine.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.sherlock.dao.NGramListDao;
import com.ciphertool.sherlock.entities.NGram;
import com.ciphertool.sherlock.entities.Word;
import com.ciphertool.sherlock.markov.WordNGramIndexNode;

public class TopWordsFacade {
	private Logger				log							= LoggerFactory.getLogger(getClass());

	private int					minWordLength;

	private NGramListDao		nGramListDao;

	private List<Word>			topWords					= new ArrayList<Word>();
	private List<Word>			topWordsAndNGrams			= new ArrayList<Word>();

	private WordNGramIndexNode	rootNodeWithWordsAndNGrams	= new WordNGramIndexNode();

	@PostConstruct
	public void init() {
		long start = System.currentTimeMillis();

		log.info("Beginning indexing of words and n-grams.");

		populateWordsAndNGramsIndex();

		log.info("Finished indexing words and n-grams in " + (System.currentTimeMillis() - start) + "ms.");
	}

	protected void populateWordsAndNGramsIndex() {
		topWordsAndNGrams.addAll(topWords);

		Map<Integer, List<NGram>> mapOfNGramLists = nGramListDao.getMapOfNGramLists();

		for (Integer numWords : mapOfNGramLists.keySet()) {
			for (NGram nGram : mapOfNGramLists.get(numWords)) {
				topWordsAndNGrams.add(new Word(nGram));
			}
		}

		String lowerCaseWord;
		for (Word word : topWordsAndNGrams) {
			if (word.getWord().length() < minWordLength) {
				continue;
			}

			lowerCaseWord = word.getWord().toLowerCase();
			WordGraphUtils.populateMap(rootNodeWithWordsAndNGrams, lowerCaseWord);
		}
	}

	public void addEntryToWordsAndNGramsIndex(Word word) {
		if (word.getWord().length() < minWordLength) {
			return;
		}

		String lowerCaseWord = word.getWord().toLowerCase();
		WordGraphUtils.populateMap(rootNodeWithWordsAndNGrams, lowerCaseWord);
	}

	/**
	 * @param nGramListDao
	 *            the nGramListDao to set
	 */
	@Required
	public void setnGramListDao(NGramListDao nGramListDao) {
		this.nGramListDao = nGramListDao;
	}

	/**
	 * @param minWordLength
	 *            the minWordLength to set
	 */
	@Required
	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}

	/**
	 * @return the root node of the combined word and n-gram index
	 */
	public WordNGramIndexNode getIndexedWordsAndNGrams() {
		return rootNodeWithWordsAndNGrams;
	}
}
