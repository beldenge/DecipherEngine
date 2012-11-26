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

package com.ciphertool.zodiacengine.genetic.dao;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.dao.GeneListDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class WordGeneListDao implements GeneListDao {
	private WordListDao wordListDao;

	@Override
	public Gene findRandomGene(Chromosome chromosome, int beginIndex) {
		Word word = wordListDao.findRandomWord();

		Gene gene = new WordGene(word, (SolutionChromosome) chromosome, beginIndex);

		return gene;
	}

	@Override
	public Gene findRandomGeneOfLength(Chromosome chromosome, int beginIndex, int length) {
		Word word = null;

		/*
		 * Keep trying until we find a word with the right length. Not the most
		 * efficient way to do this, but there isn't a better way without a lot
		 * of redesign.
		 */
		do {
			word = wordListDao.findRandomWord();
		} while (word.getId().getWord().length() != length);

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
