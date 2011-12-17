package com.ciphertool.zodiacengine.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionGeneratorTest {
	
	private static Logger log = Logger.getLogger(SolutionGeneratorTest.class);
	private static ApplicationContext context;
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testGenerateSolution() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) factory.getBean("solutionGenerator");
		
		long start = System.currentTimeMillis();
		
		@SuppressWarnings("unused")
		Solution solution = solutionGenerator.generateSolution();
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate a solution from scratch.");
		
		start = System.currentTimeMillis();
		
		solution = solutionGenerator.generateSolution();
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate a second solution from scratch.");
	}
	
	@Test
	public void testGeneratorMethods() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) factory.getBean("solutionGenerator");
		
		long start = System.currentTimeMillis();
		
		@SuppressWarnings("unused")
		List<Sentence> sentences = solutionGenerator.getSentences();
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate sentences.");
	}
	
	@Test
	public void testGeneratorPerformance() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) factory.getBean("solutionGenerator");
		ZodiacSolutionEvaluator solutionEvaluator = (ZodiacSolutionEvaluator) factory.getBean("solutionEvaluator");
		int i = 0;
		int highest = 0;
		int total = 0;
		int confidence = 0;
		Solution solution;
		
		long start = System.currentTimeMillis();
		
		for (i = 0; i < 1000; i++) {
			// generate a solution
			solution = solutionGenerator.generateSolution();
			
			// find the confidence level for the solution
			solutionEvaluator.determineConfidenceLevel(solution);
			
			// we need the confidence level several times, so just call the getter once and store in a temp variable
			confidence = solution.getConfidence();
			
			// check if there's a new higher confidence solution
			highest = (confidence > highest) ? confidence : highest;
			
			// add to the running total for computing the average later
			total += confidence;
		}
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate and validate " + i + " solutions.");
		log.info("Highest confidence level achieved: " + highest);
		log.info("Average confidence level: " + (total/i));
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
