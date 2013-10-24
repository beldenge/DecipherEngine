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

package com.ciphertool.zodiacengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.dao.WordMapDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class WordGeneListDaoTest {
	private static WordGeneListDao wordGeneListDao;
	private static WordListDao wordListDaoMock;
	private static WordMapDao wordMapDaoMock;

	@BeforeClass
	public static void setUp() {
		wordGeneListDao = new WordGeneListDao();

		wordListDaoMock = mock(WordListDao.class);
		wordGeneListDao.setWordListDao(wordListDaoMock);

		wordMapDaoMock = mock(WordMapDao.class);
		wordGeneListDao.setWordMapDao(wordMapDaoMock);
	}

	@Before
	public void resetMocks() {
		reset(wordListDaoMock);
		reset(wordMapDaoMock);
	}

	@Test
	public void testFindRandomGene() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;

		Word wordToReturn = new Word(new WordId("testing", 'V'), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene expectedGene = new WordGene(wordToReturn, chromosomeToCheck, arbitraryInteger);

		Gene geneReturned = wordGeneListDao.findRandomGene(chromosomeToCheck, arbitraryInteger);

		assertSame(chromosomeToCheck, geneReturned.getChromosome());
		assertEquals(arbitraryInteger, geneReturned.getSequences().get(0).getSequenceId());
		assertEquals(expectedGene, geneReturned);
	}

	@Test
	public void testFindRandomGeneNullChromosome() {
		Integer arbitraryInteger = 5;

		Word wordToReturn = new Word(new WordId("testing", 'V'), arbitraryInteger);
		when(wordListDaoMock.findRandomWord()).thenReturn(wordToReturn);

		Gene geneReturned = wordGeneListDao.findRandomGene(null, arbitraryInteger);

		assertNull(geneReturned);
		verify(wordListDaoMock, never()).findRandomWord();
	}

	@Test
	public void testFindRandomGeneOfLength() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;
		Integer wordLength = 7;

		Word wordToReturn = new Word(new WordId("testing", 'V'), arbitraryInteger);
		when(wordMapDaoMock.findRandomWordByLength(eq(wordLength))).thenReturn(wordToReturn);

		Gene expectedGene = new WordGene(wordToReturn, chromosomeToCheck, arbitraryInteger);

		Gene geneReturned = wordGeneListDao.findRandomGeneOfLength(chromosomeToCheck,
				arbitraryInteger, wordLength);

		assertSame(chromosomeToCheck, geneReturned.getChromosome());
		assertEquals(arbitraryInteger, geneReturned.getSequences().get(0).getSequenceId());
		assertEquals(expectedGene, geneReturned);
	}

	@Test
	public void testFindRandomGeneOfLengthNullChromosome() {
		Integer arbitraryInteger = 5;
		Integer wordLength = 7;

		Word wordToReturn = new Word(new WordId("testing", 'V'), arbitraryInteger);
		when(wordMapDaoMock.findRandomWordByLength(eq(wordLength))).thenReturn(wordToReturn);

		Gene geneReturned = wordGeneListDao.findRandomGeneOfLength(null, arbitraryInteger,
				wordLength);

		assertNull(geneReturned);
		verify(wordListDaoMock, never()).findRandomWord();
	}

	@Test
	public void testFindRandomGeneOfLengthMinimum() {
		SolutionChromosome chromosomeToCheck = new SolutionChromosome();
		Integer arbitraryInteger = 5;
		Integer wordLength = 0;

		Word wordToReturn = new Word(new WordId("testing", 'V'), arbitraryInteger);
		when(wordMapDaoMock.findRandomWordByLength(eq(wordLength))).thenReturn(wordToReturn);

		Gene geneReturned = wordGeneListDao.findRandomGeneOfLength(chromosomeToCheck,
				arbitraryInteger, wordLength);

		assertNull(geneReturned);
		verify(wordListDaoMock, never()).findRandomWord();
	}
}