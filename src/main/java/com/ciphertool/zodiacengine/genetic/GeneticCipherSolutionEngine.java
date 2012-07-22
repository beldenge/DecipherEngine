package com.ciphertool.zodiacengine.genetic;

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
				+ "ms to spawn initial population of " + geneticAlgorithm.getPopulation().size());

		List<Chromosome> bestFitIndividuals = geneticAlgorithm.iterateUntilTermination();

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to finish.");
		log.info("Best " + bestFitIndividuals.size() + " solutions in ascending order: ");
		for (Chromosome bestFitIndividual : bestFitIndividuals) {
			log.info(bestFitIndividual);
		}
	}

	/**
	 * Spins up the Spring application context
	 */
	private static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");

		log.info("Spring context created successfully!");
	}

	/**
	 * @param geneticAlgorithm
	 *            the geneticAlgorithm to set
	 */
	@Required
	public void setGeneticAlgorithm(GeneticAlgorithm geneticAlgorithm) {
		GeneticCipherSolutionEngine.geneticAlgorithm = geneticAlgorithm;
	}
}
