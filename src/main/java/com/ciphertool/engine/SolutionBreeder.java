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

package com.ciphertool.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.SolutionChromosome;
import com.ciphertool.engine.entities.WordGene;
import com.ciphertool.genetics.Breeder;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sherlock.dao.WordListDao;
import com.ciphertool.sherlock.entities.Word;

public class SolutionBreeder implements Breeder {
	private Logger		log	= LoggerFactory.getLogger(getClass());

	private Cipher		cipher;
	private WordListDao	wordListDao;

	/**
	 * Default no-args constructor
	 */
	public SolutionBreeder() {
	}

	@Override
	public Chromosome breed() {
		// Set confidence levels to lowest possible
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher.getRows(),
				cipher.getColumns());

		populateGenes(solution);

		if (log.isDebugEnabled()) {
			log.debug(solution.toString());
		}

		return (Chromosome) solution;
	}

	private void populateGenes(SolutionChromosome solutionChromosome) {
		Word nextWord;
		WordGene nextGene;
		int length = 0;
		int cipherLength = cipher.length();

		do {
			nextWord = wordListDao.findRandomWord();

			nextGene = new WordGene(nextWord, solutionChromosome);

			length += nextGene.size();

			solutionChromosome.addGene(nextGene);

			/*
			 * Truncate the last WordGene if it exceeds the Cipher length.
			 */
			if (length > cipherLength) {
				for (int i = length; i > cipherLength; i--) {
					nextGene.removeSequence(nextGene.getSequences().get(nextGene.size() - 1));
				}
			}
		} while (length < cipherLength);
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
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
