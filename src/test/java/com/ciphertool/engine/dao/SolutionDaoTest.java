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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;

public class SolutionDaoTest {
	private static SolutionDao		solutionDao;
	private static MongoOperations	mongoTemplateMock;

	@BeforeClass
	public static void setUp() {
		solutionDao = new SolutionDao();
		mongoTemplateMock = mock(MongoOperations.class);

		solutionDao.setMongoTemplate(mongoTemplateMock);
	}

	@Before
	public void resetMocks() {
		reset(mongoTemplateMock);
	}

	@Test
	public void testSetMongoTemplate() {
		SolutionDao solutionDao = new SolutionDao();
		solutionDao.setMongoTemplate(mongoTemplateMock);

		Field mongoOperationsField = ReflectionUtils.findField(SolutionDao.class, "mongoOperations");
		ReflectionUtils.makeAccessible(mongoOperationsField);
		MongoOperations mongoOperationsFromObject = (MongoOperations) ReflectionUtils.getField(mongoOperationsField, solutionDao);

		assertSame(mongoTemplateMock, mongoOperationsFromObject);
	}

	@Test
	public void testFindBySolutionId() {
		CipherKeyChromosome solutionToReturn = new CipherKeyChromosome();
		when(mongoTemplateMock.findOne(any(Query.class), eq(Chromosome.class))).thenReturn(solutionToReturn);

		int arbitraryInteger = 1;
		Chromosome solutionReturned = solutionDao.findBySolutionId(arbitraryInteger);

		assertSame(solutionToReturn, solutionReturned);
	}

	@Test
	public void testFindBySolutionIdNull() {
		Chromosome solutionReturned = solutionDao.findBySolutionId(null);

		assertNull(solutionReturned);
	}

	@Test
	public void testFindByCipherName() {
		List<Chromosome> solutionsToReturn = new ArrayList<Chromosome>();
		CipherKeyChromosome solution1 = new CipherKeyChromosome();
		solutionsToReturn.add(solution1);
		CipherKeyChromosome solution2 = new CipherKeyChromosome();
		solutionsToReturn.add(solution2);

		Cipher dummyCipherToReturn = new Cipher();
		when(mongoTemplateMock.findOne(any(Query.class), eq(Cipher.class))).thenReturn(dummyCipherToReturn);

		when(mongoTemplateMock.find(any(Query.class), eq(Chromosome.class))).thenReturn(solutionsToReturn);

		List<Chromosome> solutionsReturned = solutionDao.findByCipherName("arbitraryCipherName");

		verify(mongoTemplateMock, times(1)).findOne(any(Query.class), eq(Cipher.class));
		assertEquals(solutionsToReturn, solutionsReturned);
	}

	@Test
	public void testFindByCipherNameNull() {
		List<Chromosome> solutionsReturned = solutionDao.findByCipherName(null);

		verify(mongoTemplateMock, never()).findOne(any(Query.class), eq(Cipher.class));
		verify(mongoTemplateMock, never()).find(any(Query.class), eq(CipherKeyChromosome.class));
		assertNull(solutionsReturned);
	}

	@Test
	public void testFindByCipherNameEmpty() {
		List<Chromosome> solutionsReturned = solutionDao.findByCipherName("");

		verify(mongoTemplateMock, never()).findOne(any(Query.class), eq(Cipher.class));
		verify(mongoTemplateMock, never()).find(any(Query.class), eq(CipherKeyChromosome.class));
		assertNull(solutionsReturned);
	}

	@Test
	public void testInsert() {
		CipherKeyChromosome solutionToInsert = new CipherKeyChromosome();

		boolean result = solutionDao.insert(solutionToInsert);

		verify(mongoTemplateMock, times(1)).insert(same(solutionToInsert));
		assertTrue(result);
	}

	@Test
	public void testInsertNull() {
		boolean result = solutionDao.insert(null);

		verify(mongoTemplateMock, never()).insert(any(CipherKeyChromosome.class));
		assertFalse(result);
	}
}
