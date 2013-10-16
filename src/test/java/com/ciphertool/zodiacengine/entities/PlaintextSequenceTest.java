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

package com.ciphertool.zodiacengine.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class PlaintextSequenceTest {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(PlaintextSequenceTest.class);

	private static SolutionChromosome solutionChromosome = new SolutionChromosome();

	@Before
	public void resetSolutionChromosome() {
		Cipher cipher = new Cipher();
		solutionChromosome = new SolutionChromosome(cipher.getId(), 0, 0, 0, 0, 0);
	}

	@Test
	public void testClonePlaintextSequence() {
		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(0, "g", wordGene);

		PlaintextSequence clonedPlaintextSequence = plaintextSequence.clone();

		assertFalse(plaintextSequence == clonedPlaintextSequence);

		assertEquals(plaintextSequence.getPlaintextId(), clonedPlaintextSequence.getPlaintextId());
		assertEquals(plaintextSequence, clonedPlaintextSequence);

		/*
		 * The Gene should not be cloned.
		 */
		assertFalse(plaintextSequence.getGene() == null);
		assertTrue(clonedPlaintextSequence.getGene() == null);
	}

	@Test
	public void testShiftLeft() {
		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(5, "g", wordGene);

		plaintextSequence.shiftLeft(2);

		assertEquals(plaintextSequence.getPlaintextId(), new Integer(3));
	}

	@Test
	public void testShiftRight() {
		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(5, "g", wordGene);

		plaintextSequence.shiftRight(2);

		assertEquals(plaintextSequence.getPlaintextId(), new Integer(7));
	}
}
