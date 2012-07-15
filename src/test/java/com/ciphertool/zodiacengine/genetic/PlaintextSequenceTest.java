package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class PlaintextSequenceTest {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(PlaintextSequenceTest.class);

	@Test
	public void testClonePlaintextSequence() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(
				solutionChromosome, 0), "g", wordGene);

		PlaintextSequence clonedPlaintextSequence = plaintextSequence.clone();

		assertFalse(plaintextSequence.getPlaintextId() == clonedPlaintextSequence.getPlaintextId());

		/*
		 * The Solution should not be cloned.
		 */
		assertTrue(clonedPlaintextSequence.getPlaintextId().getSolution() == null);
		clonedPlaintextSequence.getPlaintextId().setSolution(solutionChromosome);

		assertFalse(plaintextSequence == clonedPlaintextSequence);

		assertEquals(plaintextSequence, clonedPlaintextSequence);

		/*
		 * The Gene should not be cloned.
		 */
		assertFalse(plaintextSequence.getGene() == null);
		assertTrue(clonedPlaintextSequence.getGene() == null);
	}

	@Test
	public void testShiftLeft() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(
				solutionChromosome, 5), "g", wordGene);

		plaintextSequence.shiftLeft(2);

		assertEquals(plaintextSequence.getPlaintextId().getCiphertextId(), 3);
	}

	@Test
	public void testShiftRight() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		Word word = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(
				solutionChromosome, 5), "g", wordGene);

		plaintextSequence.shiftRight(2);

		assertEquals(plaintextSequence.getPlaintextId().getCiphertextId(), 7);
	}
}
