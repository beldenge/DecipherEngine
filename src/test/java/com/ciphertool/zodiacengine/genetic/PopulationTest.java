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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.MaximizationFitnessComparator;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.util.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.genetic.util.SolutionBreeder;

public class PopulationTest extends GeneticAlgorithmTestBase {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(PopulationTest.class);
	private static Population population = new Population();
	private static final int POPULATION_SIZE = 10;
	private static final int LIFESPAN = 5;
	private static SolutionBreeder solutionBreederMock = mock(SolutionBreeder.class);
	private static SolutionChromosome dummySolution;

	@BeforeClass
	public static void setUp() {
		population.setBreeder(solutionBreederMock);

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

		population.setLifespan(LIFESPAN);
		population.setFitnessComparator(new MaximizationFitnessComparator());

		dummySolution = knownSolution.clone();
		dummySolution.getGenes().get(0).insertSequence(
				0,
				new PlaintextSequence(new PlaintextId(dummySolution, 0), "i", dummySolution
						.getGenes().get(0)));
	}

	@Before
	public void repopulate() {
		when(solutionBreederMock.breed()).thenReturn(knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone());

		population.breed(POPULATION_SIZE);
		population.evaluateFitness(null);
	}

	@Test
	public void testPopulateIndividuals() {
		population.clearIndividuals();
		assertEquals(population.size(), 0);

		population.breed(POPULATION_SIZE);

		assertEquals(population.size(), POPULATION_SIZE);

		for (Chromosome individual : population.getIndividuals()) {
			assertEquals(individual.actualSize().intValue(), ((Solution) individual)
					.getPlaintextCharacters().size());
		}
	}

	@Test
	public void testSpinIndexRouletteWheel() {
		population.evaluateFitness(null);

		int winningNumber = -1;

		winningNumber = population.spinIndexRouletteWheel();

		assertTrue(winningNumber > -1);
	}

	@Test
	public void testIncreaseAge() {
		assertEquals(population.size(), POPULATION_SIZE);

		for (int i = 0; i < POPULATION_SIZE; i++) {
			population.getIndividuals().get(i).setAge(i);
		}

		population.increaseAge();

		/*
		 * The way we test this makes it work out so that the resulting
		 * population size is equal to LIFESPAN. That's not typical behavior.
		 */
		assertEquals(population.size(), LIFESPAN);

		// Increase age again. One more Chromosome should age off.
		population.increaseAge();

		assertEquals(population.size(), LIFESPAN - 1);

		for (int i = 0; i < population.size(); i++) {
			assertEquals(population.getIndividuals().get(i).getAge(), i + 2);
		}
	}

	@Test
	public void testRemoveIndex() {
		population.addIndividual(dummySolution);
		assertEquals(population.size(), POPULATION_SIZE + 1);

		boolean foundDummy = false;
		for (Chromosome individual : population.getIndividuals()) {
			if (individual.equals(dummySolution)) {
				foundDummy = true;
			}
		}

		assertTrue(foundDummy);

		Double originalTotalFitness = population.getTotalFitness();

		population.removeIndividual(POPULATION_SIZE);
		assertEquals(population.size(), POPULATION_SIZE);

		for (Chromosome individual : population.getIndividuals()) {
			assertNotSame(individual, dummySolution);
		}

		assertTrue(population.getTotalFitness() < originalTotalFitness);
		assertEquals(population.getTotalFitness(), (Double) (originalTotalFitness - dummySolution
				.getFitness()));
	}

	@Test
	public void testRemoveEquals() {
		population.addIndividual(dummySolution);
		assertEquals(population.size(), POPULATION_SIZE + 1);

		boolean foundDummy = false;
		for (Chromosome individual : population.getIndividuals()) {
			if (individual.equals(dummySolution)) {
				foundDummy = true;
			}
		}

		assertTrue(foundDummy);

		Double originalTotalFitness = population.getTotalFitness();

		population.removeIndividual(dummySolution);
		assertEquals(population.size(), POPULATION_SIZE);

		for (Chromosome individual : population.getIndividuals()) {
			assertNotSame(individual, dummySolution);
		}

		assertTrue(population.getTotalFitness() < originalTotalFitness);
		assertEquals(population.getTotalFitness(), (Double) (originalTotalFitness - dummySolution
				.getFitness()));
	}

	@Test
	public void testAdd() {
		assertEquals(population.size(), POPULATION_SIZE);
		Double originalTotalFitness = population.getTotalFitness();

		Chromosome individualToAdd = knownSolution.clone();
		population.addIndividual(individualToAdd);

		assertEquals(population.size(), POPULATION_SIZE + 1);
		assertTrue(population.getTotalFitness() > originalTotalFitness);
		assertEquals(population.getTotalFitness(), (Double) (originalTotalFitness + individualToAdd
				.getFitness()));
	}

	@Test
	public void testSize() {
		assertTrue(population.size() > 0);
		assertEquals(population.size(), population.getIndividuals().size());
	}

	@Test
	public void testClear() {
		assertTrue(population.getTotalFitness() > 0.0);

		population.clearIndividuals();

		assertEquals(population.getTotalFitness(), new Double(0.0));
		assertEquals(population.size(), 0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetIndividuals() {
		List<Chromosome> individuals = population.getIndividuals();

		// The List should be unmodifiable
		individuals.remove(0);
	}

	@Test
	public void testSort() {
		for (int i = 0; i < population.size(); i++) {
			population.getIndividuals().get(i).setFitness(new Double(POPULATION_SIZE - i));
		}

		boolean outOfOrder = false;
		for (int i = 1; i < population.size(); i++) {
			if (population.getIndividuals().get(i).getFitness() < population.getIndividuals().get(
					i - 1).getFitness()) {
				outOfOrder = true;
			}
		}
		assertTrue(outOfOrder);

		population.sortIndividuals();

		for (int i = 1; i < population.size(); i++) {
			if (population.getIndividuals().get(i).getFitness() < population.getIndividuals().get(
					i - 1).getFitness()) {
				fail("Population is out of order after performing sort.  This should not be the case.");
			}
		}
	}

	@Test
	public void testEvaluate() {
		// The fitness should be calculated in the @Before method
		Double originalTotalFitness = population.getTotalFitness();
		assertTrue(originalTotalFitness > 0.0);

		setObject(population, "totalFitness", new Double(0.0));
		assertEquals(population.getTotalFitness(), new Double(0.0));

		population.evaluateFitness(null);

		assertEquals(population.getTotalFitness(), originalTotalFitness);
	}
}
