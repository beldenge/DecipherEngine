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

package com.ciphertool.zodiacengine.genetic.algorithms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.algorithms.BasicGeneticAlgorithm;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LowestCommonGroupCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.SingleSequenceMutationAlgorithm;
import com.ciphertool.genetics.algorithms.selection.ProbabilisticSelectionAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.genetic.GeneticAlgorithmTestBase;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.dao.PlaintextSequenceDao;
import com.ciphertool.zodiacengine.genetic.util.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.genetic.util.SolutionBreeder;

public class BasicGeneticAlgorithmTest extends GeneticAlgorithmTestBase {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(BasicGeneticAlgorithmTest.class);

	private static Population population = new Population();
	private static GeneticAlgorithm geneticAlgorithm;
	private static SolutionBreeder solutionBreederMock;

	private static final int POPULATION_SIZE = 10;
	private static final double SURVIVAL_RATE = 0.9;
	private static final double MUTATION_RATE = 0.1;
	private static final double CROSSOVER_RATE = 1.0;
	private static final int LIFESPAN = -1;
	private static final int MAX_GENERATIONS = 50;
	private static final int MAX_THREADS = 4;
	private static final int THREAD_EXECUTOR_QUEUE_CAPACITY = 100;

	@BeforeClass
	public static void setUp() {
		FitnessEvaluator fitnessEvaluator = new CipherSolutionKnownSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		CrossoverAlgorithm crossoverAlgorithm = new LowestCommonGroupCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);

		SingleSequenceMutationAlgorithm mutationAlgorithm = new SingleSequenceMutationAlgorithm();
		mutationAlgorithm.setSequenceDao(new PlaintextSequenceDao());

		ProbabilisticSelectionAlgorithm selectionAlgorithm = new ProbabilisticSelectionAlgorithm();

		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy(zodiac408,
				POPULATION_SIZE, LIFESPAN, MAX_GENERATIONS, SURVIVAL_RATE, MUTATION_RATE,
				CROSSOVER_RATE, fitnessEvaluator, crossoverAlgorithm, mutationAlgorithm,
				selectionAlgorithm);

		solutionBreederMock = mock(SolutionBreeder.class);
		population.setBreeder(solutionBreederMock);

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
	}

	@Before
	public void repopulate() {
		population.clearIndividuals();

		when(solutionBreederMock.breed()).thenReturn(knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone(), knownSolution.clone(),
				knownSolution.clone(), knownSolution.clone());

		population.breed(POPULATION_SIZE);
		population.evaluateFitness(null);
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

		geneticAlgorithm.mutate();

		int numEqualIndividuals = 0;
		for (int i = 0; i < population.size(); i++) {
			if (clonedIndividuals.get(i).equals(population.getIndividuals().get(i))) {
				numEqualIndividuals++;
			}
		}

		assertEquals(population.size(), POPULATION_SIZE);
		assertEquals(numEqualIndividuals, POPULATION_SIZE - 1);
	}

	@Test
	public void testCrossover() {
		SolutionChromosome dummySolution = knownSolution.clone();

		dummySolution.getGenes().get(0).insertSequence(
				0,
				new PlaintextSequence(new PlaintextId(dummySolution, 0), "i", dummySolution
						.getGenes().get(0)));

		for (Plaintext plaintext : dummySolution.getPlaintextCharacters()) {
			plaintext.setValue("*");
		}

		population.clearIndividuals();
		population.addIndividual(knownSolution);
		population.addIndividual(dummySolution);

		List<SolutionChromosome> clonedIndividuals = new ArrayList<SolutionChromosome>();

		for (Chromosome individual : population.getIndividuals()) {
			clonedIndividuals.add((SolutionChromosome) individual.clone());
		}

		geneticAlgorithm.crossover();

		int numEqualIndividuals = 0;
		for (Chromosome individual : population.getIndividuals()) {
			for (int i = 0; i < clonedIndividuals.size(); i++) {
				if (clonedIndividuals.get(i).equals(individual)) {
					numEqualIndividuals++;
				}
			}
		}

		assertEquals(numEqualIndividuals, clonedIndividuals.size());
		assertEquals(population.size(), clonedIndividuals.size() + 2);
	}
}
