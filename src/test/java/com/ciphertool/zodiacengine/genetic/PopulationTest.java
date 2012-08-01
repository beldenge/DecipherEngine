package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.zodiacengine.entities.Solution;

public class PopulationTest {
	private static Logger log = Logger.getLogger(PopulationTest.class);
	private static ApplicationContext context;
	private static Population population;
	private static final int populationSize = 100;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");
		log.info("Spring context created successfully!");
		population = (Population) context.getBean("population");
	}

	@Test
	public void testPopulateIndividuals() {
		population.populateIndividuals(populationSize);

		assertEquals(population.size(), populationSize);

		for (Chromosome individual : population.getIndividuals()) {
			assertEquals(individual.actualSize().intValue(), ((Solution) individual)
					.getPlaintextCharacters().size());
		}
	}

	@Test
	public void testSpinObjectRouletteWheel() {
		// Just in case a previous test modified the population
		population.populateIndividuals(populationSize);

		population.evaluateFitness();

		Chromosome chromosome = population.spinObjectRouletteWheel();

		assertNotNull(chromosome);
	}

	@Test
	public void testSpinIndexRouletteWheel() {
		// Just in case a previous test modified the population
		population.populateIndividuals(populationSize);

		population.evaluateFitness();

		int winningNumber = -1;

		winningNumber = population.spinIndexRouletteWheel();

		assertTrue(winningNumber > -1);
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		((ClassPathXmlApplicationContext) context).close();
		population = null;
		context = null;
	}
}
