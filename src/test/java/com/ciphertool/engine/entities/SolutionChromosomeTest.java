/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;
import com.ciphertool.genetics.entities.VariableLengthGene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class SolutionChromosomeTest {
	private static SolutionChromosome solutionChromosome = new SolutionChromosome();
	private static Cipher cipher = new Cipher("zodiac", 24, 17);
	private static Logger logMock;

	private static final int CIPHER_SIZE = 408;

	@BeforeClass
	public static void setUp() {
		BigInteger cipherId = new BigInteger("12345");
		cipher.setId(cipherId);

		logMock = mock(Logger.class);

		Field logField = ReflectionUtils.findField(SolutionChromosome.class, "log");
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, null, logMock);
	}

	@Before
	public void resetDependencies() {
		solutionChromosome = new SolutionChromosome(cipher.getId(), 0, 0, 0, cipher.getRows(), cipher.getColumns());

		reset(logMock);
	}

	@Test
	public void testConstructor() {
		BigInteger cipherIdToSet = new BigInteger("999");
		int totalMatchesToSet = 1;
		int uniqueMatchesToSet = 2;
		int adjacentMatchesToSet = 3;
		int rowsToSet = 4;
		int columnsToSet = 5;

		SolutionChromosome solutionChromosome = new SolutionChromosome(cipherIdToSet, totalMatchesToSet,
				uniqueMatchesToSet, adjacentMatchesToSet, rowsToSet, columnsToSet);

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

		new SolutionChromosome(null, totalMatchesToSet, uniqueMatchesToSet, adjacentMatchesToSet, rowsToSet,
				columnsToSet);
	}

	@Test
	public void testSetId() {
		String idToSet = "999";

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
		solutionChromosome.setDatabaseCreatedDate(now);

		assertSame(now, solutionChromosome.getDatabaseCreatedDate());
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
	public void testIncreaseAge() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertEquals(0, solutionChromosome.getAge());

		solutionChromosome.increaseAge();

		assertEquals(1, solutionChromosome.getAge());

		solutionChromosome.increaseAge();

		assertEquals(2, solutionChromosome.getAge());
	}

	@Test
	public void testSetNumberOfChildren() {
		int numberOfChildrenToSet = 5;

		SolutionChromosome solutionChromosome = new SolutionChromosome();
		solutionChromosome.setNumberOfChildren(numberOfChildrenToSet);

		assertEquals(numberOfChildrenToSet, solutionChromosome.getNumberOfChildren());
	}

	@Test
	public void testIncreaseNumberOfChildren() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertEquals(0, solutionChromosome.getNumberOfChildren());

		solutionChromosome.increaseNumberOfChildren();

		assertEquals(1, solutionChromosome.getNumberOfChildren());

		solutionChromosome.increaseNumberOfChildren();

		assertEquals(2, solutionChromosome.getNumberOfChildren());
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
		assertEquals(0, solutionChromosome.actualSize().intValue());

		Word word = new Word("smile", PartOfSpeechType.NOUN);

		WordGene wordGene = new WordGene(word, solutionChromosome);

		solutionChromosome.addGene(wordGene);

		assertEquals(5, solutionChromosome.actualSize().intValue());

		solutionChromosome.addGene(wordGene);

		assertEquals(10, solutionChromosome.actualSize().intValue());
	}

	@Test
	public void testTargetSize() {
		assertEquals(CIPHER_SIZE, solutionChromosome.targetSize().intValue());

		solutionChromosome.setColumns(5);
		solutionChromosome.setRows(5);

		assertEquals(25, solutionChromosome.targetSize().intValue());
	}

	@Test
	public void testAddGene() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, solutionChromosome);
		solutionChromosome.addGene(wordGene1);
		assertSame(solutionChromosome, wordGene1.getChromosome());
		assertTrue(solutionChromosome.isEvaluationNeeded());

		Word word2 = new Word("belden", PartOfSpeechType.NOUN);
		WordGene wordGene2 = new WordGene(word2, solutionChromosome);
		solutionChromosome.addGene(wordGene2);
		assertSame(solutionChromosome, wordGene2.getChromosome());
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(2, solutionChromosome.getGenes().size());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1.getWord());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2.getWord());

		validateSequencesAndGenes(solutionChromosome);

		for (PlaintextSequence sequence : solutionChromosome.getPlaintextCharacters()) {
			assertFalse(sequence.getHasMatch());
		}

		validateHasMatchValues(wordGene1, false);
		validateHasMatchValues(wordGene2, false);
	}

	@Test
	public void testAddInvalidGene() {
		solutionChromosome.addGene(null);
		assertEquals(0, solutionChromosome.getGenes().size());
	}

	@Test
	public void testInsertGene() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		WordGene wordGene3 = new WordGene(word3, solutionChromosome);
		solutionChromosome.insertGene(0, wordGene3);
		assertSame(solutionChromosome, wordGene3.getChromosome());
		assertTrue(solutionChromosome.isEvaluationNeeded());

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, solutionChromosome);
		solutionChromosome.insertGene(0, wordGene1);
		assertSame(solutionChromosome, wordGene1.getChromosome());
		assertTrue(solutionChromosome.isEvaluationNeeded());

		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		WordGene wordGene2 = new WordGene(word2, solutionChromosome);
		solutionChromosome.insertGene(1, wordGene2);
		assertSame(solutionChromosome, wordGene2.getChromosome());
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1.getWord());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2.getWord());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(2)).getWordString(), word3.getWord());

		validateSequencesAndGenes(solutionChromosome);

		validateHasMatchValues(wordGene1, false);
		validateHasMatchValues(wordGene2, false);
		validateHasMatchValues(wordGene3, false);
	}

	@Test
	public void testInsertInvalidGene() {
		solutionChromosome.insertGene(0, null);
		assertEquals(0, solutionChromosome.getGenes().size());
	}

	@Test
	public void testRemoveGene() {
		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, solutionChromosome);
		solutionChromosome.insertGene(0, wordGene1);

		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		WordGene wordGene2 = new WordGene(word2, solutionChromosome);
		solutionChromosome.insertGene(1, wordGene2);

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		WordGene wordGene3 = new WordGene(word3, solutionChromosome);
		solutionChromosome.insertGene(2, wordGene3);

		assertEquals(3, solutionChromosome.getGenes().size());

		/*
		 * Make the solution clean before checking for dirtiness after removeGene
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		assertEquals(5, wordGene2.size());

		solutionChromosome.removeGene(1);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(2, solutionChromosome.getGenes().size());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1.getWord());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word3.getWord());

		validateSequencesAndGenes(solutionChromosome);
	}

	@Test
	public void testRemoveInvalidGene() {
		assertNull(solutionChromosome.removeGene(0));
	}

	@Test
	public void testRemoveGeneOutOfBounds() {
		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGeneOriginal = new WordGene(word1, solutionChromosome);
		solutionChromosome.addGene(wordGeneOriginal);

		solutionChromosome.removeGene(1);

		assertSame(wordGeneOriginal, solutionChromosome.getGenes().get(0));
		assertEquals(1, solutionChromosome.getGenes().size());
	}

	@Test
	public void testReplaceGene() {
		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, solutionChromosome);
		solutionChromosome.insertGene(0, wordGene1);

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		WordGene wordGene3 = new WordGene(word3, solutionChromosome);
		solutionChromosome.insertGene(1, wordGene3);

		int beforeSize = solutionChromosome.getGenes().size();

		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		WordGene wordGene2 = new WordGene(word2, solutionChromosome);

		/*
		 * Make the solution clean before checking for dirtiness after replaceGene
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		solutionChromosome.replaceGene(1, wordGene2);
		assertTrue(solutionChromosome.isEvaluationNeeded());
		assertSame(solutionChromosome, wordGene2.getChromosome());

		assertEquals(solutionChromosome.getGenes().size(), beforeSize);

		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1.getWord());

		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2.getWord());

		validateSequencesAndGenes(solutionChromosome);

		validateHasMatchValues(wordGene2, false);
	}

	@Test
	public void testReplaceInvalidGene() {
		Word word = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGeneToReplace = new WordGene(word, solutionChromosome);
		solutionChromosome.replaceGene(0, wordGeneToReplace);
	}

	@Test
	public void testReplaceGeneOutOfBounds() {
		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGeneOriginal = new WordGene(word1, solutionChromosome);
		solutionChromosome.addGene(wordGeneOriginal);

		Word word2 = new Word("smile", PartOfSpeechType.NOUN);
		WordGene wordGeneToReplaceWith = new WordGene(word2, solutionChromosome);
		solutionChromosome.replaceGene(1, wordGeneToReplaceWith);

		assertSame(wordGeneOriginal, solutionChromosome.getGenes().get(0));
		assertEquals(1, solutionChromosome.getGenes().size());
	}

	@Test
	public void testAddPlaintext() {
		WordGene wordGene = new WordGene();

		PlaintextSequence plaintextSequence1 = new PlaintextSequence("g", wordGene);

		assertNull(plaintextSequence1.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.addPlaintext(plaintextSequence1);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(1, solutionChromosome.getPlaintextCharacters().size());

		PlaintextSequence plaintextSequence2 = new PlaintextSequence("b", wordGene);

		assertNull(plaintextSequence2.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.addPlaintext(plaintextSequence2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(2, solutionChromosome.getPlaintextCharacters().size());

		assertSame(plaintextSequence1, solutionChromosome.getPlaintextCharacters().get(0));
		assertSame(plaintextSequence2, solutionChromosome.getPlaintextCharacters().get(1));

		assertFalse(plaintextSequence1.getHasMatch());
		assertFalse(plaintextSequence2.getHasMatch());

		assertEquals(0, plaintextSequence1.getSequenceId().intValue());
		assertEquals(1, plaintextSequence2.getSequenceId().intValue());
	}

	@Test
	public void testAddInvalidPlaintext() {
		solutionChromosome.addPlaintext(null);
		assertTrue(solutionChromosome.getPlaintextCharacters().isEmpty());
		verify(logMock, times(1)).warn(anyString());
		verifyNoMoreInteractions(logMock);
	}

	@Test
	public void testInsertPlaintext() {
		WordGene wordGene = new WordGene();

		PlaintextSequence plaintextSequence3 = new PlaintextSequence("b", wordGene);

		assertNull(plaintextSequence3.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.insertPlaintext(0, plaintextSequence3);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(1, solutionChromosome.getPlaintextCharacters().size());

		PlaintextSequence plaintextSequence1 = new PlaintextSequence("g", wordGene);

		assertNull(plaintextSequence1.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.insertPlaintext(0, plaintextSequence1);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(2, solutionChromosome.getPlaintextCharacters().size());

		PlaintextSequence plaintextSequence2 = new PlaintextSequence("e", wordGene);

		assertNull(plaintextSequence2.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.insertPlaintext(1, plaintextSequence2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(3, solutionChromosome.getPlaintextCharacters().size());

		assertSame(plaintextSequence1, solutionChromosome.getPlaintextCharacters().get(0));
		assertSame(plaintextSequence2, solutionChromosome.getPlaintextCharacters().get(1));
		assertSame(plaintextSequence3, solutionChromosome.getPlaintextCharacters().get(2));

		assertFalse(plaintextSequence1.getHasMatch());
		assertFalse(plaintextSequence2.getHasMatch());
		assertFalse(plaintextSequence3.getHasMatch());

		assertEquals(0, plaintextSequence1.getSequenceId().intValue());
		assertEquals(1, plaintextSequence2.getSequenceId().intValue());
		assertEquals(2, plaintextSequence3.getSequenceId().intValue());
	}

	@Test
	public void testInsertInvalidPlaintext() {
		solutionChromosome.insertPlaintext(0, null);
		assertTrue(solutionChromosome.getPlaintextCharacters().isEmpty());
		verify(logMock, times(1)).warn(anyString());
		verifyNoMoreInteractions(logMock);
	}

	@Test
	public void testRemovePlaintext() {
		WordGene wordGene = new WordGene();

		PlaintextSequence plaintextSequence1 = new PlaintextSequence("g", wordGene);

		assertNull(plaintextSequence1.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.addPlaintext(plaintextSequence1);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(1, solutionChromosome.getPlaintextCharacters().size());

		PlaintextSequence plaintextSequence2 = new PlaintextSequence("e", wordGene);

		assertNull(plaintextSequence2.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.addPlaintext(plaintextSequence2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(2, solutionChromosome.getPlaintextCharacters().size());

		PlaintextSequence plaintextSequence3 = new PlaintextSequence("b", wordGene);

		assertNull(plaintextSequence3.getSequenceId());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.addPlaintext(plaintextSequence3);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(0, solutionChromosome.getGenes().size());
		assertEquals(3, solutionChromosome.getPlaintextCharacters().size());

		/*
		 * Make the solution clean before checking for dirtiness after removeGene
		 */
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());

		solutionChromosome.removePlaintext(plaintextSequence2);
		assertTrue(solutionChromosome.isEvaluationNeeded());

		assertEquals(2, solutionChromosome.getPlaintextCharacters().size());

		assertSame(plaintextSequence1, solutionChromosome.getPlaintextCharacters().get(0));
		assertSame(plaintextSequence3, solutionChromosome.getPlaintextCharacters().get(1));

		assertFalse(plaintextSequence1.getHasMatch());
		assertFalse(plaintextSequence3.getHasMatch());

		assertEquals(0, plaintextSequence1.getSequenceId().intValue());
		assertEquals(1, plaintextSequence3.getSequenceId().intValue());
	}

	@Test
	public void testRemoveInvalidPlaintext() {
		assertFalse(solutionChromosome.removePlaintext(null));
		verify(logMock, times(1)).warn(anyString());
		verifyNoMoreInteractions(logMock);
	}

	@Test
	public void testCloneSolutionChromosome() {
		solutionChromosome.setSolutionSetId(123);
		solutionChromosome.setFitness(50.0);
		solutionChromosome.setCipherId(new BigInteger("5678"));
		solutionChromosome.setEvaluationNeeded(false);

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, solutionChromosome);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word("elmer", PartOfSpeechType.NOUN);
		WordGene wordGene2 = new WordGene(word2, solutionChromosome);
		solutionChromosome.addGene(wordGene2);

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		WordGene wordGene3 = new WordGene(word3, solutionChromosome);
		solutionChromosome.addGene(wordGene3);

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();

		/*
		 * Make sure the Ciphers and Solution Sets reference the same memory address since those Objects are not cloned.
		 */
		assertSame(solutionChromosome.getCipherId(), clonedSolutionChromosome.getCipherId());
		assertSame(solutionChromosome.getSolutionSetId(), clonedSolutionChromosome.getSolutionSetId());

		assertEquals(solutionChromosome.getFitness(), clonedSolutionChromosome.getFitness());
		assertNotSame(solutionChromosome.getFitness(), clonedSolutionChromosome.getFitness());

		assertNull(clonedSolutionChromosome.getDatabaseCreatedDate());
		assertEquals(0, clonedSolutionChromosome.getNumberOfChildren());
		assertEquals(0, clonedSolutionChromosome.getAge());
		assertEquals(solutionChromosome.isEvaluationNeeded(), clonedSolutionChromosome.isEvaluationNeeded());

		/*
		 * Make sure the Chromosomes reference different memory addresses.
		 */
		assertNotSame(solutionChromosome, clonedSolutionChromosome);

		/*
		 * Make sure the content of the Chromosomes are equal.
		 */
		assertEquals(solutionChromosome, clonedSolutionChromosome);

		/*
		 * Make sure the WordGenes all reference different memory addresses.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertNotSame(solutionChromosome.getGenes().get(i), clonedSolutionChromosome.getGenes().get(i));
		}

		/*
		 * Make sure the content of the WordGenes are equal.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertEquals(solutionChromosome.getGenes().get(i), clonedSolutionChromosome.getGenes().get(i));
		}

		/*
		 * Make sure the the WordGenes all reference the cloned Chromosome.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertSame(clonedSolutionChromosome, clonedSolutionChromosome.getGenes().get(i).getChromosome());
		}

		/*
		 * Make sure the Sequences reference different memory addresses.
		 */
		for (int i = 0; i < solutionChromosome.actualSize(); i++) {
			assertNotSame(solutionChromosome.getPlaintextCharacters().get(i), clonedSolutionChromosome
					.getPlaintextCharacters().get(i));
		}

		/*
		 * Make sure the content of the Sequences are equal.
		 */
		for (int i = 0; i < solutionChromosome.actualSize(); i++) {
			assertEquals(solutionChromosome.getPlaintextCharacters().get(i), clonedSolutionChromosome
					.getPlaintextCharacters().get(i));
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome.getPlaintextCharacters().size());

		validateSequencesAndGenes(clonedSolutionChromosome);
	}

	@Test
	public void testRemoveGeneOnClonedSolution() {
		assertTrue(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setFitness(0.0);
		assertFalse(solutionChromosome.isEvaluationNeeded());
		solutionChromosome.setCipherId(cipher.getId());

		Word word1 = new Word("george", PartOfSpeechType.NOUN);
		WordGene wordGene1 = new WordGene(word1, solutionChromosome);
		solutionChromosome.addGene(wordGene1);

		Word word3 = new Word("belden", PartOfSpeechType.NOUN);
		WordGene wordGene3 = new WordGene(word3, solutionChromosome);
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
		 * Make the solution clean before checking for dirtiness after removeGene
		 */
		clonedSolutionChromosome.setFitness(0.0);
		assertFalse(clonedSolutionChromosome.isEvaluationNeeded());

		clonedSolutionChromosome.removeGene(1);
		assertTrue(clonedSolutionChromosome.isEvaluationNeeded());
		assertFalse(solutionChromosome.isEvaluationNeeded());

		assertEquals(((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString(), word1.getWord());

		validateSequencesAndGenes(clonedSolutionChromosome);
	}

	/**
	 * Reusable method for validating Lists of Sequences by looping through them at the Chromosome level and comparing
	 * to what exists at the Gene level.
	 * 
	 * @param solutionChromosome
	 *            the SolutionChromosome to validate
	 */
	private static void validateSequencesAndGenes(SolutionChromosome solutionChromosome) {
		Integer count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < ((VariableLengthGene) gene).size(); j++) {
				assertSame(solutionChromosome.getPlaintextCharacters().get(count), ((VariableLengthGene) gene)
						.getSequences().get(j));

				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getSequenceId(), count);

				assertEquals(((VariableLengthGene) gene).getSequences().get(j).getSequenceId(), count);
				assertEquals(((VariableLengthGene) gene).getSequences().get(j).getValue(), solutionChromosome
						.getPlaintextCharacters().get(count).getValue());

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome.getPlaintextCharacters().size());
	}

	/**
	 * Reusable method for validating that an entire Gene has the expected hasMatch value set.
	 * 
	 * @param wordGeneToValidate
	 *            the WordGene to validate
	 * @param expectedValue
	 *            the expected value
	 */
	private static void validateHasMatchValues(WordGene wordGeneToValidate, boolean expectedValue) {
		for (Sequence sequence : wordGeneToValidate.getSequences()) {
			assertEquals(expectedValue, ((PlaintextSequence) sequence).getHasMatch());
		}
	}

	@Test
	public void testEquals() {
		String baseId = "12345";
		Integer baseSolutionSetId = new Integer(678);
		BigInteger baseCipherId = new BigInteger("999");
		Date baseCreatedDate = new Date();
		int baseAge = 30;
		int baseNumberOfChildren = 1;
		Word word1 = new Word("learn", PartOfSpeechType.NOUN);
		WordGene gene1 = new WordGene(word1, null);
		Word word2 = new Word("listen", PartOfSpeechType.NOUN);
		WordGene gene2 = new WordGene(word2, null);
		Word word3 = new Word("love", PartOfSpeechType.NOUN);
		WordGene gene3 = new WordGene(word3, null);

		SolutionChromosome base = new SolutionChromosome();
		base.setId(baseId);
		base.setSolutionSetId(baseSolutionSetId);
		base.setCipherId(baseCipherId);
		base.setDatabaseCreatedDate(baseCreatedDate);
		base.setAge(baseAge);
		base.setNumberOfChildren(baseNumberOfChildren);
		base.addGene(gene1.clone());
		base.addGene(gene2.clone());
		base.addGene(gene3.clone());

		SolutionChromosome solutionChromosomeEqualToBase = new SolutionChromosome();
		solutionChromosomeEqualToBase.setId(baseId);
		solutionChromosomeEqualToBase.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeEqualToBase.setCipherId(baseCipherId);
		solutionChromosomeEqualToBase.setDatabaseCreatedDate(baseCreatedDate);
		solutionChromosomeEqualToBase.setAge(baseAge);
		solutionChromosomeEqualToBase.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeEqualToBase.addGene(gene1.clone());
		solutionChromosomeEqualToBase.addGene(gene2.clone());
		solutionChromosomeEqualToBase.addGene(gene3.clone());

		assertEquals(base, solutionChromosomeEqualToBase);

		SolutionChromosome solutionChromosomeWithDifferentId = new SolutionChromosome();
		solutionChromosomeWithDifferentId.setId("54321");
		solutionChromosomeWithDifferentId.setSolutionSetId(baseSolutionSetId);
		solutionChromosomeWithDifferentId.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentId.setDatabaseCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentId.setAge(baseAge);
		solutionChromosomeWithDifferentId.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentId.addGene(gene1.clone());
		solutionChromosomeWithDifferentId.addGene(gene2.clone());
		solutionChromosomeWithDifferentId.addGene(gene3.clone());

		assertTrue(base.equals(solutionChromosomeWithDifferentId));

		SolutionChromosome solutionChromosomeWithDifferentSolutionSetId = new SolutionChromosome();
		solutionChromosomeWithDifferentSolutionSetId.setId(baseId);
		solutionChromosomeWithDifferentSolutionSetId.setSolutionSetId(new Integer(876));
		solutionChromosomeWithDifferentSolutionSetId.setCipherId(baseCipherId);
		solutionChromosomeWithDifferentSolutionSetId.setDatabaseCreatedDate(baseCreatedDate);
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
		solutionChromosomeWithDifferentCipherId.setDatabaseCreatedDate(baseCreatedDate);
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
		solutionChromosomeWithDifferentCreatedDate.setDatabaseCreatedDate(cal.getTime());
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
		solutionChromosomeWithDifferentAge.setDatabaseCreatedDate(baseCreatedDate);
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
		solutionChromosomeWithDifferentNumberOfChildren.setDatabaseCreatedDate(baseCreatedDate);
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
		solutionChromosomeWithDifferentGenes.setDatabaseCreatedDate(baseCreatedDate);
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
		solutionChromosomeWithDifferentPlaintextCharacters.setDatabaseCreatedDate(baseCreatedDate);
		solutionChromosomeWithDifferentPlaintextCharacters.setAge(baseAge);
		solutionChromosomeWithDifferentPlaintextCharacters.setNumberOfChildren(baseNumberOfChildren);
		solutionChromosomeWithDifferentPlaintextCharacters.addGene(gene1.clone());
		solutionChromosomeWithDifferentPlaintextCharacters.addGene(gene2.clone());
		solutionChromosomeWithDifferentPlaintextCharacters.addGene(gene3.clone());
		solutionChromosomeWithDifferentPlaintextCharacters.addPlaintext(new PlaintextSequence("x", gene3));

		assertFalse(base.equals(solutionChromosomeWithDifferentPlaintextCharacters));

		SolutionChromosome solutionChromosomeWithNullPropertiesA = new SolutionChromosome();
		SolutionChromosome solutionChromosomeWithNullPropertiesB = new SolutionChromosome();
		assertEquals(solutionChromosomeWithNullPropertiesA, solutionChromosomeWithNullPropertiesB);
	}
}
