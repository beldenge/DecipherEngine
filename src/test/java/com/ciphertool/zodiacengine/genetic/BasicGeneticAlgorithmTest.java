package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.BasicGeneticAlgorithm;
import com.ciphertool.genetics.GeneticAlgorithm;
import com.ciphertool.genetics.Population;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class BasicGeneticAlgorithmTest {
	private static Logger log = Logger.getLogger(BasicGeneticAlgorithmTest.class);

	private static ApplicationContext context;
	private static Population population;
	private static GeneticAlgorithm geneticAlgorithm;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");
		log.info("Spring context created successfully!");

		population = (Population) context.getBean("population");
		geneticAlgorithm = (GeneticAlgorithm) context.getBean("geneticAlgorithm");

		population.populateIndividuals(10);
		population.evaluateFitness();
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		population = null;
		geneticAlgorithm = null;
		context = null;
	}

	@Test
	public void testSelect() {
		((BasicGeneticAlgorithm) geneticAlgorithm).setSurvivalRate(0.9);

		SolutionChromosome bestChromosome = (SolutionChromosome) population.getBestFitIndividual();
		log.info("Best fit individual before: " + bestChromosome);

		geneticAlgorithm.select();

		assertEquals(population.size(), 9);

		log.info("Best fit individual after: "
				+ ((SolutionChromosome) population.getBestFitIndividual()));
		assertTrue(bestChromosome == (SolutionChromosome) population.getBestFitIndividual());
	}
}
