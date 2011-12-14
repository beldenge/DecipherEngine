package com.ciphertool.zodiacengine.util;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionEvaluatorTest {
	
	private static Logger log = Logger.getLogger(SolutionEvaluatorTest.class);
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testValidateSolution() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) factory.getBean("solutionGenerator");
		long start = System.currentTimeMillis();
		Solution solution = solutionGenerator.generateSolution();
		long end = System.currentTimeMillis();
		log.info("Took " + (end-start) + "ms to generate solution.");
		SolutionEvaluator solutionEvaluator = (SolutionEvaluator) factory.getBean("solutionEvaluator");
		start = System.currentTimeMillis();
		solutionEvaluator.determineConfidenceLevel(solution);
		end = System.currentTimeMillis();
		log.info("Took " + (end-start) + "ms to determine confidence level.");
	}
}
