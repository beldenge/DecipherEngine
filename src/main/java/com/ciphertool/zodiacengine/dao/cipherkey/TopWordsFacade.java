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

package com.ciphertool.zodiacengine.dao.cipherkey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.dao.NGramListDao;
import com.ciphertool.sentencebuilder.dao.UniqueWordListDao;
import com.ciphertool.sentencebuilder.entities.NGram;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.wordgraph.IndexNode;
import com.ciphertool.zodiacengine.common.WordGraphUtils;

public class TopWordsFacade {
	private Logger log = Logger.getLogger(getClass());

	private int minWordLength;
	private int top;

	private UniqueWordListDao wordListDao;
	private NGramListDao nGramListDao;

	private List<Word> topWords = new ArrayList<Word>();
	private List<Word> topWordsAndNGrams = new ArrayList<Word>();

	private IndexNode rootNodeWithWordsAndNGrams = new IndexNode();
	private IndexNode rootNodeWordsOnly = new IndexNode();

	@PostConstruct
	public void init() {
		long start = System.currentTimeMillis();

		log.info("Beginning indexing of words and n-grams.");

		populateWordsIndex();

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
			if (word.getId().getWord().length() < minWordLength) {
				continue;
			}

			lowerCaseWord = word.getId().getWord().toLowerCase();
			WordGraphUtils.populateMap(rootNodeWithWordsAndNGrams, lowerCaseWord);
		}
	}

	public void addEntryToWordsAndNGramsIndex(Word word) {
		if (word.getId().getWord().length() < minWordLength) {
			return;
		}

		String lowerCaseWord = word.getId().getWord().toLowerCase();
		WordGraphUtils.populateMap(rootNodeWithWordsAndNGrams, lowerCaseWord);
	}

	protected void populateWordsIndex() {
		topWords = wordListDao.getTopWords(top);

		if (topWords == null || topWords.size() < top) {
			String message = "Attempted to get top " + top + " words from populated DAO, but only "
					+ (topWords == null ? 0 : topWords.size()) + " words were available.";
			log.error(message);
			throw new IllegalStateException(message);
		}

		String lowerCaseWord;
		for (Word word : topWords) {
			if (word.getId().getWord().length() < minWordLength) {
				continue;
			}

			lowerCaseWord = word.getId().getWord().toLowerCase();
			WordGraphUtils.populateMap(rootNodeWordsOnly, lowerCaseWord);
		}
	}

	/**
	 * @param top
	 *            the top to set
	 */
	@Required
	public void setTop(int top) {
		if (top <= 0) {
			String message = "Value of " + top + " is invalid for top in " + this.getClass()
					+ ".  Value must be greater than zero.";
			log.error(message);
			throw new IllegalArgumentException(message);
		}

		this.top = top;
	}

	/**
	 * @param wordListDao
	 *            the wordListDao to set
	 */
	@Required
	public void setWordListDao(UniqueWordListDao wordListDao) {
		this.wordListDao = wordListDao;
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
	public IndexNode getIndexedWordsAndNGrams() {
		return rootNodeWithWordsAndNGrams;
	}

	/**
	 * @return the root node of the word index
	 */
	public IndexNode getIndexedWords() {
		return rootNodeWordsOnly;
	}
}
