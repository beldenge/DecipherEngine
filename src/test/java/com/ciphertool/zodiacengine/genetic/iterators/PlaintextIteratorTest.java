package com.ciphertool.zodiacengine.genetic.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class PlaintextIteratorTest {

	protected static SolutionChromosome knownSolution = new SolutionChromosome();

	private final static int TOTAL_SEQUENCES = 12;

	@BeforeClass
	public static void setUp() {
		knownSolution.setCipherId(BigInteger.ZERO);

		knownSolution.setRows(24);
		knownSolution.setColumns(17);

		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something
		 * arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word(new WordId("i", '*')), knownSolution, 0);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("like", '*')), knownSolution, 1);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killing", '*')), knownSolution, 5);
		knownSolution.addGene(nextWordGene);
	}

	@Test
	public void testNextAndHasNext() {
		Iterator<PlaintextSequence> plaintextIterator = knownSolution.iterator();

		PlaintextSequence nextPlaintextSequence;

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("i", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("l", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("i", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("k", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("e", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("k", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("i", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("l", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("l", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("i", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("n", nextPlaintextSequence.getValue());

		assertTrue(plaintextIterator.hasNext());
		nextPlaintextSequence = plaintextIterator.next();
		assertEquals("g", nextPlaintextSequence.getValue());

		assertFalse(plaintextIterator.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testNoNextElement() {
		Iterator<PlaintextSequence> plaintextIterator = knownSolution.iterator();

		for (int i = 0; i < TOTAL_SEQUENCES; i++) {
			assertTrue(plaintextIterator.hasNext());

			plaintextIterator.next();
		}

		plaintextIterator.next();
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptySolution() {
		SolutionChromosome emptySolution = new SolutionChromosome();
		Iterator<PlaintextSequence> plaintextIterator = emptySolution.iterator();

		plaintextIterator.next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {
		Iterator<PlaintextSequence> plaintextIterator = knownSolution.iterator();

		plaintextIterator.remove();
	}
}
