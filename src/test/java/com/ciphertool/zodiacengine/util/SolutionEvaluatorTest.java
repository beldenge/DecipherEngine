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
		context = null;
	}
}
