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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

public class SolutionChromosomeTest {
	private static Logger log = Logger.getLogger(SolutionChromosomeTest.class);

	private static SolutionChromosome solutionChromosome = new SolutionChromosome();
	private static Cipher cipher = new Cipher("zodiac", 24, 17);

	@Before
	public void resetSolutionChromosome() {
		Cipher cipher = new Cipher();
		BigInteger cipherId = new BigInteger("12345");
		cipher.setId(cipherId);
		solutionChromosome = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher.getRows(),
				cipher.getColumns());
	}

	@Test
	public void testConstructor() {
		BigInteger cipherIdToSet = new BigInteger("999");
		int totalMatchesToSet = 1;
		int uniqueMatchesToSet = 2;
		int adjacentMatchesToSet = 3;
		int rowsToSet = 4;
		int columnsToSet = 5;

		SolutionChromosome solutionChromosome = new SolutionChromosome(cipherIdToSet,
				totalMatchesToSet, uniqueMatchesToSet, adjacentMatchesToSet, rowsToSet,
				columnsToSet);

		assertSame(cipherIdToSet, solutionChromosome.getCipherId());
		assertEquals(totalMatchesToSet, solutionChromosome.getTotalMatches());
		assertEquals(uniqueMatchesToSet, solutionChromosome.getUniqueMatches());
		assertEquals(adjacentMatchesToSet, solutionChromosome.getAdjacentMatches());
		assertEquals(rowsToSet, solutionChromosome.getRows());
		assertEquals(columnsToSet, solutionChromosome.getColumns());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullCipherId() {
		int totalMatchesToSet = 1;
		int uniqueMatchesToSet = 2;
		int adjacentMatchesToSet = 3;
		int rowsToSet = 4;
		int columnsToSet = 5;

		new SolutionChromosome(null, totalMatchesToSet, uniqueMatchesToSet, adjacentMatchesToSet,
				rowsToSet, columnsToSet);
	}

	@Test
	public void testSetId() {
		BigInteger idToSet = new BigInteger("999");

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setId(idToSet);

		assertSame(idToSet, solutionChromosome.getId());
	}

	@Test
	public void testSetSolutionSetId() {
		Integer solutionSetIdToSet = new Integer(999);

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setSolutionSetId(solutionSetIdToSet);

		assertSame(solutionSetIdToSet, solutionChromosome.getSolutionSetId());
	}

	@Test
	public void testSetCipherId() {
		BigInteger cipherIdToSet = new BigInteger("999");

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setCipherId(cipherIdToSet);

		assertSame(cipherIdToSet, solutionChromosome.getCipherId());
	}

	@Test
	public void testSetCreatedDate() {
		Date now = new Date();

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setCreatedDate(now);

		assertSame(now, solutionChromosome.getCreatedDate());
	}

	@Test
	public void testSetRows() {
		int rowsToSet = 2;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setRows(rowsToSet);

		assertEquals(rowsToSet, solutionChromosome.getRows());
	}

	@Test
	public void testSetColumns() {
		int columnsToSet = 3;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setColumns(columnsToSet);

		assertEquals(columnsToSet, solutionChromosome.getColumns());
	}

	@Test
	public void testSetTotalMatches() {
		int totalMatchesToSet = 1;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setTotalMatches(totalMatchesToSet);

		assertEquals(totalMatchesToSet, solutionChromosome.getTotalMatches());
	}

	@Test
	public void testSetUniqueMatches() {
		int uniqueMatchesToSet = 2;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setUniqueMatches(uniqueMatchesToSet);

		assertEquals(uniqueMatchesToSet, solutionChromosome.getUniqueMatches());
	}

	@Test
	public void testSetAdjacentMatches() {
		int adjacentMatchesToSet = 3;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setAdjacentMatches(adjacentMatchesToSet);

		assertEquals(adjacentMatchesToSet, solutionChromosome.getAdjacentMatches());
	}

	@Test
	public void testSetEvaluationNeeded() {
		boolean needsEvaluationToSet = false;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setEvaluationNeeded(needsEvaluationToSet);

		assertEquals(needsEvaluationToSet, solutionChromosome.isEvaluationNeeded());
	}

	@Test
	public void testEvaluationNeededDefault() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();
		assertTrue(solutionChromosome.isEvaluationNeeded());
	}

	@Test
	public void testSetFitness() {
		Double fitnessToSet = new Double(100.0);

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setFitness(fitnessToSet);

		assertSame(fitnessToSet, solutionChromosome.getFitness());
	}

	@Test
	public void testSetAge() {
		int ageToSet = 30;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setAge(ageToSet);

		assertEquals(ageToSet, solutionChromosome.getAge());
	}

	@Test
	public void testSetNumberOfChildren() {
		int numberOfChildrenToSet = 5;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setNumberOfChildren(numberOfChildrenToSet);

		assertEquals(numberOfChildrenToSet, solutionChromosome.getNumberOfChildren());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGenesUnmodifiable() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.addGene(new WordGene());

		List<Gene> genes = solutionChromosome.getGenes();
		genes.remove(0);
	}

	@Test
	public void testGetNullGenes() {
		// solutionChromosome should have been initialized in the @Before method
		assertNotNull(solutionChromosome.getGenes());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPlaintextCharactersUnmodifiable() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.addPlaintext(new PlaintextSequence());

		List<PlaintextSequence> plaintextCharacters = solutionChromosome.getPlaintextCharacters();
		plaintextCharacters.remove(0);
	}

	@Test
	public void testGetNullPlaintextCharacters() {
		// solutionChromosome should have been initialized in the @Before method
		assertNotNull(solutionChromosome.getPlaintextCharacters());
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
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setCipherId(null);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

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
				assertEquals(
						solutionChromosome.getPlaintextCharacters().get(count).getSequenceId(),
						count);

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
						.getSequenceId(), count);

				count++;
			}
		}
	}

	@Test
	public void testInsertGene() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setCipherId(cipher.getId());

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene3);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

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
				assertEquals(
						solutionChromosome.getPlaintextCharacters().get(count).getSequenceId(),
						count);

				assertEquals(gene.getSequences().get(j).getSequenceId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveGene() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
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
		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertEquals(wordGene2.size(), 5);

		solutionChromosome.removeGene(1);
		assertTrue(solutionChromosome.isEvaluationNeeded());
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
				assertEquals(
						solutionChromosome.getPlaintextCharacters().get(count).getSequenceId(),
						count);

				assertEquals(gene.getSequences().get(j).getSequenceId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testReplaceGene() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
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
		assertFalse(solutionChromosome.isEvaluationNeeded());

		solutionChromosome.replaceGene(1, wordGene2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

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
				assertEquals(
						solutionChromosome.getPlaintextCharacters().get(count).getSequenceId(),
						count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveGeneOnClonedSolution() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
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
		assertFalse(solutionChromosome.isEvaluationNeeded());

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();
		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertTrue(clonedSolutionChromosome.isEvaluationNeeded());

		/*
		 * Make the solution clean before checking for dirtiness after
		 * removeGene
		 */
		clonedSolutionChromosome.setFitness(0.0);
		assertFalse(clonedSolutionChromosome.isEvaluationNeeded());

		clonedSolutionChromosome.removeGene(1);
		assertTrue(clonedSolutionChromosome.isEvaluationNeeded());
		assertFalse(solutionChromosome.isEvaluationNeeded());

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
						.getSequenceId(), count);

				count++;
			}
		}

		assertEquals(clonedSolutionChromosome.actualSize().intValue(), clonedSolutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testEquals() {
		BigInteger baseId = new BigInteger("12345");
		Integer baseSolutionSetId = new Integer(678);
		BigInteger baseCipherId = new BigInteger("999");
		Date baseCreatedDate = new Date();
		int baseAge = 30;
		int baseNumberOfChildren = 1;
		Word word1 = new Word(new WordId("learn", 'N'));
		WordGene gene1 = new WordGene(word1, null, 0);
		Word word2 = new Word(new WordId("listen", 'N'));
		WordGene gene2 = new WordGene(word2, null, 5);
		Word word3 = new Word(new WordId("love", 'N'));
		WordGene gene3 = new WordGene(word3, null, 11);

		SolutionChromosome base = new SolutionChromosome();
		base.setId(baseId);
		base.setSolutionSetId(baseSolutionSetId);
		base.setCipherId(baseCipherId);
		base.setCreatedDate(baseCreatedDate);
		base.setAge(baseAge);
		base.setNumberOfChildren(baseNumberOfChildren);
		base.addGene(gene1.clone());
		base.addGene(gene2.clone());
		base.addGene(gene3.clone());

		SolutionChromosome solutionChromosomeEqualToBase = new SolutionChromosome();
		solutionChromosomeEqualToBase.setId(baseId);
		solutionChromosomeEqualToBase.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeEqualToBase.setCipherId(baseCipherId);
		solutionChromosomeEqualToBase.setCreatedDate(baseCreatedDate);
		solutionChromosomeEqualToBase.setAge(baseAge);
		solutionChromosomeEqualToBase.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeEqualToBase.addGene(gene1.clone());
		solutionChromosomeEqualToBase.addGene(gene2.clone());
		solutionChromosomeEqualToBase.addGene(gene3.clone());

		assertEquals(base, solutionChromosomeEqualToBase);

		SolutionChromosome solutionChromosomeWithDifferentId = new SolutionChromosome();
		solutionChromosomeWithDifferentId.setId(new BigInteger("54321"));
		solutionChromosomeWithDifferentId.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentId.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentId.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentId.setAge(baseAge);
		solutionChromosomeWithDifferentId.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentId.addGene(gene1.clone());
		solutionChromosomeWithDifferentId.addGene(gene2.clone());
		solutionChromosomeWithDifferentId.addGene(gene3.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentId));

		SolutionChromosome solutionChromosomeWithDifferentSolutionSetId = new SolutionChromosome();
		solutionChromosomeWithDifferentSolutionSetId.setId(baseId);
		solutionChromosomeWithDifferentSolutionSetId.setSolutionSetId(new Integer(876));
		solutionChromosomeWithDifferentSolutionSetId.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentSolutionSetId.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentSolutionSetId.setAge(baseAge);
		solutionChromosomeWithDifferentSolutionSetId.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentSolutionSetId.addGene(gene1.clone());
		solutionChromosomeWithDifferentSolutionSetId.addGene(gene2.clone());
		solutionChromosomeWithDifferentSolutionSetId.addGene(gene3.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentSolutionSetId));

		SolutionChromosome solutionChromosomeWithDifferentCipherId = new SolutionChromosome();
		solutionChromosomeWithDifferentCipherId.setId(baseId);
		solutionChromosomeWithDifferentCipherId.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentCipherId.setCipherId(new BigInteger("777"));
		solutionChromosomeWithDifferentCipherId.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentCipherId.setAge(baseAge);
		solutionChromosomeWithDifferentCipherId.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentCipherId.addGene(gene1.clone());
		solutionChromosomeWithDifferentCipherId.addGene(gene2.clone());
		solutionChromosomeWithDifferentCipherId.addGene(gene3.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentCipherId));

		SolutionChromosome solutionChromosomeWithDifferentCreatedDate = new SolutionChromosome();
		solutionChromosomeWithDifferentCreatedDate.setId(baseId);
		solutionChromosomeWithDifferentCreatedDate.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentCreatedDate.setCipherId(baseCipherId);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		solutionChromosomeWithDifferentCreatedDate.setCreatedDate(cal.getTime());
		solutionChromosomeWithDifferentCreatedDate.setAge(baseAge);
		solutionChromosomeWithDifferentCreatedDate.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentCreatedDate.addGene(gene1.clone());
		solutionChromosomeWithDifferentCreatedDate.addGene(gene2.clone());
		solutionChromosomeWithDifferentCreatedDate.addGene(gene3.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentCreatedDate));

		SolutionChromosome solutionChromosomeWithDifferentAge = new SolutionChromosome();
		solutionChromosomeWithDifferentAge.setId(baseId);
		solutionChromosomeWithDifferentAge.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentAge.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentAge.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentAge.setAge(32);
		solutionChromosomeWithDifferentAge.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentAge.addGene(gene1.clone());
		solutionChromosomeWithDifferentAge.addGene(gene2.clone());
		solutionChromosomeWithDifferentAge.addGene(gene3.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentAge));

		SolutionChromosome solutionChromosomeWithDifferentNumberOfChildren = new SolutionChromosome();
		solutionChromosomeWithDifferentNumberOfChildren.setId(baseId);
		solutionChromosomeWithDifferentNumberOfChildren.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentNumberOfChildren.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentNumberOfChildren.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentNumberOfChildren.setAge(baseAge);
		solutionChromosomeWithDifferentNumberOfChildren.setNumberOfChildren(2);
		solutionChromosomeWithDifferentNumberOfChildren.addGene(gene1.clone());
		solutionChromosomeWithDifferentNumberOfChildren.addGene(gene2.clone());
		solutionChromosomeWithDifferentNumberOfChildren.addGene(gene3.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentNumberOfChildren));

		SolutionChromosome solutionChromosomeWithDifferentGenes = new SolutionChromosome();
		solutionChromosomeWithDifferentGenes.setId(baseId);
		solutionChromosomeWithDifferentGenes.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentGenes.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentGenes.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentGenes.setAge(baseAge);
		solutionChromosomeWithDifferentGenes.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentGenes.addGene(gene3.clone());
		solutionChromosomeWithDifferentGenes.addGene(gene2.clone());
		solutionChromosomeWithDifferentGenes.addGene(gene1.clone());

		assertFalse(base.equals(solutionChromosomeWithDifferentGenes));

		SolutionChromosome solutionChromosomeWithDifferentPlaintextCharacters = new SolutionChromosome();
		solutionChromosomeWithDifferentPlaintextCharacters.setId(baseId);
		solutionChromosomeWithDifferentPlaintextCharacters.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentPlaintextCharacters.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentPlaintextCharacters.setCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentPlaintextCharacters.setAge(baseAge);
		solutionChromosomeWithDifferentPlaintextCharacters
				.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentPlaintextCharacters.addGene(gene1.clone());
		solutionChromosomeWithDifferentPlaintextCharacters.addGene(gene2.clone());
		solutionChromosomeWithDifferentPlaintextCharacters.addGene(gene3.clone());
		solutionChromosomeWithDifferentPlaintextCharacters.addPlaintext(new PlaintextSequence(15,
				"x", gene3));

		assertFalse(base.equals(solutionChromosomeWithDifferentPlaintextCharacters));

		SolutionChromosome solutionChromosomeWithNullPropertiesA = new SolutionChromosome();
		SolutionChromosome solutionChromosomeWithNullPropertiesB = new SolutionChromosome();
		assertEquals(solutionChromosomeWithNullPropertiesA, solutionChromosomeWithNullPropertiesB);
	}
}
