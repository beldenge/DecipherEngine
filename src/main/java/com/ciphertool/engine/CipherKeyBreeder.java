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
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.Breeder;
import com.ciphertool.genetics.dao.GeneDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;

public class CipherKeyBreeder implements Breeder {
	private Logger					log		= LoggerFactory.getLogger(getClass());

	private GeneDao					geneDao;

	private static final String[]	KEYS	= { "a", "anchor", "b", "backc", "backd", "backe", "backf", "backj",
			"backk", "backl", "backp", "backq", "backr", "backslash", "box", "boxdot", "carrot", "circledot", "d", "e",
			"f", "flipt", "forslash", "fullbox", "fullcircle", "fulltri", "g", "h", "horstrike", "i", "j", "k", "l",
			"lrbox", "m", "n", "o", "p", "pi", "plus", "q", "r", "s", "t", "tri", "tridot", "u", "v", "vertstrike", "w",
			"x", "y", "z", "zodiac" };

	private Cipher					cipher;

	/**
	 * Default no-args constructor
	 */
	public CipherKeyBreeder() {
	}

	@Override
	public Chromosome breed() {
		CipherKeyChromosome chromosome = new CipherKeyChromosome(cipher);

		for (int i = 0; i < KEYS.length; i++) {
			// Should never happen, but we check just in case
			if (chromosome.actualSize() >= chromosome.targetSize()) {
				throw new IllegalStateException(
						"Attempted to add a Gene to CipherKeyChromosome, but the maximum number of Genes ("
								+ chromosome.targetSize() + ") have already been allocated.");
			}

			Gene newGene = geneDao.findRandomGene(chromosome);

			chromosome.putGene(KEYS[i], newGene);
		}

		if (log.isDebugEnabled()) {
			log.debug(chromosome.toString());
		}

		return chromosome;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
	}

	/**
	 * @param geneDao
	 *            the geneDao to set
	 */
	@Required
	public void setGeneDao(GeneDao geneDao) {
		this.geneDao = geneDao;
	}
}
