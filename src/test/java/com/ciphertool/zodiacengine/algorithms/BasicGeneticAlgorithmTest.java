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

package com.ciphertool.zodiacengine.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.algorithms.BasicGeneticAlgorithm;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LowestCommonGroupCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.SingleSequenceMutationAlgorithm;
import com.ciphertool.genetics.algorithms.selection.ProbabilisticSelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.modes.RouletteSelector;
import com.ciphertool.genetics.algorithms.selection.modes.Selector;
import com.ciphertool.genetics.dao.ExecutionStatisticsDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.SolutionBreeder;
import com.ciphertool.zodiacengine.dao.PlaintextSequenceDao;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionKnownSolutionFitnessEvaluator;

public class BasicGeneticAlgorithmTest extends GeneticAlgorithmTestBase {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(BasicGeneticAlgorithmTest.class);

	protected static Population population = new Population();
	protected static GeneticAlgorithm geneticAlgorithm;
	protected static SolutionBreeder solutionBreederMock = mock(SolutionBreeder.class);;

	protected static final int POPULATION_SIZE = 10;
	protected static final double SURVIVAL_RATE = 0.9;
	protected static final double MUTATION_RATE = 0.1;
	protected static final int MAX_MUTATIONS_PER_INDIVIDUAL = 5;
	protected static final double CROSSOVER_RATE = 1.0;
	protected static final int LIFESPAN = -1;
	protected static final int MAX_GENERATIONS = 2;
	protected static final int MAX_THREADS = 4;
	protected static final int THREAD_EXECUTOR_QUEUE_CAPACITY = 100;
	protected static final boolean MUTATE_DURING_CROSSOVER = true;

	@BeforeClass
	public static void setUp() {
		FitnessEvaluator fitnessEvaluator = new CipherSolutionKnownSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		CrossoverAlgorithm crossoverAlgorithm = new LowestCommonGroupCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);

		SingleSequenceMutationAlgorithm mutationAlgorithm = new SingleSequenceMutationAlgorithm();
		mutationAlgorithm.setSequenceDao(new PlaintextSequenceDao());

		ProbabilisticSelectionAlgorithm selectionAlgorithm = new ProbabilisticSelectionAlgorithm();

		Selector selector = new RouletteSelector();

		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(zodiac408,
				POPULATION_SIZE, LIFESPAN, MAX_GENERATIONS, SURVIVAL_RATE, MUTATION_RATE,
				MAX_MUTATIONS_PER_INDIVIDUAL, CROSSOVER_RATE, MUTATE_DURING_CROSSOVER,
				fitnessEvaluator, crossoverAlgorithm, mutationAlgorithm, selectionAlgorithm,
				selector);

		population.setBreeder(solutionBreederMock);
		population.setSelector(selector);

		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(MAX_THREADS);
		taskExecutor.setMaxPoolSize(MAX_THREADS);
		taskExecutor.setQueueCapacity(THREAD_EXECUTOR_QUEUE_CAPACITY);
		taskExecutor.setKeepAliveSeconds(1);
		taskExecutor.setAllowCoreThreadTimeOut(true);
		taskExecutor.initialize();

		population.setTaskExecutor(taskExecutor);

