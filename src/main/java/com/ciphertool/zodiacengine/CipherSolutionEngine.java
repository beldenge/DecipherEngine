package com.ciphertool.zodiacengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dto.CipherDto;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.util.SolutionEvaluator;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class CipherSolutionEngine {
	private static Logger log = Logger.getLogger(CipherSolutionEngine.class);
	private static BeanFactory factory;
	private static long numIterations;
	private static int maxThreads;
	private static int monitorSleepMillis;
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String [] args) throws InterruptedException {
		// Spin up the Spring application context
		setUp();
		
		SolutionGenerator solutionGenerator = (SolutionGenerator) factory.getBean("solutionGenerator");
		SolutionEvaluator solutionEvaluator = (SolutionEvaluator) factory.getBean("solutionEvaluator");

		CipherDto cipherDto = null;
		Runnable cipherTask = null;
		Thread cipherWorker = null;
		long threadIterations = 0;
		
		long start = System.currentTimeMillis();
		
		List<Thread> threads = new ArrayList<Thread>();
		List<CipherDto> cipherDtos = new ArrayList<CipherDto>();
		
		log.info("Beginning solution generation.  Generating " + numIterations + " solutions using " + maxThreads +" threads.");
		
		for (int i = 1; i <= maxThreads; i++) {
			threadIterations = (numIterations / maxThreads);
			if (i == 1)
			{
				/*
				 * If the number of iterations doesn't divide evenly among the threads, add the remainder to the first thread
				 */
				threadIterations += (numIterations % maxThreads);
			}
			
			cipherDto = new CipherDto(String.valueOf(i));
			cipherDtos.add(cipherDto);
			
			cipherTask = new CipherSolutionRunnable(threadIterations, solutionGenerator, solutionEvaluator, cipherDto);
			
			cipherWorker = new Thread(cipherTask, String.valueOf(i));
			
			cipherWorker.start();
			threads.add(cipherWorker);
		}
		
		/*
		 * Keep checking threads until no more are left running
		 */
		int running = 0;
		do {
			running = 0;
			for (Thread thread : threads) {
				if (thread.isAlive()) {
					running ++;
				}
			}
			
			/*
			 * There's no need to loop through this as fast as possible.  
			 * Sleep for a short period so that there isn't so much overhead from monitoring the threads' state.
			 */
			Thread.sleep(monitorSleepMillis);
		}
		while (running > 0);
		
		long totalSolutions = 0;
		long confidenceSum = 0;
		long uniqueMatchSum = 0;
		
		Solution solutionMostMatches = new Solution(1, 0, 0);
		Solution solutionMostUnique = new Solution(1, 0, 0);

		/*
		 * Sum up all data from all CipherDtos passed to the threads
		 */
		for (CipherDto nextCipherDto : cipherDtos) {
			log.debug("Best solution from thread " + nextCipherDto.getThreadName() + ": " + nextCipherDto.getSolutionMostMatches());
			log.debug("Most unique solution from thread " + nextCipherDto.getThreadName() + ": " + nextCipherDto.getSolutionMostUnique());
			
			totalSolutions += nextCipherDto.getNumSolutions();
			confidenceSum += nextCipherDto.getConfidenceSum();
			uniqueMatchSum += nextCipherDto.getUniqueMatchSum();
			
			/*
			 * Find the Solution with the highest confidence level (most matches in plaintext)
			 */
			if (nextCipherDto.getSolutionMostMatches().getConfidence() > solutionMostMatches.getConfidence()) {
				solutionMostMatches = nextCipherDto.getSolutionMostMatches();
			}
			
			/*
			 * Find the Solution with the highest number of unique matches in plaintext
			 */
			if (nextCipherDto.getSolutionMostUnique().getUniqueMatches() > solutionMostUnique.getUniqueMatches()) {
				solutionMostUnique = nextCipherDto.getSolutionMostUnique();
			}
		}
		
		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate and validate " + totalSolutions + " solutions.");
		log.info("Highest confidence level achieved: " + solutionMostMatches.getConfidence());
		log.info("Average confidence level: " + (confidenceSum / totalSolutions));
		log.info("Best solution found: " + solutionMostMatches);
		log.info("Most unique matches achieved: " + solutionMostUnique.getUniqueMatches());
		log.info("Average unique matches: " + (uniqueMatchSum / totalSolutions));
		log.info("Solution with most unique matches found: " + solutionMostUnique);
	}
	
	/**
	 * Spins up the Spring application context
	 */
	private static void setUp() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		
		factory = context;
		
		log.info("Spring context created successfully!");
	}

	/**
	 * @param numIterations
	 */
	@Required
	public void setNumIterations(long numIterations) {
		CipherSolutionEngine.numIterations = numIterations;
	}

	/**
	 * @param setMaxThreads
	 */
	@Required
	public void setMaxThreads(int maxThreads) {
		CipherSolutionEngine.maxThreads = maxThreads;
	}

	/**
	 * @param monitorSleepMillis the monitorSleepMillis to set
	 */
	@Required
	public void setMonitorSleepMillis(int monitorSleepMillis) {
		CipherSolutionEngine.monitorSleepMillis = monitorSleepMillis;
	}
}
