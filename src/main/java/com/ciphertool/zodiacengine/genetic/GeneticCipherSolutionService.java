package com.ciphertool.zodiacengine.genetic;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.zodiacengine.AbstractCipherSolutionService;

public class GeneticCipherSolutionService extends AbstractCipherSolutionService {
	private Logger log = Logger.getLogger(getClass());

	private GeneticAlgorithm geneticAlgorithm;
	private String[] commandsBefore;
	private String[] commandsAfter;
	private long start;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		start = System.currentTimeMillis();

		geneticAlgorithm.spawnInitialPopulation();

		log.info("Took " + (System.currentTimeMillis() - start)
				+ "ms to spawn initial population of size "
				+ geneticAlgorithm.getPopulation().size());

		geneticAlgorithm.iterateUntilTermination();

		end();
	}

	public void stop() {
		List<Chromosome> bestFitIndividuals = geneticAlgorithm.getBestFitIndividuals();

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to finish.");
		log.info("Best " + bestFitIndividuals.size() + " solutions in ascending order: ");
		for (Chromosome bestFitIndividual : bestFitIndividuals) {
			log.info(bestFitIndividual);
		}
	}

	protected void setUp() {
		/*
		 * currentCommand is really just used for exception catching.
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
}