		geneticAlgorithm = new BasicGeneticAlgorithm();
		geneticAlgorithm.setPopulation(population);
		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);

		ExecutionStatisticsDao executionStatisticsDaoMock = mock(ExecutionStatisticsDao.class);
		((BasicGeneticAlgorithm) geneticAlgorithm)
				.setExecutionStatisticsDao(executionStatisticsDaoMock);
	}

	@Before
	public void repopulate() {
		population.clearIndividuals();

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
	public void testSpawnInitialPopulation() {
		List<Chromosome> individualsBefore = new ArrayList<Chromosome>();
		int originalSize = population.size();
		for (int i = 0; i < originalSize; i++) {
			((SolutionChromosome) population.getIndividuals().get(0)).setId(new BigInteger(String
					.valueOf(i)));
			individualsBefore.add(population.removeIndividual(0));
		}

		assertEquals(population.size(), 0);
		assertEquals(geneticAlgorithm.getPopulation().size(), 0);

		try {
			invokeMethod(geneticAlgorithm, "initialize", null, null);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertEquals(population.size(), POPULATION_SIZE);

		for (Chromosome individual : population.getIndividuals()) {
			// Make sure each individual has been evaluated
			assertTrue(individual.getFitness() > 0.0);

			// Make sure each individual is new
			for (Chromosome individualBefore : individualsBefore) {
				assertFalse(individual == individualBefore);
			}
		}
	}

	@Test
	public void testSelect() {
		assertEquals(population.size(), POPULATION_SIZE);

		geneticAlgorithm.select();

		assertEquals(population.size(), POPULATION_SIZE - 1);
	}

	@Test
	public void testMutate() {
		List<SolutionChromosome> clonedIndividuals = new ArrayList<SolutionChromosome>();

		for (Chromosome individual : population.getIndividuals()) {
			clonedIndividuals.add((SolutionChromosome) individual.clone());
		}

		geneticAlgorithm.mutate(population.size());
		population.resetEligibility();

		int numEqualIndividuals = 0;
		for (int i = 0; i < clonedIndividuals.size(); i++) {
			if (clonedIndividuals.get(i).equals(population.getIndividuals().get(i))) {
				numEqualIndividuals++;
			}
		}

		assertEquals(population.size(), POPULATION_SIZE + 1);
		assertEquals(numEqualIndividuals, POPULATION_SIZE);
	}

	@Test
	public void testCrossover() {
		SolutionChromosome dummySolution1 = knownSolution.clone();
		SolutionChromosome dummySolution2 = knownSolution.clone();

		for (Plaintext plaintext : dummySolution1.getPlaintextCharacters()) {
			plaintext.setValue("a");
		}

		for (Plaintext plaintext : dummySolution2.getPlaintextCharacters()) {
			plaintext.setValue("b");
		}

		population.clearIndividuals();
		population.addIndividual(dummySolution1);
		population.addIndividual(dummySolution2);

		List<SolutionChromosome> clonedIndividuals = new ArrayList<SolutionChromosome>();

		for (Chromosome individual : population.getIndividuals()) {
			clonedIndividuals.add((SolutionChromosome) individual.clone());
		}

		geneticAlgorithm.crossover(population.size());
		population.resetEligibility();

		int numEqualIndividuals = 0;
		for (Chromosome individual : population.getIndividuals()) {
			if (clonedIndividuals.contains(individual)) {
				numEqualIndividuals++;
			}
		}

		assertEquals(numEqualIndividuals, clonedIndividuals.size());
		assertEquals(population.size(), clonedIndividuals.size() + 1);
	}

	@Test
	public void testEvolve() {
		GeneticAlgorithm geneticAlgorithmSpy = spy(geneticAlgorithm);
		geneticAlgorithmSpy.evolveAutonomously();

		/*
		 * Make sure the loop executes only twice and that all pertinent methods
		 * are called
		 */
		InOrder inOrder = inOrder(geneticAlgorithmSpy);
		inOrder.verify(geneticAlgorithmSpy, times(1)).select();
		inOrder.verify(geneticAlgorithmSpy, times(1)).crossover(anyInt());
		inOrder.verify(geneticAlgorithmSpy, times(1)).mutate(anyInt());
		inOrder.verify(geneticAlgorithmSpy, times(1)).select();
		inOrder.verify(geneticAlgorithmSpy, times(1)).crossover(anyInt());
		inOrder.verify(geneticAlgorithmSpy, times(1)).mutate(anyInt());
		inOrder.verify(geneticAlgorithmSpy, times(0)).select();
		inOrder.verify(geneticAlgorithmSpy, times(0)).crossover(anyInt());
		inOrder.verify(geneticAlgorithmSpy, times(0)).mutate(anyInt());
	}
}
