package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionGeneratorTest {
	
	private static Logger log = Logger.getLogger(SolutionGeneratorTest.class);
	private static ApplicationContext context;
	
	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testGenerateSolution() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) context.getBean("solutionGenerator");
		
		@SuppressWarnings("unused")
		Solution solution = solutionGenerator.generateSolution();
		
		solution = solutionGenerator.generateSolution();
	}
	
	@Test
	public void testGeneratorMethods() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) context.getBean("solutionGenerator");
		
		@SuppressWarnings("unused")
		List<Sentence> sentences = solutionGenerator.getSentences();
	}
	
	@Test
	public void testGeneratorPerformance() {
		ZodiacSolutionGenerator solutionGenerator = (ZodiacSolutionGenerator) context.getBean("solutionGenerator");
		ZodiacSolutionEvaluator solutionEvaluator = (ZodiacSolutionEvaluator) context.getBean("solutionEvaluator");
		CipherDao cipherDao = (CipherDao) context.getBean("cipherDao");
		int i = 0;
		
		List<Solution> solutions = new ArrayList<Solution>();

		long start;
		
		for (int tests = 1; tests <= 10; tests ++) {
			start = System.currentTimeMillis();
			
			for (i = 0; i < 1000; i++) {
				// generate a solution
				solutions.add(solutionGenerator.generateSolution());
			}
			
			log.info("Test " + tests + " took " + (System.currentTimeMillis() - start) + "ms to generate " + i + " solutions.");
	
			start = System.currentTimeMillis();
			
			for (Solution solution : solutions) {
				// find the confidence level for the solution
				solutionEvaluator.determineConfidenceLevel(solution);
			}
			
			log.info("Test " + tests + " took " + (System.currentTimeMillis() - start) + "ms to evaluate " + i + " solutions.");
			
			solutions.clear();
			
			start = System.currentTimeMillis();
			
			Solution solution;
			for (i = 0; i < 1000; i ++) {
				// find the confidence level for the solution
				solution = solutionGenerator.generateSolution();
				solutionEvaluator.determineConfidenceLevel(solution);
			}
			
			log.info("Test " + tests + " took " + (System.currentTimeMillis() - start) + "ms to generate and evaluate " + i + " solutions.");
		}
		
		List<List<Sentence>> sentenceLists = new ArrayList<List<Sentence>>();
		
		start = System.currentTimeMillis();
		
		for (i = 0; i < 1000; i++) {
			sentenceLists.add(solutionGenerator.getSentences());
		}
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate sentences for " + i + " ciphers.");

		Cipher cipher = cipherDao.findByCipherName("zodiac340");
		
		start = System.currentTimeMillis();
		
		for (List<Sentence> sentenceList : sentenceLists) {
			Solution solution = new Solution(cipher.getId(), 0, 0);
			solution.setCipher(cipher);
			solutionGenerator.convertSentencesToPlaintext(solution, sentenceList);
		}
		
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to convert sentences to Plaintext for " + i + " ciphers.");
	}
	
	/**
	 * Without setting these to null, the humongous wordMap will not be garbage collected and subsequent unit tests may encounter an out of memory exception
	 */
	@AfterClass
	public static void cleanUp() {
		context = null;
	}
}
