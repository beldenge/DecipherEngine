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

package com.ciphertool.engine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;
import com.ciphertool.engine.dao.ForcedWordGeneDao;
import com.ciphertool.engine.entities.SolutionChromosome;
import com.ciphertool.engine.entities.WordGene;

public class ForcedWordGeneDaoTest {
	private static ForcedWordGeneDao forcedWordGeneDao;
	private static WordListDao wordListDaoMock;

	@BeforeClass
	public static void setUp() {
		forcedWordGeneDao = new ForcedWordGeneDao();

		wordListDaoMock = mock(WordListDao.class);
		forcedWordGeneDao.setWordListDao(wordListDaoMock);
	}

	@Before
	public void resetMocks() {
		reset(wordListDaoMock);
	}

	@Test
	public void testSetWordListDao() {
		ForcedWordGeneDao forcedWordGeneDao = new ForcedWordGeneDao();
		forcedWordGeneDao.setWordListDao(wordListDaoMock);

		Field wordListDaoField = ReflectionUtils.findField(ForcedWordGeneDao.class, "wordListDao");
		ReflectionUtils.makeAccessible(wordListDaoField);
		WordListDao wordListDaoFromObject = (WordListDao) ReflectionUtils.getField(wordListDaoField, forcedWordGeneDao);

		assertSame(wordListDaoMock, wordListDaoFromObject);
	}

	@Test
	public void testFindRandomGene() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;

		Word wordToReturn = new Word(new WordId("testing", PartOfSpeechType.VERB_PARTICIPLE), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene expectedGene = new WordGene(wordToReturn, chromosomeToCheck);

		Gene geneReturned = forcedWordGeneDao.findRandomGene(chromosomeToCheck);

		assertSame(chromosomeToCheck, geneReturned.getChromosome());
		assertEquals(expectedGene, geneReturned);
	}

	@Test
	public void testFindRandomGeneNullChromosome() {
		Integer arbitraryInteger = 5;

		Word wordToReturn = new Word(new WordId("testing", PartOfSpeechType.VERB_PARTICIPLE), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene geneReturned = forcedWordGeneDao.findRandomGene(null);

		assertNull(geneReturned);
		verify(wordListDaoMock, never()).findRandomWord();
	}

	@Test
	public void testFindRandomGeneOfLength() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;
		Integer wordLength = 7;

		Word wordToReturn = new Word(new WordId("testing", PartOfSpeechType.VERB_PARTICIPLE), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene expectedGene = new WordGene(wordToReturn, chromosomeToCheck);

		Gene geneReturned = forcedWordGeneDao.findRandomGeneOfLength(chromosomeToCheck, wordLength);

		assertSame(chromosomeToCheck, geneReturned.getChromosome());
		assertEquals(expectedGene, geneReturned);
	}

	@Test
	public void testFindRandomGeneOfLengthNullChromosome() {
		Integer arbitraryInteger = 5;
		Integer wordLength = 7;

		Word wordToReturn = new Word(new WordId("testing", PartOfSpeechType.VERB_PARTICIPLE), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene geneReturned = forcedWordGeneDao.findRandomGeneOfLength(null, wordLength);

		assertNull(geneReturned);
		verify(wordListDaoMock, never()).findRandomWord();
	}

	@Test
	public void testFindRandomGeneOfLengthMinimum() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;
		Integer wordLength = 0;

		Word wordToReturn = new Word(new WordId("testing", PartOfSpeechType.VERB_PARTICIPLE), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene geneReturned = forcedWordGeneDao.findRandomGeneOfLength(chromosomeToCheck, wordLength);

		assertNull(geneReturned);
		verify(wordListDaoMock, never()).findRandomWord();
	}

	@Test
	public void testFindRandomGeneOfLengthMaxAttempts() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;
		Integer wordLength = 5;

		Word wordToReturn = new Word(new WordId("testing", PartOfSpeechType.VERB_PARTICIPLE), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene geneReturned = forcedWordGeneDao.findRandomGeneOfLength(chromosomeToCheck, wordLength);

		assertNull(geneReturned);
		verify(wordListDaoMock, times(10000)).findRandomWord();
	}
}
