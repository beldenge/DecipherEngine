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
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class WordGeneTest {
	private static Logger log = Logger.getLogger(WordGeneTest.class);

	private static Word word = new Word(new WordId("smile", 'N'));
	private static int beginCiphertextIndex = 0;
	private static SolutionChromosome solutionChromosome = new SolutionChromosome();

	@BeforeClass
	public static void setUp() {
		solutionChromosome.setSolutionSetId(null);
		assertTrue(solutionChromosome.isDirty());
	}

	@Before
	public void resetDirtiness() {
		solutionChromosome = new SolutionChromosome();
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
	}

	@Test
	public void getNullPlaintextSequences() {
		// solutionChromosome should have been initialized in the @Before method
		assertNotNull(solutionChromosome.getPlaintextCharacters());
	}

	@Test
	public void testConstructor() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isDirty());

		assertEquals(solutionChromosome.actualSize(), new Integer(0));

		assertTrue(solutionChromosome == wordGene.getChromosome());

		assertEquals(wordGene.size(), word.getId().getWord().length());
	}

	@Test
	public void testCloneWordGene() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isDirty());

		WordGene clonedWordGene = wordGene.clone();

		/*
		 * Make sure the WordGenes reference different memory addresses.
		 */
		assertFalse(wordGene == clonedWordGene);

		/*
		 * Make sure the content of the WordGenes are equal.
		 */
		assertEquals(wordGene, clonedWordGene);

		/*
		 * Make sure the Chromosomes reference different memory addresses even
		 * though that Object is not cloned.
		 */
		assertFalse(wordGene.getChromosome() == clonedWordGene.getChromosome());

		/*
		 * Make sure the Sequences reference different memory addresses.
		 */
		for (int i = 0; i < wordGene.size(); i++) {
			assertFalse(wordGene.getSequences().get(i) == clonedWordGene.getSequences().get(i));
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

		assertEquals(wordGene.size(), george.getId().getWord().length());
	}

	@Test
	public void testAddSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isDirty());

		int ciphertextId = wordGene.size();

		PlaintextSequence plaintextSequence = new PlaintextSequence(ciphertextId, "y", wordGene);
		assertFalse(solutionChromosome.isDirty());

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * addSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());

		wordGene.addSequence(plaintextSequence);
		assertTrue(solutionChromosome.isDirty());

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertTrue(solutionChromosome.getPlaintextCharacters().get(ciphertextId) == plaintextSequence);

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().intValue(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}
	}

	@Test
	public void testInsertSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isDirty());

		int ciphertextId = 0;

		PlaintextSequence plaintextSequence = new PlaintextSequence(ciphertextId, "h", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * insertSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());

		wordGene.insertSequence(ciphertextId, plaintextSequence);
		assertTrue(solutionChromosome.isDirty());

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertTrue(solutionChromosome.getPlaintextCharacters().get(ciphertextId) == plaintextSequence);

		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));
			/*
			 * We don't test the ciphertextId since that should only be updated
			 * when adding the Gene back to the SolutionChromosome.
			 */

			assertTrue(solutionChromosome.getPlaintextCharacters().get(i) == wordGene
					.getSequences().get(i));
		}

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().intValue(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);
				assertEquals(gene.getSequences().get(j).getValue(), solutionChromosome
						.getPlaintextCharacters().get(count).getValue());

				count++;
			}
		}

	}

	@Test
	public void testRemoveInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.removeSequence(null);
	}

	@Test
	public void testRemoveSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isDirty());

		int ciphertextId = 1;

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * removeSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());

		wordGene.removeSequence(wordGene.getSequences().get(ciphertextId));
		assertTrue(solutionChromosome.isDirty());

		assertEquals(wordGene.size(), geneSizeBefore - 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore - 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "l");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "e");

		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));
			/*
			 * We don't test the ciphertextId since that should only be updated
			 * when removing the Gene from the SolutionChromosome.
			 */

			assertTrue(solutionChromosome.getPlaintextCharacters().get(i) == wordGene
					.getSequences().get(i));
		}

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().intValue(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}
	}

	@Test
	public void testReplaceInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.replaceSequence(0, null);
	}

	@Test
	public void testReplaceSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		assertFalse(solutionChromosome.isDirty());

		int ciphertextId = 0;

		PlaintextSequence plaintextSequence = new PlaintextSequence(ciphertextId, "h", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		/*
		 * Make the solution clean before checking for dirtiness after
		 * replaceSequence
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());

		String valueBefore = ((PlaintextSequence) wordGene.getSequences().get(ciphertextId))
				.getValue();
		wordGene.replaceSequence(ciphertextId, plaintextSequence);
		assertTrue(solutionChromosome.isDirty());
		assertFalse(valueBefore.equals(plaintextSequence.getValue()));

		assertEquals(wordGene.size(), geneSizeBefore);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertTrue(solutionChromosome.getPlaintextCharacters().get(ciphertextId) == plaintextSequence);

		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));
			assertEquals(solutionChromosome.getPlaintextCharacters().get(i).getPlaintextId()
					.intValue(), i);

			assertTrue(solutionChromosome.getPlaintextCharacters().get(i) == wordGene
					.getSequences().get(i));
		}
	}

	@Test
	public void testCountMatches() {
		Word newWord = new Word(new WordId("smile", 'N'));
		WordGene wordGeneToTest = new WordGene(newWord, null, 0);
		((PlaintextSequence) wordGeneToTest.getSequences().get(1)).setHasMatch(true);
		((PlaintextSequence) wordGeneToTest.getSequences().get(2)).setHasMatch(true);

		assertEquals(2, wordGeneToTest.countMatches());
	}
}
