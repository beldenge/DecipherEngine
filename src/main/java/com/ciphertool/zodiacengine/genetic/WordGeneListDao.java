package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class WordGeneListDao extends WordListDao implements GeneListDao {

	/*
	 * Calls the super constructor and then stacks the list based on word
	 * frequency
	 */
	public WordGeneListDao(WordDao wordDao) {
		super(wordDao);

		List<Word> wordsToAdd = new ArrayList<Word>();

		for (Word w : this.wordList) {
			/*
			 * Add the word to the map by reference a number of times equal to
			 * the frequency value -1 since it already exists in the list once.
			 */
			for (int i = 0; i < w.getFrequencyWeight() - 1; i++) {
				wordsToAdd.add(w);
			}
		}

		this.wordList.addAll(wordsToAdd);
	}

	public WordGene findRandomGene() {
		/*
		 * TODO this sucks. The whole purpose was to cast from Word to WordGene
		 * to avoid cloning which is expensive.
		 */
		Word randomWord = findRandomWord();

		return new WordGene(new WordId(randomWord.getWordId().getWord(), randomWord.getWordId()
				.getPartOfSpeech()), randomWord.getFrequencyWeight());
	}
}
