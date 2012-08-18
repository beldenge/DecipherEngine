package com.ciphertool.zodiacengine.genetic;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;

public class GeneticCipherSolutionEngine {
	private static Logger log = Logger.getLogger(GeneticCipherSolutionEngine.class);
	@SuppressWarnings("unused")
	private static ApplicationContext context;
	private static GeneticAlgorithm geneticAlgorithm;
	private static String[] commandsBefore;
	private static String[] commandsAfter;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Spin up the Spring application context
		setUp();

		long start = System.currentTimeMillis();

		geneticAlgorithm.spawnInitialPopulation();

		log.info("Took " + (System.currentTimeMillis() - start)
				+ "ms to spawn initial population of size "
				+ geneticAlgorithm.getPopulation().size());

		List<Chromosome> bestFitIndividuals = geneticAlgorithm.iterateUntilTermination();

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to finish.");
		log.info("Best " + bestFitIndividuals.size() + " solutions in ascending order: ");
		for (Chromosome bestFitIndividual : bestFitIndividuals) {
			log.info(bestFitIndividual);
		}

		tearDown();
	}

	/**
	 * Spins up the Spring application context
	 */
	private static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");

		log.info("Spring context created successfully!");

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

	private static void tearDown() {
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
		GeneticCipherSolutionEngine.geneticAlgorithm = geneticAlgorithm;
	}

	/**
	 * @param commandsBefore
	 *            the commandsBefore to set
	 */
	@Required
	public static void setCommandsBefore(String[] commandsBefore) {
		GeneticCipherSolutionEngine.commandsBefore = commandsBefore;
	}

	/**
	 * @param commandsAfter
	 *            the commandsAfter to set
	 */
	@Required
	public static void setCommandsAfter(String[] commandsAfter) {
		GeneticCipherSolutionEngine.commandsAfter = commandsAfter;
	}
}
