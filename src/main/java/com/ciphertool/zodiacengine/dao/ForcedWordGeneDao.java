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

package com.ciphertool.zodiacengine.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.dao.VariableLengthGeneDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class ForcedWordGeneDao implements VariableLengthGeneDao {
	private Logger log = Logger.getLogger(getClass());

	private WordListDao wordListDao;

	private static final int MAX_FIND_ATTEMPTS = 10000;

	@Override
	public Gene findRandomGene(Chromosome chromosome) {
		if (chromosome == null) {
			log.warn("Attempted to find random gene while specifying null chromosome.  Unable to continue, thus returning null.");

			return null;
		}

		Word word = wordListDao.findRandomWord();

		Gene gene = new WordGene(word, (SolutionChromosome) chromosome);

		return gene;
	}

	@Override
	public Gene findRandomGeneOfLength(Chromosome chromosome, int length) {
		if (chromosome == null) {
			log.warn("Attempted to find random gene while specifying null chromosome.  Unable to continue, thus returning null.");

			return null;
		}

		if (length < 1) {
			log.warn("Unable to find random gene of length " + length
					+ " since it is less than 1, which is obviously the minimum length.  Returning null.");

			return null;
		}

		Word word = null;

		/*
		 * Keep trying until we find a word with the right length. Or return null if we reach the max attempts.
		 * Otherwise there is a chance of infinite loop if a length of inordinate size is provided.
		 */
		int attempts = 0;
		do {
			word = wordListDao.findRandomWord();

			attempts++;

			if (attempts >= MAX_FIND_ATTEMPTS) {
				if (log.isDebugEnabled()) {
					log.debug("Unable to find random gene of length " + length + " after " + attempts
							+ " attempts.  Returning null.");
				}

				return null;
			}
		} while (word.getId().getWord().length() != length);

		Gene gene = new WordGene(word, (SolutionChromosome) chromosome);

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
