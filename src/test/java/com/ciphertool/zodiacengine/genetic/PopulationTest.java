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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.genetic.util.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.genetic.util.SolutionChromosomeGenerator;

public class PopulationTest extends GeneticAlgorithmTestBase {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(PopulationTest.class);
	private static Population population = new Population();
	private static final int populationSize = 100;

	@BeforeClass
	public static void setUp() {
		SolutionChromosomeGenerator solutionChromosomeGeneratorMock = mock(SolutionChromosomeGenerator.class);
		when(solutionChromosomeGeneratorMock.generateChromosome())
				.thenReturn(knownSolution.clone());
		population.setChromosomeGenerator(solutionChromosomeGeneratorMock);

		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.setKeepAliveSeconds(1);
		taskExecutor.setAllowCoreThreadTimeOut(true);
		taskExecutor.initialize();

		population.setTaskExecutor(taskExecutor);

		CipherSolutionKnownSolutionFitnessEvaluator cipherSolutionKnownSolutionFitnessEvaluator = new CipherSolutionKnownSolutionFitnessEvaluator();
		cipherSolutionKnownSolutionFitnessEvaluator.setGeneticStructure(zodiac408);
		population.setFitnessEvaluator(cipherSolutionKnownSolutionFitnessEvaluator);
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
}
