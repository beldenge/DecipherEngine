package com.ciphertool.zodiacengine.genetic;

import com.ciphertool.sentencebuilder.dao.WordDao;
import com.ciphertool.sentencebuilder.dao.WordListDao;

public class WordGeneListDao extends WordListDao implements GeneListDao {
	public WordGeneListDao(WordDao wordDao) {
		super(wordDao);
	}

	public WordGene findRandomGene() {
		return (WordGene) findRandomWord();
	}
}
