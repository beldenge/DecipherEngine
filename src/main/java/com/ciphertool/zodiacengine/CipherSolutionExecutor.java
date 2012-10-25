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

package com.ciphertool.zodiacengine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.dto.CipherDto;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.enumerations.ApplicationDurationType;
import com.ciphertool.zodiacengine.util.SolutionEvaluator;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class CipherSolutionExecutor {
	private static Logger log = Logger.getLogger(CipherSolutionExecutor.class);
	@SuppressWarnings("unused")
	private static BeanFactory factory;
	private static ApplicationDurationType applicationDurationType;
	private static long applicationRunMillis;
	private static long numIterations;
	private static int maxThreads;
	private static CipherDao cipherDao;
	private static String cipherName;
	private static long queueTaskLimit;
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

		int cipherId = cipherDao.findByCipherName(cipherName).getId();

		long start = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

		cipherDto = new CipherDto(String.valueOf(0), cipherId);

		/*
		 * We want to generate and validate a specific number of solutions, no
		 * matter how long it takes.
		 */
		if (applicationDurationType == ApplicationDurationType.ITERATION) {
			if (numIterations <= 0) {
				throw new IllegalArgumentException(
						"ApplicationDurationType set to ITERATION, but numIterations was not set or was set incorrectly.");
			}

			log.info("Beginning solution generation.  Generating " + numIterations
					+ " solutions using " + maxThreads + " threads.");

			for (long i = 1; i <= numIterations; i++) {
				Runnable cipherTask = new CipherSolutionSynchronizedRunnable(solutionGenerator,
						solutionEvaluator, cipherDto);

				executor.execute(cipherTask);
			}

			// Make executor accept no new threads and finish all existing
			// threads in the queue
			executor.shutdown();
		}
		/*
		 * We want to generate and validate solutions for a set amount of time,
		 * no matter how many we can generate in that time period.
		 */
		else if (applicationDurationType == ApplicationDurationType.TEMPORAL) {
			if (applicationRunMillis <= 0) {
				throw new IllegalArgumentException(
						"ApplicationDurationType set to TEMPORAL, but applicationRunMillis was not set or was set incorrectly.");
			}

			log.info("Beginning solution generation.  Generating solutions for "
					+ applicationRunMillis + "ms using " + maxThreads + " threads.");

			long count = 0;

			while (true) {
				Runnable cipherTask = new CipherSolutionSynchronizedRunnable(solutionGenerator,
						solutionEvaluator, cipherDto);

				executor.execute(cipherTask);

				/*
				 * This is a fairly rudimentary way of managing the number of
				 * tasks sent to the executor.
				 * 
				 * If we don't manage it somehow, the app will get bogged down
				 * by the continuous while loop and performance will degrade
				 * significantly.
				 */
				if (++count >= queueTaskLimit) {
					count = 0;

					executor.shutdown();

					/*
					 * We are mainly concerned about blocking until all tasks
					 * are finished, so the timeout is not a big concern.
					 */
					executor.awaitTermination(1, TimeUnit.MINUTES);

					executor = Executors.newFixedThreadPool(maxThreads);

					if ((System.currentTimeMillis() - start) > applicationRunMillis) {
						break;
					}
				}
			}

			// Make executor stop immediately
			executor.shutdownNow();
		}

		// Wait until all threads are finished
		while (!executor.isTerminated()) {
		}

		Solution solutionMostMatches = cipherDto.getSolutionMostMatches();
		Solution solutionMostUnique = cipherDto.getSolutionMostUnique();
		Solution solutionMostAdjacent = cipherDto.getSolutionMostAdjacent();

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate and validate "
				+ cipherDto.getNumSolutions() + " solutions.");
		log.info("Highest total matches achieved: " + solutionMostMatches.getTotalMatches());
		log.info("Average total matches: "
				+ (cipherDto.getTotalMatchSum() / cipherDto.getNumSolutions()));
		log.info("Best solution found: " + solutionMostMatches);
		log.info("Most unique matches achieved: " + solutionMostUnique.getUniqueMatches());
		log.info("Average unique matches: "
				+ (cipherDto.getUniqueMatchSum() / cipherDto.getNumSolutions()));
		log.info("Solution with most unique matches found: " + solutionMostUnique);
		log.info("Most adjacent matches achieved: " + solutionMostAdjacent.getAdjacentMatchCount());
		log.info("Average adjacent matches: "
				+ (cipherDto.getAdjacentMatchSum() / cipherDto.getNumSolutions()));
		log.info("Solution with most adjacent matches found: " + solutionMostAdjacent);
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

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		CipherSolutionExecutor.cipherDao = cipherDao;
	}

	/**
	 * @param cipherName
	 *            the cipherName to set
	 */
	@Required
	public void setCipherName(String cipherName) {
		CipherSolutionExecutor.cipherName = cipherName;
	}

	/**
	 * @param queueTaskLimit
	 *            the queueTaskLimit to set
	 */
	@Required
	public void setQueueTaskLimit(long queueTaskLimit) {
		CipherSolutionExecutor.queueTaskLimit = queueTaskLimit;
	}

	/**
	 * @param solutionGenerator
	 *            the solutionGenerator to set
	 */
	@Required
	public void setSolutionGenerator(SolutionGenerator solutionGenerator) {
		CipherSolutionExecutor.solutionGenerator = solutionGenerator;
	}

	/**
	 * @param solutionEvaluator
	 *            the solutionEvaluator to set
	 */
	@Required
	public void setSolutionEvaluator(SolutionEvaluator solutionEvaluator) {
		CipherSolutionExecutor.solutionEvaluator = solutionEvaluator;
	}
}
