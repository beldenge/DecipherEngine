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

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Solution;

public class PopulationTest {
	private static Logger log = Logger.getLogger(PopulationTest.class);
	private static ApplicationContext context;
	private static Population population;
	private static final int populationSize = 100;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic-test.xml");
		log.info("Spring context created successfully!");
		GeneticAlgorithm geneticAlgorithm = (GeneticAlgorithm) context.getBean("geneticAlgorithm");

		CipherDao cipherDao = (CipherDao) context.getBean("cipherDao");

		FitnessEvaluator fitnessEvaluator = (FitnessEvaluator) context
				.getBean("defaultFitnessEvaluator");

		CrossoverAlgorithm crossoverAlgorithm = (CrossoverAlgorithm) context
				.getBean("defaultCrossoverAlgorithm");

		MutationAlgorithm mutationAlgorithm = (MutationAlgorithm) context
				.getBean("defaultMutationAlgorithm");

		Cipher cipher = cipherDao.findByCipherName("zodiac340");
		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(cipher,
				100, -1, 50, 0.9, 0.001, 0.05, fitnessEvaluator, crossoverAlgorithm,
				mutationAlgorithm);

		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);
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

		population.evaluateFitness(null);

		Chromosome chromosome = population.spinObjectRouletteWheel();

		assertNotNull(chromosome);
	}

	@Test
	public void testSpinIndexRouletteWheel() {
		// Just in case a previous test modified the population
		population.populateIndividuals(populationSize);

		population.evaluateFitness(null);

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
