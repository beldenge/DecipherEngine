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

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class WordGeneTest {
	private static Logger log = Logger.getLogger(WordGeneTest.class);

	private static Word word = new Word(new WordId("smile", 'N'));
	private static int beginCiphertextIndex = 0;
	private static SolutionChromosome solutionChromosome = new SolutionChromosome();

	@BeforeClass
	public static void setUp() {
		solutionChromosome.setSolutionSetId(null);
		assertTrue(solutionChromosome.isEvaluationNeeded());
	}

	@Before
	public void resetDirtiness() {
		solutionChromosome = new SolutionChromosome();
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
	}

	@Test
	public void testConstructor() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertEquals(solutionChromosome.actualSize(), new Integer(0));
		assertSame(solutionChromosome, wordGene.getChromosome());
		assertEquals(wordGene.size(), word.getId().getWord().length());

		assertEquals("s", wordGene.getSequences().get(0).getValue());
		assertEquals("m", wordGene.getSequences().get(1).getValue());
		assertEquals("i", wordGene.getSequences().get(2).getValue());
		assertEquals("l", wordGene.getSequences().get(3).getValue());
		assertEquals("e", wordGene.getSequences().get(4).getValue());

		assertEquals(new Integer(0), wordGene.getSequences().get(0).getSequenceId());
		assertEquals(new Integer(1), wordGene.getSequences().get(1).getSequenceId());
		assertEquals(new Integer(2), wordGene.getSequences().get(2).getSequenceId());
		assertEquals(new Integer(3), wordGene.getSequences().get(3).getSequenceId());
		assertEquals(new Integer(4), wordGene.getSequences().get(4).getSequenceId());
	}

	@Test
	public void testConstructorNullWord() {
		WordGene wordGene = new WordGene(null, solutionChromosome, beginCiphertextIndex);

		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertEquals(solutionChromosome.actualSize(), new Integer(0));
		assertSame(solutionChromosome, wordGene.getChromosome());
		assertEquals(0, wordGene.size());
	}

	@Test
	public void testConstructorNullWordId() {
		Word wordWithNullId = new Word(null);
		WordGene wordGene = new WordGene(wordWithNullId, solutionChromosome, beginCiphertextIndex);

		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertEquals(solutionChromosome.actualSize(), new Integer(0));
		assertSame(solutionChromosome, wordGene.getChromosome());
		assertEquals(0, wordGene.size());
	}

	@Test
	public void testConstructorNullWordString() {
		Word wordWithNullId = new Word(new WordId(null, 'N'));
		WordGene wordGene = new WordGene(wordWithNullId, solutionChromosome, beginCiphertextIndex);

		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertEquals(solutionChromosome.actualSize(), new Integer(0));
		assertSame(solutionChromosome, wordGene.getChromosome());
		assertEquals(0, wordGene.size());
	}

	@Test
	public void testSetChromosome() {
		WordGene wordGene = new WordGene();
		wordGene.setChromosome(solutionChromosome);

		assertSame(solutionChromosome, wordGene.getChromosome());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSequencesUnmodifiable() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		List<Sequence> sequences = wordGene.getSequences();
		sequences.remove(0); // should throw exception
	}

	@Test
	public void getNullPlaintextSequences() {
		// solutionChromosome should have been initialized in the @Before method
		assertNotNull(solutionChromosome.getPlaintextCharacters());
	}

	@Test
	public void testAddInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.addSequence(null);
	}

	@Test
	public void testAddSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int ciphertextId = wordGene.size();

		PlaintextSequence newPlaintextSequence = new PlaintextSequence(ciphertextId, "y", wordGene);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * addSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		wordGene.addSequence(newPlaintextSequence);
		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertEquals(wordGene, newPlaintextSequence.getGene());

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertSame(solutionChromosome.getPlaintextCharacters().get(ciphertextId),
				newPlaintextSequence);

		validateByLoopThroughSequencesAndGenes(solutionChromosome, wordGene);
	}

	@Test
	public void testDoAddInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.doAddSequence(null);
	}

	@Test
	public void testDoAddSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int ciphertextId = wordGene.size();

		PlaintextSequence newPlaintextSequence = new PlaintextSequence(ciphertextId, "y", wordGene);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * addSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		wordGene.doAddSequence(newPlaintextSequence);
		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertEquals(wordGene, newPlaintextSequence.getGene());

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertSame(solutionChromosome.getPlaintextCharacters().get(ciphertextId),
				newPlaintextSequence);

		validateByLoopThroughSequencesAndGenes(solutionChromosome, wordGene);
	}

	@Test
	public void testInsertInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.insertSequence(0, null);
	}

	@Test
	public void testInsertSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int ciphertextId = 0;

		PlaintextSequence newPlaintextSequence = new PlaintextSequence(ciphertextId, "h", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * insertSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		wordGene.insertSequence(ciphertextId, newPlaintextSequence);
		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertEquals(wordGene, newPlaintextSequence.getGene());

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertSame(solutionChromosome.getPlaintextCharacters().get(ciphertextId),
				newPlaintextSequence);

		validateByLoopThroughSequencesAndGenes(solutionChromosome, wordGene);

	}

	@Test
	public void testRemoveInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.removeSequence(null);
	}

	@Test
	public void testRemoveSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int ciphertextId = 1;

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * removeSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		wordGene.removeSequence(wordGene.getSequences().get(ciphertextId));
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(wordGene.size(), geneSizeBefore - 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore - 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "l");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "e");

		validateByLoopThroughSequencesAndGenes(solutionChromosome, wordGene);
	}

	@Test
	public void testReplaceInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.replaceSequence(0, null);
	}

	@Test
	public void testReplaceSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		int ciphertextId = 0;

		PlaintextSequence newPlaintextSequence = new PlaintextSequence(ciphertextId, "h", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * replaceSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		String valueBefore = ((PlaintextSequence) wordGene.getSequences().get(ciphertextId))
				.getValue();
		wordGene.replaceSequence(ciphertextId, newPlaintextSequence);
		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertFalse(valueBefore.equals(newPlaintextSequence.getValue()));
		assertEquals(wordGene, newPlaintextSequence.getGene());

		assertEquals(wordGene.size(), geneSizeBefore);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertSame(solutionChromosome.getPlaintextCharacters().get(ciphertextId),
				newPlaintextSequence);

		validateByLoopThroughSequencesAndGenes(solutionChromosome, wordGene);
	}

	/**
	 * Reusable method for validating Lists of Sequnces by looping through the
	 * List of Sequences at the Chromosome level and also by individually
	 * looping through the Genes.
	 * 
	 * @param solutionChromosome
	 *            the SolutionChromosome to validate
	 * @param wordGene
	 *            the WordGene to validate
	 */
	private void validateByLoopThroughSequencesAndGenes(SolutionChromosome solutionChromosome,
			WordGene wordGene) {
		// Validate by looping through the Sequences
		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));

			assertSame(solutionChromosome.getPlaintextCharacters().get(i), wordGene.getSequences()
					.get(i));

			assertEquals(solutionChromosome.getPlaintextCharacters().get(i).getSequenceId()
					.intValue(), i);
		}

		// Validate by looping through the Genes
		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertSame(solutionChromosome.getPlaintextCharacters().get(count), gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getSequenceId()
						.intValue(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);
				assertEquals(gene.getSequences().get(j).getValue(), solutionChromosome
						.getPlaintextCharacters().get(count).getValue());

				count++;
			}
		}
	}

	@Test
	public void testResetSequences() {
		PlaintextSequence sequence1 = new PlaintextSequence(0, "a", null);
		PlaintextSequence sequence2 = new PlaintextSequence(1, "b", null);
		PlaintextSequence sequence3 = new PlaintextSequence(2, "c", null);

		WordGene base = new WordGene();
		base.setChromosome(solutionChromosome);
		base.addSequence(sequence1);
		base.addSequence(sequence2);
		base.addSequence(sequence3);

		assertEquals(3, base.size());
		assertSame(sequence1, base.getSequences().get(0));
		assertSame(sequence2, base.getSequences().get(1));
		assertSame(sequence3, base.getSequences().get(2));

		base.resetSequences();

		assertNotNull(base.getSequences());
		assertEquals(0, base.getSequences().size());
	}

	@Test
	public void testCloneWordGene() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		WordGene clonedWordGene = wordGene.clone();

		/*
		 * Make sure the WordGenes reference different memory addresses.
		 */
		assertNotSame(wordGene, clonedWordGene);

		/*
		 * Make sure the content of the WordGenes are equal.
		 */
		assertEquals(wordGene, clonedWordGene);

		/*
		 * Make sure the Chromosome is null since that should not be cloned.
		 */
		assertNull(clonedWordGene.getChromosome());

		/*
		 * Make sure the Sequences reference different memory addresses.
		 */
		for (int i = 0; i < wordGene.size(); i++) {
			assertNotSame(wordGene.getSequences().get(i), clonedWordGene.getSequences().get(i));
		}

		/*
		 * Make sure the content of the Sequences are equal.
		 */
		for (int i = 0; i < wordGene.size(); i++) {
			assertEquals(wordGene.getSequences().get(i), clonedWordGene.getSequences().get(i));
		}
	}

	@Test
	public void testSize() {
		Word george = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(george, solutionChromosome, beginCiphertextIndex);

		assertEquals(6, wordGene.size());
	}

	@Test
	public void testGetWordString() {
		Word george = new Word(new WordId("george", 'N'));
		WordGene wordGene = new WordGene(george, solutionChromosome, beginCiphertextIndex);

		assertEquals("george", wordGene.getWordString());
	}

	@Test
	public void testCountMatches() {
		Word newWord = new Word(new WordId("smile", 'N'));
		WordGene wordGeneToTest = new WordGene(newWord, null, 0);
		((PlaintextSequence) wordGeneToTest.getSequences().get(1)).setHasMatch(true);
		((PlaintextSequence) wordGeneToTest.getSequences().get(2)).setHasMatch(true);

		assertEquals(2, wordGeneToTest.countMatches());
	}

	@Test
	public void testEquals() {
		PlaintextSequence sequence1 = new PlaintextSequence(0, "a", null);
		PlaintextSequence sequence2 = new PlaintextSequence(1, "b", null);
		PlaintextSequence sequence3 = new PlaintextSequence(2, "c", null);

		WordGene base = new WordGene();
		base.setChromosome(solutionChromosome);
		base.addSequence(sequence1.clone());
		base.addSequence(sequence2.clone());
		base.addSequence(sequence3.clone());

		WordGene wordGeneEqualToBase = new WordGene();
		wordGeneEqualToBase.setChromosome(solutionChromosome);
		wordGeneEqualToBase.addSequence(sequence1.clone());
		wordGeneEqualToBase.addSequence(sequence2.clone());
		wordGeneEqualToBase.addSequence(sequence3.clone());

		assertEquals(wordGeneEqualToBase, base);

		WordGene wordGeneWithDifferentSequences = new WordGene();
		wordGeneWithDifferentSequences.setChromosome(solutionChromosome);
		wordGeneWithDifferentSequences.addSequence(sequence3.clone());
		wordGeneWithDifferentSequences.addSequence(sequence2.clone());
		wordGeneWithDifferentSequences.addSequence(sequence1.clone());

		assertFalse(wordGeneWithDifferentSequences.equals(base));

		WordGene wordGeneWithNullPropertiesA = new WordGene();
		WordGene wordGeneWithNullPropertiesB = new WordGene();
		assertEquals(wordGeneWithNullPropertiesA, wordGeneWithNullPropertiesB);
	}
}