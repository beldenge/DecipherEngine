package com.ciphertool.zodiacengine.genetic;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;

public class WordGeneListDao extends WordListDao implements GeneListDao {
	/*
	 * Calls the super constructor and then stacks the list based on word
	 * frequency
	 */
	public WordGeneListDao(WordDao wordDao) {
		super(wordDao);

		for (Word w : this.wordList) {
			/*
			 * Add the word to the map by reference a number of times equal to
			 * the frequency value -1 since it already exists in the list once.
			 */
			for (int i = 0; i < w.getFrequencyWeight() - 1; i++) {
				this.wordList.add(w);
			}
		}
	}

	public WordGene findRandomGene() {
		return (WordGene) findRandomWord();
	}
}
