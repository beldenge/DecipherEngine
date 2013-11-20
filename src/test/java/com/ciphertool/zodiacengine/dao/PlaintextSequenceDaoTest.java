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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.WordGene;

public class PlaintextSequenceDaoTest {
	private static PlaintextSequenceDao plaintextSequenceDao;

	@BeforeClass
	public static void setUp() {
		plaintextSequenceDao = new PlaintextSequenceDao();
	}

	@Test
	public void testFindRandomSequence() {
		Integer arbitraryInteger = 50;

		WordGene gene = new WordGene();
		PlaintextSequence sequence = (PlaintextSequence) plaintextSequenceDao.findRandomSequence(
				gene, arbitraryInteger);

		assertTrue(Character.isLetter(sequence.getValue().charAt(0)));
		assertSame(gene, sequence.getGene());
	}

	@Test
	public void testFindRandomSequenceNullGene() {
		Integer arbitraryInteger = 50;

		PlaintextSequence sequence = (PlaintextSequence) plaintextSequenceDao.findRandomSequence(
				null, arbitraryInteger);

		assertNull(sequence);
	}
}
