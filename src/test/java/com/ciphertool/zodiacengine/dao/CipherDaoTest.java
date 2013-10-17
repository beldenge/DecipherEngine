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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.ciphertool.zodiacengine.entities.Cipher;

public class CipherDaoTest {
	private static CipherDao cipherDao;
	private static MongoOperations mongoTemplateMock;

	@BeforeClass
	public static void setUp() {
		cipherDao = new CipherDao();
		mongoTemplateMock = mock(MongoOperations.class);

		cipherDao.setMongoTemplate(mongoTemplateMock);
	}

	@Before
	public void resetMocks() {
		reset(mongoTemplateMock);
	}

	@Test
	public void testFindByCipherName() {
		Cipher cipherToReturn = new Cipher();
		when(mongoTemplateMock.findOne(any(Query.class), eq(Cipher.class))).thenReturn(
				cipherToReturn);

		Cipher cipherReturned = cipherDao.findByCipherName("arbitraryCipherName");

		assertSame(cipherToReturn, cipherReturned);
	}

	@Test
	public void testFindByCipherNameNull() {
		Cipher cipherReturned = cipherDao.findByCipherName(null);

		assertNull(cipherReturned);
	}

	@Test
	public void testFindAll() {
		List<Cipher> ciphersToReturn = new ArrayList<Cipher>();
		Cipher cipher1 = new Cipher("fiveByFive", 5, 5);
		ciphersToReturn.add(cipher1);
		Cipher cipher2 = new Cipher("tenByTen", 10, 10);
		ciphersToReturn.add(cipher2);

		when(mongoTemplateMock.findAll(eq(Cipher.class))).thenReturn(ciphersToReturn);

		List<Cipher> ciphersReturned = cipherDao.findAll();

		assertEquals(ciphersToReturn, ciphersReturned);
	}
}
