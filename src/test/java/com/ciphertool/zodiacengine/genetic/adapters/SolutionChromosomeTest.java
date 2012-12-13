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

package com.ciphertool.zodiacengine.genetic.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.SolutionId;

public class SolutionChromosomeTest {
	private static Logger log = Logger.getLogger(SolutionChromosomeTest.class);

	private static Cipher cipher = new Cipher("zodiac", 24, 17);

	@Test
	public void testGetNullPlaintextCharacters() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertNotNull(solutionChromosome.getPlaintextCharacters());
	}

	@Test
	public void testActualSize() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

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
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		Cipher cipher = new Cipher();

		solutionChromosome.setCipher(cipher);

		cipher.setColumns(5);
		cipher.setRows(5);

		assertEquals(solutionChromosome.targetSize().intValue(), 25);
	}

	@Test
	public void testAddGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipher(new Cipher());

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		assertEquals(solutionChromosome.getGenes().size(), 2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testCloneSolutionChromosome() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);
		solutionChromosome.setId(new SolutionId());
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipher(new Cipher());

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
		assertTrue(solutionChromosome.getCipher() == clonedSolutionChromosome.getCipher());

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

		int count = 0;
		for (Gene gene : clonedSolutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(clonedSolutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				assertEquals(clonedSolutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				count++;
			}
		}
	}

	@Test
	public void testInsertGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipher(cipher);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene3);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getId().getWord());

		log.info("Gene 3: " + ((WordGene) solutionChromosome.getGenes().get(2)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(2)).getWordString(), word3
				.getId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipher(cipher);

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

		solutionChromosome.removeGene(1);

		assertEquals(solutionChromosome.getGenes().size(), 2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word3
				.getId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testReplaceGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene3);

		int beforeSize = solutionChromosome.getGenes().size();

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);

		solutionChromosome.replaceGene(1, wordGene2);

		assertEquals(solutionChromosome.getGenes().size(), beforeSize);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveGeneOnClonedSolution() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);
		solutionChromosome.setFitness(0.0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene3);

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();

		clonedSolutionChromosome.removeGene(1);

		log.info("Gene 1: "
				+ ((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString(), word1
				.getId().getWord());

		int count = 0;
		for (Gene gene : clonedSolutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(clonedSolutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(clonedSolutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(clonedSolutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(clonedSolutionChromosome.actualSize().intValue(), clonedSolutionChromosome
				.getPlaintextCharacters().size());
	}
}
