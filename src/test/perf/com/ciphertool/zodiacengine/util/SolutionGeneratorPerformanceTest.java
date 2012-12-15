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

package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionGeneratorPerformanceTest {

	private static Logger log = Logger.getLogger(SolutionGeneratorPerformanceTest.class);
	private static ApplicationContext context;
	private static ZodiacSolutionGenerator solutionGenerator;
	private static ZodiacSolutionEvaluator solutionEvaluator;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		log.info("Spring context created successfully!");

		CipherDao cipherDao = (CipherDao) context.getBean("cipherDao");

		Cipher cipher = cipherDao.findByCipherName("zodiac340");

		solutionGenerator = (ZodiacSolutionGenerator) context.getBean("solutionGenerator");
		solutionEvaluator = (ZodiacSolutionEvaluator) context.getBean("solutionEvaluator");

		solutionGenerator.setCipher(cipher);
		solutionEvaluator.setCipher(cipher);
	}

	@Test
	public void testGeneratorPerformance() {
		int i = 0;

		List<Solution> solutions = new ArrayList<Solution>();

		long start;

		for (int tests = 1; tests <= 10; tests++) {
			start = System.currentTimeMillis();

			for (i = 0; i < 1000; i++) {
				// generate a solution
				solutions.add(solutionGenerator.generateSolution());
			}

			log.info("Test " + tests + " took " + (System.currentTimeMillis() - start)
					+ "ms to generate " + i + " solutions.");

			start = System.currentTimeMillis();

			for (Solution solution : solutions) {
				// find the confidence level for the solution
				solutionEvaluator.determineConfidenceLevel(solution);
			}

			log.info("Test " + tests + " took " + (System.currentTimeMillis() - start)
					+ "ms to evaluate " + i + " solutions.");

			solutions.clear();

			start = System.currentTimeMillis();

			Solution solution;
			for (i = 0; i < 1000; i++) {
				// find the confidence level for the solution
				solution = solutionGenerator.generateSolution();
				solutionEvaluator.determineConfidenceLevel(solution);
			}

			log.info("Test " + tests + " took " + (System.currentTimeMillis() - start)
					+ "ms to generate and evaluate " + i + " solutions.");
		}
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
