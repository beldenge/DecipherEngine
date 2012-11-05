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

package com.ciphertool.zodiacengine.dao;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.entities.SolutionId;
import com.ciphertool.zodiacengine.entities.SolutionSet;

public class PlaintextDaoTest {

	private static Logger log = Logger.getLogger(PlaintextDaoTest.class);
	private static ApplicationContext context;

	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		log.info("Spring context created successfully!");
	}

	@Test
	public void testFindByPlaintextId() {
		PlaintextDao plaintextDao = (PlaintextDao) context.getBean("plaintextDao");
		Solution solution = new Solution(1, 0, 0, 0);
		// provided a solution with this id exists
		solution.setId(new SolutionId(26, new SolutionSet(1)));
		PlaintextId plaintextId = new PlaintextId(solution, 5);
		Plaintext plaintext = plaintextDao.findByPlaintextId(plaintextId);
		log.info("Plaintext: " + plaintext);
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		((ClassPathXmlApplicationContext) context).close();
		context = null;
	}
}
