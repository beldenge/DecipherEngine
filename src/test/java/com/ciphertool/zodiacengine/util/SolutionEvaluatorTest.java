package com.ciphertool.zodiacengine.util;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionEvaluatorTest {
	
	private static Logger log = Logger.getLogger(SolutionEvaluatorTest.class);
	private static ApplicationContext context;
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testValidateSolution() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) factory.getBean("solutionGenerator");
		long start = System.currentTimeMillis();
		Solution solution = solutionGenerator.generateSolution();
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate solution.");
		SolutionEvaluator solutionEvaluator = (SolutionEvaluator) factory.getBean("solutionEvaluator");
		start = System.currentTimeMillis();
		solutionEvaluator.determineConfidenceLevel(solution);
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to determine confidence level.");
	}
	
	/**
	 * Without setting these to null, the humongous wordMap will not be garbage collected and subsequent unit tests may encounter an out of memory exception
	 */
	@AfterClass
	public static void cleanUp() {
		context = null;
		factory = null;
	}
}
