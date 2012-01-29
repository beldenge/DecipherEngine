package com.ciphertool.zodiacengine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dto.CipherDto;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.enumerations.ApplicationDurationType;
import com.ciphertool.zodiacengine.util.SolutionEvaluator;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class CipherSolutionExecutor {
	private static Logger log = Logger.getLogger(CipherSolutionExecutor.class);
	private static BeanFactory factory;
	private static ApplicationDurationType applicationDurationType;
	private static long applicationRunMillis;
	private static long numIterations;
	private static int maxThreads;
	
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
		
		long start = System.currentTimeMillis();
		
		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
		
		cipherDto = new CipherDto(String.valueOf(0));
		
		/*
		 * We want to generate and validate a specific number of solutions, no matter how long it takes.
		 */
		if (applicationDurationType == ApplicationDurationType.ITERATION) {
			if (numIterations <= 0) {
				throw new IllegalArgumentException("ApplicationDurationType set to ITERATION, but numIterations was not set or was set incorrectly.");
			}
			
			log.info("Beginning solution generation.  Generating " + numIterations + " solutions using " + maxThreads +" threads.");
			
			for (long i = 1; i <= numIterations; i ++) {
				Runnable cipherTask = new CipherSolutionSynchronizedRunnable(solutionGenerator, solutionEvaluator, cipherDto);
				
				executor.execute(cipherTask);
			}
			
			// Make executor accept no new threads and finish all existing threads in the queue
			executor.shutdown();
		}
		/*
		 * We want to generate and validate solutions for a set amount of time, no matter how many we can generate in that time period.
		 */
		else if (applicationDurationType == ApplicationDurationType.TEMPORAL) {
			if (applicationRunMillis <= 0) {
				throw new IllegalArgumentException("ApplicationDurationType set to TEMPORAL, but applicationRunMillis was not set or was set incorrectly.");
			}
			
			log.info("Beginning solution generation.  Generating solutions for " + applicationRunMillis + "ms using " + maxThreads +" threads.");
			
			long count = 0;
			
			while (true) {
				Runnable cipherTask = new CipherSolutionSynchronizedRunnable(solutionGenerator, solutionEvaluator, cipherDto);
				
				executor.execute(cipherTask);
				
				/*
				 * This is a fairly rudimentary way of managing the number of tasks sent to the executor.
				 * 
				 * If we don't manage it somehow, the app will get bogged down by the continuous while loop and performance will degrade significantly.
				 */
				if (++count >= 10000) {
					count = 0;
					
					executor.shutdown();
					
					/*
					 * We are mainly concerned about blocking until all tasks are finished, so the timeout is not a big concern.
					 */
					executor.awaitTermination(1, TimeUnit.MINUTES);
					
					executor = Executors.newFixedThreadPool(maxThreads);
					
					if((System.currentTimeMillis() - start) > applicationRunMillis) {
						break;
					}
				}
			}
			
			// Make executor stop immediately
			executor.shutdownNow();
		}
		
		// Wait until all threads are finished
		while (!executor.isTerminated()) {}
		
		Solution solutionMostMatches = cipherDto.getSolutionMostMatches();
		Solution solutionMostUnique = cipherDto.getSolutionMostUnique();

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate and validate " + cipherDto.getNumSolutions() + " solutions.");
		log.info("Highest confidence level achieved: " + solutionMostMatches.getConfidence());
		log.info("Average confidence level: " + (cipherDto.getConfidenceSum() / cipherDto.getNumSolutions()));
		log.info("Best solution found: " + solutionMostMatches);
		log.info("Most unique matches achieved: " + solutionMostUnique.getUniqueMatches());
		log.info("Average unique matches: " + (cipherDto.getUniqueMatchSum() / cipherDto.getNumSolutions()));
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
	 * @param setMaxThreads
	 */
	@Required
	public void setMaxThreads(int maxThreads) {
		CipherSolutionExecutor.maxThreads = maxThreads;
	}
	
	/**
	 * @param applicationDurationType
	 */
	@Required
	public void setApplicationDurationType(ApplicationDurationType applicationDurationType) {
		CipherSolutionExecutor.applicationDurationType = applicationDurationType;
	}
	
	/**
	 * Not required since the duration type may not be iteration-based.
	 * 
	 * @param numIterations
	 */
	public void setNumIterations(long numIterations) {
		CipherSolutionExecutor.numIterations = numIterations;
	}
	
	/**
	 * Not required since the duration type may not be temporal.
	 * 
	 * @param numIterations
	 */
	public void setApplicationRunMillis(long applicationRunMillis) {
		CipherSolutionExecutor.applicationRunMillis = applicationRunMillis;
	}
}
