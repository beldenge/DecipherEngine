/**
 * Copyright 2013 George Belden
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

package com.ciphertool.zodiacengine.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.dao.GeneListDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.dao.WordMapDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class WordGeneListDao implements GeneListDao {
	private Logger log = Logger.getLogger(getClass());

	private WordListDao wordListDao;
	private WordMapDao wordMapDao;

	@Override
	public Gene findRandomGene(Chromosome chromosome, int beginIndex) {
		Word word = wordListDao.findRandomWord();

		Gene gene = new WordGene(word, (SolutionChromosome) chromosome, beginIndex);

		return gene;
	}

	@Override
	public Gene findRandomGeneOfLength(Chromosome chromosome, int beginIndex, int length) {
		if (length < 1) {
			log.warn("Unable to find random gene of length "
					+ length
					+ " since it is less than 1, which is obviously the minimum length.  Returning null.");

			return null;
		}

		Word word = wordMapDao.findRandomWordByLength(length);

		if (word == null) {
			throw new IllegalArgumentException("Attempted to find a word of length " + length
					+ " which was not found.");
		}

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

	/**
	 * @param wordMapDao
	 *            the wordMapDao to set
	 */
	@Required
	public void setWordMapDao(WordMapDao wordMapDao) {
		this.wordMapDao = wordMapDao;
	}
}
