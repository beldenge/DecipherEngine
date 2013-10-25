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

package com.ciphertool.zodiacengine.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class PlaintextSequenceTest {
	private static SolutionChromosome solutionChromosome = new SolutionChromosome();

	@Before
	public void resetSolutionChromosome() {
		Cipher cipher = new Cipher();
		cipher.setId(new BigInteger("123"));
		solutionChromosome = new SolutionChromosome(cipher.getId(), 0, 0, 0, 0, 0);
	}

	@Test
	public void testConstructor() {
		Integer sequenceIdToSet = new Integer(123);
		String valueToSet = "valueToSet";
		Word arbitraryWord = new Word(new WordId("arbitraryWord", 'N'));
		WordGene geneToSet = new WordGene(arbitraryWord, solutionChromosome, 5);
		PlaintextSequence plaintextSequence = new PlaintextSequence(sequenceIdToSet, valueToSet,
				geneToSet);

		assertSame(sequenceIdToSet, plaintextSequence.getSequenceId());
		assertEquals(valueToSet, plaintextSequence.getValue());
		assertSame(geneToSet, plaintextSequence.getGene());
	}

	@Test
	public void testSetGene() {
		Word arbitraryWord = new Word(new WordId("arbitraryWord", 'N'));
		WordGene geneToSet = new WordGene(arbitraryWord, solutionChromosome, 5);
		PlaintextSequence plaintextSequence = new PlaintextSequence();
		plaintextSequence.setGene(geneToSet);

		assertSame(geneToSet, plaintextSequence.getGene());
	}

	@Test
	public void testSetSequenceId() {
		Integer sequenceIdToSet = new Integer(123);
		PlaintextSequence plaintextSequence = new PlaintextSequence();
		plaintextSequence.setSequenceId(sequenceIdToSet);

		assertSame(sequenceIdToSet, plaintextSequence.getSequenceId());
	}

	@Test
	public void testSetValue() {
		String valueToSet = "valueToSet";
		PlaintextSequence plaintextSequence = new PlaintextSequence();

		// We have to set the Gene and Chromosome to satisfy the @Dirty aspect
		WordGene geneToSet = new WordGene();
		geneToSet.setChromosome(solutionChromosome);
		plaintextSequence.setGene(geneToSet);

		solutionChromosome.setEvaluationNeeded(false);

		plaintextSequence.setValue(valueToSet);

		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertEquals(valueToSet, plaintextSequence.getValue());
	}

	@Test
	public void testHasMatch() {
		PlaintextSequence plaintextSequence = new PlaintextSequence();
		plaintextSequence.setHasMatch(true);

		assertTrue(plaintextSequence.getHasMatch());
	}

	@Test
	public void testEquals() {
		Integer baseSequenceId = new Integer(123);
		String baseValue = "baseValue";

		PlaintextSequence base = new PlaintextSequence(baseSequenceId, baseValue, null);

		PlaintextSequence plaintextSequenceEqualToBase = new PlaintextSequence(baseSequenceId,
				baseValue, null);
		assertEquals(base, plaintextSequenceEqualToBase);

		PlaintextSequence plaintextSequenceWithDifferentSequenceId = new PlaintextSequence(321,
				baseValue, null);
		assertFalse(base.equals(plaintextSequenceWithDifferentSequenceId));

		PlaintextSequence plaintextSequenceWithDifferentValue = new PlaintextSequence(
				baseSequenceId, "differentWord", null);
		assertFalse(base.equals(plaintextSequenceWithDifferentValue));

		PlaintextSequence plaintextSequenceWithNullPropertiesA = new PlaintextSequence();
		PlaintextSequence plaintextSequenceWithNullPropertiesB = new PlaintextSequence();
		assertEquals(plaintextSequenceWithNullPropertiesA, plaintextSequenceWithNullPropertiesB);
	}

	@Test
	public void testClonePlaintextSequence() {
		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(0, "g", wordGene);
		plaintextSequence.setHasMatch(true);

		PlaintextSequence clonedPlaintextSequence = plaintextSequence.clone();

		assertNotSame(plaintextSequence, clonedPlaintextSequence);
		assertEquals(plaintextSequence, clonedPlaintextSequence);

		// HasMatch should always start false
		assertFalse(clonedPlaintextSequence.getHasMatch());
		assertEquals(plaintextSequence.getSequenceId(), clonedPlaintextSequence.getSequenceId());

		/*
		 * The Gene should not be cloned.
		 */
		assertNotNull(plaintextSequence.getGene());
		assertNull(clonedPlaintextSequence.getGene());
	}

	@Test
	public void testShiftLeft() {
		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(5, "g", wordGene);

		solutionChromosome.setEvaluationNeeded(false);

		plaintextSequence.shiftLeft(2);

		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertEquals(plaintextSequence.getSequenceId(), new Integer(3));
	}

	@Test
	public void testShiftRight() {
		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(5, "g", wordGene);

		solutionChromosome.setEvaluationNeeded(false);

		plaintextSequence.shiftRight(2);

		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertEquals(plaintextSequence.getSequenceId(), new Integer(7));
	}
}
