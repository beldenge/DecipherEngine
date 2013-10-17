/**
 * Copyright 2013 George Belden
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

package com.ciphertool.zodiacengine.service;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.zodiacengine.dao.SolutionDao;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.view.GenericCallback;

public class GeneticCipherSolutionService extends AbstractCipherSolutionService {
	private Logger log = Logger.getLogger(getClass());

	private GeneticAlgorithm geneticAlgorithm;
	private String[] commandsBefore;
	private String[] commandsAfter;
	private SolutionDao solutionDao;
	private long start;

	public void start(GenericCallback uiCallback, boolean debugMode) {
		start = System.currentTimeMillis();

		if (debugMode) {
			runAlgorithmStepwise();
		} else {
			runAlgorithmAutonomously(uiCallback);
		}
	}

	/**
	 * In this method we ALWAYS want to execute the inherited end() method
	 * 
	 * @param teardownCallback
	 */
	private void runAlgorithmAutonomously(GenericCallback uiCallback) {
		try {
			geneticAlgorithm.evolveAutonomously();
		} catch (Throwable t) {
			log.error("Caught Throwable while running cipher solution service.  "
					+ "Cannot continue.  Performing tear-down tasks.", t);
		} finally {
			end();
			uiCallback.doCallback();
		}
	}

	/**
	 * In this method we only want to execute the inherited end() method if an
	 * exception is caught
	 * 
	 * @param teardownCallback
	 */
	private void runAlgorithmStepwise() {
		try {
			geneticAlgorithm.initialize();

			/*
			 * Print the population every generation since this is debug mode.
			 */
			this.geneticAlgorithm.getPopulation().printAscending();

			geneticAlgorithm.proceedWithNextGeneration();
		} catch (Throwable t) {
			log.error("Caught Throwable while running cipher solution service.  "
					+ "Cannot continue.  Performing tear-down tasks.", t);

			end();
		}
	}

	public void endImmediately(boolean inDebugMode) {
		if (inDebugMode) {
			end();
		} else {
			geneticAlgorithm.requestStop();
		}
	}

	protected void proceed() {
		try {
			/*
			 * Print the population every generation since this is debug mode,
			 * and print beforehand since it will all be printed again at the
			 * finish.
			 */
			this.geneticAlgorithm.getPopulation().printAscending();

			geneticAlgorithm.proceedWithNextGeneration();
		} catch (Throwable t) {
			log.error("Caught Throwable while running cipher solution service.  "
					+ "Cannot continue.  Performing tear-down tasks.", t);

			end();
		}
	}

	public void stop() {
		/*
		 * Print out summary information
		 */
		log.info("Total running time was " + (System.currentTimeMillis() - start) + "ms.");

		this.geneticAlgorithm.getPopulation().printAscending();

		persistPopulation();
	}

	protected void setUp(GeneticAlgorithmStrategy geneticAlgorithmStrategy) {
		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);

		/*
		 * currentCommand is really just used for exception logging.
		 */
		String currentCommand = "";

		try {
			for (String commandBefore : commandsBefore) {
				log.info("Executing shell command on start up: " + commandBefore);
				currentCommand = commandBefore;

				Runtime.getRuntime().exec(commandBefore);
			}
		} catch (IOException e) {
			log.warn("Unable to execute commmand before app begin: " + currentCommand
					+ ".  Continuing.");
		}
	}

	protected void tearDown() {
		/*
		 * currentCommand is really just used for exception catching.
		 */
		String currentCommand = "";

		try {
			for (String commandAfter : commandsAfter) {
				log.info("Executing shell command on termination: " + commandAfter);
				currentCommand = commandAfter;

				Runtime.getRuntime().exec(commandAfter);
			}
		} catch (IOException e) {
			log.warn("Unable to execute commmand after app end: " + currentCommand
					+ ".  Continuing.");
		}
	}

	private void persistPopulation() {
		log.info("Persisting the entire population to database.");

		List<Chromosome> individuals = geneticAlgorithm.getPopulation().getIndividuals();

		if (individuals == null || individuals.size() == 0) {
			log.info("No population to persist to database.  Returning.");

			return;
		}

		int solutionSetId = (int) System.currentTimeMillis();

		long startInsert = System.currentTimeMillis();

		for (Chromosome individual : individuals) {
			// Set the solution set ID as the current timestamp
			((SolutionChromosome) individual).setSolutionSetId(solutionSetId);

			solutionDao.insert((SolutionChromosome) individual);
		}

		log.info("Took " + (System.currentTimeMillis() - startInsert)
				+ "ms to persist population to database.");
	}

	/**
	 * @param geneticAlgorithm
	 *            the geneticAlgorithm to set
	 */
	@Required
	public void setGeneticAlgorithm(GeneticAlgorithm geneticAlgorithm) {
		this.geneticAlgorithm = geneticAlgorithm;
	}

	/**
	 * @param commandsBefore
	 *            the commandsBefore to set
	 */
	@Required
	public void setCommandsBefore(String[] commandsBefore) {
		this.commandsBefore = commandsBefore;
	}

	/**
	 * @param commandsAfter
	 *            the commandsAfter to set
	 */
	@Required
	public void setCommandsAfter(String[] commandsAfter) {
		this.commandsAfter = commandsAfter;
	}

	/**
	 * @param solutionDao
	 *            the solutionDao to set
	 */
	@Required
	public void setSolutionDao(SolutionDao solutionDao) {
		this.solutionDao = solutionDao;
	}
}
