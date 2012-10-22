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

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionEvaluatorTest {

	private static Logger log = Logger.getLogger(SolutionEvaluatorTest.class);
	private static ApplicationContext context;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		log.info("Spring context created successfully!");
	}

	@Test
	public void testValidateSolution() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) context
				.getBean("solutionGenerator");
		long start = System.currentTimeMillis();
		Solution solution = solutionGenerator.generateSolution();
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate solution.");
		SolutionEvaluator solutionEvaluator = (SolutionEvaluator) context
				.getBean("solutionEvaluator");
		start = System.currentTimeMillis();
		solutionEvaluator.determineConfidenceLevel(solution);
		log.info("Took " + (System.currentTimeMillis() - start)
				+ "ms to determine confidence level.");
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
