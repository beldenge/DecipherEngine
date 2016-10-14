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

package com.ciphertool.engine.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.entities.PlaintextSequence;
import com.ciphertool.genetics.dao.SequenceDao;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;
import com.ciphertool.sentencebuilder.util.LetterUtils;

public class PlaintextSequenceDao implements SequenceDao {
	private static Logger log = LoggerFactory.getLogger(PlaintextSequenceDao.class);

	@Override
	public Sequence findRandomSequence(Gene gene, int sequenceIndex) {
		if (gene == null) {
			log.warn("Attempted to find random sequence but the associated Gene is null.  Cannot continue, thus returning null.");

			return null;
		}

		Sequence sequence = new PlaintextSequence(String.valueOf(LetterUtils.getRandomLetter()), gene);

		return sequence;
	}
}
