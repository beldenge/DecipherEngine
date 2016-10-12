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

package com.ciphertool.DecipherEngine.dao.cipherkey;

import com.ciphertool.genetics.dao.GeneDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.util.LetterUtils;
import com.ciphertool.DecipherEngine.entities.cipherkey.CipherKeyGene;

public class CipherKeyGeneDao implements GeneDao {

	@Override
	public Gene findRandomGene(Chromosome chromosome) {
		CipherKeyGene newGene = new CipherKeyGene(chromosome, String.valueOf(LetterUtils.getRandomLetter()));

		return newGene;
	}
}