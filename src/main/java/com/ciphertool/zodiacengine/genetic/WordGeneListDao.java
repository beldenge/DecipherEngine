package com.ciphertool.zodiacengine.genetic;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;

public class WordGeneListDao implements GeneListDao {
	private WordListDao wordListDao;

	@Override
	public Gene findRandomGene(Chromosome chromosome, int beginIndex) {
		Word word = wordListDao.findRandomWord();

		Gene gene = new WordGene(word, (SolutionChromosome) chromosome, beginIndex);

		return gene;
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
