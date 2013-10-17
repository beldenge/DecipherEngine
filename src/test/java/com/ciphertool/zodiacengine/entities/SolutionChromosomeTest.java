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
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class SolutionChromosomeTest {
	private static Logger log = Logger.getLogger(SolutionChromosomeTest.class);

	private static SolutionChromosome solutionChromosome = new SolutionChromosome();
	private static Cipher cipher = new Cipher("zodiac", 24, 17);

	@Before
	public void resetSolutionChromosome() {
		Cipher cipher = new Cipher();
		solutionChromosome = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher.getRows(),
				cipher.getColumns());
	}

	@Test
	public void testGetNullPlaintextCharacters() {
		// solutionChromosome should have been initialized in the @Before method
		assertNotNull(solutionChromosome.getPlaintextCharacters());
	}

	@Test
	public void testGetNullGenes() {
		// solutionChromosome should have been initialized in the @Before method
		assertNotNull(solutionChromosome.getGenes());
	}

	@Test
	public void testActualSize() {
		assertEquals(solutionChromosome.actualSize().intValue(), 0);

		Word word = new Word(new WordId("smile", 'N'));

		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		solutionChromosome.addGene(wordGene);

		assertEquals(solutionChromosome.actualSize().intValue(), 5);

		solutionChromosome.addGene(wordGene);

		assertEquals(solutionChromosome.actualSize().intValue(), 10);
	}

	@Test
	public void testTargetSize() {
		solutionChromosome.setColumns(5);
		solutionChromosome.setRows(5);

		assertEquals(solutionChromosome.targetSize().intValue(), 25);
	}

	@Test
	public void testAddGene() {
		assertTrue(solutionChromosome.isDirty());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
		solutionChromosome.setCipherId(null);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);
		assertTrue(solutionChromosome.isDirty());

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);
		assertTrue(solutionChromosome.isDirty());

		assertEquals(solutionChromosome.getGenes().size(), 2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getId().getWord());

		Integer count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testCloneSolutionChromosome() {
		solutionChromosome.setSolutionSetId(null);
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipherId(null);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene3);

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();

		/*
		 * Make sure the Ciphers reference the same memory address since that
		 * Object is not cloned.
		 */
		assertTrue(solutionChromosome.getCipherId() == clonedSolutionChromosome.getCipherId());

		/*
		 * Make sure the Chromosomes reference different memory addresses.
		 */
		assertFalse(solutionChromosome == clonedSolutionChromosome);

		/*
		 * Make sure the content of the Chromosomes are equal.
		 */
		assertEquals(solutionChromosome, clonedSolutionChromosome);

		/*
		 * Make sure the WordGenes reference different memory addresses.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertFalse(solutionChromosome.getGenes().get(i) == clonedSolutionChromosome.getGenes()
					.get(i));
		}

		/*
		 * Make sure the content of the WordGenes are equal.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertEquals(solutionChromosome.getGenes().get(i), clonedSolutionChromosome.getGenes()
					.get(i));
		}

		/*
		 * Make sure the Sequences reference different memory addresses.
		 */
		for (int i = 0; i < solutionChromosome.actualSize(); i++) {
			assertFalse(solutionChromosome.getPlaintextCharacters().get(i) == clonedSolutionChromosome
					.getPlaintextCharacters().get(i));
		}

		/*
		 * Make sure the content of the Sequences are equal.
		 */
		for (int i = 0; i < solutionChromosome.actualSize(); i++) {
			assertEquals(solutionChromosome.getPlaintextCharacters().get(i),
					clonedSolutionChromosome.getPlaintextCharacters().get(i));
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		Integer count = 0;
		for (Gene gene : clonedSolutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(clonedSolutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				assertEquals(clonedSolutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId(), count);

				count++;
			}
		}
	}

	@Test
	public void testInsertGene() {
		assertTrue(solutionChromosome.isDirty());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
		solutionChromosome.setCipherId(cipher.getId());

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene3);
		assertTrue(solutionChromosome.isDirty());

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);
		assertTrue(solutionChromosome.isDirty());

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene2);
		assertTrue(solutionChromosome.isDirty());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getId().getWord());

		log.info("Gene 3: " + ((WordGene) solutionChromosome.getGenes().get(2)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(2)).getWordString(), word3
				.getId().getWord());

		Integer count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveGene() {
		assertTrue(solutionChromosome.isDirty());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
		solutionChromosome.setCipherId(cipher.getId());

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene2);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(2, wordGene3);

		assertEquals(solutionChromosome.getGenes().size(), 3);

		/*
		 * Make the solution clean before checking for dirtiness after
		 * removeGene
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
		assertEquals(wordGene2.size(), 5);

		solutionChromosome.removeGene(1);
		assertTrue(solutionChromosome.isDirty());
		assertEquals(wordGene2.size(), 0);

		assertEquals(solutionChromosome.getGenes().size(), 2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word3
				.getId().getWord());

		Integer count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count), gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testReplaceGene() {
		assertTrue(solutionChromosome.isDirty());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
		solutionChromosome.setCipherId(cipher.getId());

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene3);

		int beforeSize = solutionChromosome.getGenes().size();

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);

		/*
		 * Make the solution clean before checking for dirtiness after
		 * replaceGene
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());

		solutionChromosome.replaceGene(1, wordGene2);
		assertTrue(solutionChromosome.isDirty());

		assertEquals(solutionChromosome.getGenes().size(), beforeSize);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getId().getWord());

		Integer count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveGeneOnClonedSolution() {
		assertTrue(solutionChromosome.isDirty());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());
		solutionChromosome.setCipherId(cipher.getId());

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene3);

		/*
		 * Make the solution clean before checking for dirtiness after clone
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isDirty());

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();
		assertFalse(solutionChromosome.isDirty());
		assertTrue(clonedSolutionChromosome.isDirty());

		/*
		 * Make the solution clean before checking for dirtiness after
		 * removeGene
		 */
		clonedSolutionChromosome.setFitness(0.0);
		assertFalse(clonedSolutionChromosome.isDirty());

		clonedSolutionChromosome.removeGene(1);
		assertTrue(clonedSolutionChromosome.isDirty());
		assertFalse(solutionChromosome.isDirty());

		log.info("Gene 1: "
				+ ((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		Integer count = 0;
		for (Gene gene : clonedSolutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(clonedSolutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(clonedSolutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(clonedSolutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId(), count);

				count++;
			}
		}

		assertEquals(clonedSolutionChromosome.actualSize().intValue(), clonedSolutionChromosome
				.getPlaintextCharacters().size());
	}
}
