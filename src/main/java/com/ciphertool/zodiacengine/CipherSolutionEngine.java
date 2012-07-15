package com.ciphertool.zodiacengine;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.dto.CipherDto;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.util.SolutionEvaluator;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class CipherSolutionEngine {
	private static Logger log = Logger.getLogger(CipherSolutionEngine.class);
	private static long numIterations;
	private static int maxThreads;
	private static int monitorSleepMillis;
	private static CipherDao cipherDao;
	private static String cipherName;
	private static SolutionGenerator solutionGenerator;
	private static SolutionEvaluator solutionEvaluator;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Spin up the Spring application context
		setUp();

		CipherDto cipherDto = null;
		Runnable cipherTask = null;
		Thread cipherWorker = null;
		long threadIterations = 0;
		int cipherId = cipherDao.findByCipherName(cipherName).getId();

		long start = System.currentTimeMillis();

		List<Thread> threads = new ArrayList<Thread>();
		List<CipherDto> cipherDtos = new ArrayList<CipherDto>();

		if (maxThreads > numIterations) {
			log.warn("The number of threads is greater than the number of tasks.  Reducing thread count to "
					+ numIterations + ".");

			maxThreads = (int) numIterations;
		}

		log.info("Beginning solution generation.  Generating " + numIterations
				+ " solutions using " + maxThreads + " threads.");

		for (int i = 1; i <= maxThreads; i++) {
			threadIterations = (numIterations / maxThreads);
			if (i == 1) {
				/*
				 * If the number of iterations doesn't divide evenly among the
				 * threads, add the remainder to the first thread
				 */
				threadIterations += (numIterations % maxThreads);
			}

			cipherDto = new CipherDto(String.valueOf(i), cipherId);
			cipherDtos.add(cipherDto);

			cipherTask = new CipherSolutionRunnable(threadIterations, solutionGenerator,
					solutionEvaluator, cipherDto);

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
					running++;
				}
			}

			/*
			 * There's no need to loop through this as fast as possible. Sleep
			 * for a short period so that there isn't so much overhead from
			 * monitoring the threads' state.
			 */
			Thread.sleep(monitorSleepMillis);
		} while (running > 0);

		long totalSolutions = 0;
		long totalMatchSum = 0;
		long uniqueMatchSum = 0;
		long adjacentMatchSum = 0;

		Solution solutionMostMatches = new Solution(cipherId, 0, 0, 0);
		Solution solutionMostUnique = new Solution(cipherId, 0, 0, 0);
		Solution solutionMostAdjacent = new Solution(cipherId, 0, 0, 0);

		/*
		 * Sum up all data from all CipherDtos passed to the threads
		 */
		for (CipherDto nextCipherDto : cipherDtos) {
			log.debug("Best solution from thread " + nextCipherDto.getThreadName() + ": "
					+ nextCipherDto.getSolutionMostMatches());
			log.debug("Most unique solution from thread " + nextCipherDto.getThreadName() + ": "
					+ nextCipherDto.getSolutionMostUnique());
			log.debug("Solution with most adjacent matches from thread "
					+ nextCipherDto.getThreadName() + ": "
					+ nextCipherDto.getSolutionMostAdjacent());

			totalSolutions += nextCipherDto.getNumSolutions();
			totalMatchSum += nextCipherDto.getTotalMatchSum();
			uniqueMatchSum += nextCipherDto.getUniqueMatchSum();
			adjacentMatchSum += nextCipherDto.getAdjacentMatchSum();

			/*
			 * Find the Solution with the highest number of total matches
			 */
			if (nextCipherDto.getSolutionMostMatches().getTotalMatches() > solutionMostMatches
					.getTotalMatches()) {
				solutionMostMatches = nextCipherDto.getSolutionMostMatches();
			}

			/*
			 * Find the Solution with the highest number of unique matches in
			 * plaintext
			 */
			if (nextCipherDto.getSolutionMostUnique().getUniqueMatches() > solutionMostUnique
					.getUniqueMatches()) {
				solutionMostUnique = nextCipherDto.getSolutionMostUnique();
			}

			/*
			 * Find the Solution with the highest number of adjacent matches in
			 * plaintext
			 */
			if (nextCipherDto.getSolutionMostAdjacent().getAdjacentMatchCount() > solutionMostAdjacent
					.getAdjacentMatchCount()) {
				solutionMostAdjacent = nextCipherDto.getSolutionMostAdjacent();
			}
		}

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate and validate "
				+ totalSolutions + " solutions.");
		log.info("Most total matches achieved: " + solutionMostMatches.getTotalMatches());
		log.info("Average total matches: " + (totalMatchSum / totalSolutions));
		log.info("Best solution found: " + solutionMostMatches);
		log.info("Most unique matches achieved: " + solutionMostUnique.getUniqueMatches());
		log.info("Average unique matches: " + (uniqueMatchSum / totalSolutions));
		log.info("Solution with most unique matches found: " + solutionMostUnique);
		log.info("Most adjacent matches achieved: " + solutionMostAdjacent.getAdjacentMatchCount());
		log.info("Average adjacent matches: " + (adjacentMatchSum / totalSolutions));
		log.info("Solution with most adjacent matches found: " + solutionMostAdjacent);
	}

	/**
	 * Spins up the Spring application context
	 */
	private static void setUp() {
		@SuppressWarnings("unused")
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-zodiac.xml");

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
	 * @param monitorSleepMillis
	 *            the monitorSleepMillis to set
	 */
	@Required
	public void setMonitorSleepMillis(int monitorSleepMillis) {
		CipherSolutionEngine.monitorSleepMillis = monitorSleepMillis;
	}

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		CipherSolutionEngine.cipherDao = cipherDao;
	}

	/**
	 * @param cipherName
	 *            the cipherName to set
	 */
	@Required
	public void setCipherName(String cipherName) {
		CipherSolutionEngine.cipherName = cipherName;
	}

	/**
	 * @param solutionGenerator
	 *            the solutionGenerator to set
	 */
	@Required
	public void setSolutionGenerator(SolutionGenerator solutionGenerator) {
		CipherSolutionEngine.solutionGenerator = solutionGenerator;
	}

	/**
	 * @param solutionEvaluator
	 *            the solutionEvaluator to set
	 */
	@Required
	public void setSolutionEvaluator(SolutionEvaluator solutionEvaluator) {
		CipherSolutionEngine.solutionEvaluator = solutionEvaluator;
	}
}
