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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
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
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.genetic.GeneticAlgorithmTestBase;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.dao.PlaintextSequenceDao;
import com.ciphertool.zodiacengine.genetic.util.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.genetic.util.SolutionChromosomeGenerator;

public class BasicGeneticAlgorithmTest extends GeneticAlgorithmTestBase {
	private static Logger log = Logger.getLogger(BasicGeneticAlgorithmTest.class);

	private static Population population = new Population();
	private static GeneticAlgorithm geneticAlgorithm;
	private static final int POPULATION_SIZE = 10;
	private static final double SURVIVAL_RATE = 0.9;
	private static final double MUTATION_RATE = 0.001;
	private static final double CROSSOVER_RATE = 0.05;
	private static final int LIFESPAN = -1;
	private static final int MAX_GENERATIONS = 50;

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
		population.populateIndividuals(10);
		population.evaluateFitness(null);

		geneticAlgorithm = new BasicGeneticAlgorithm();
		geneticAlgorithm.setPopulation(population);
		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);
	}

	@Test
	public void testSelect() {
		((BasicGeneticAlgorithm) geneticAlgorithm).getStrategy().setSurvivalRate(0.9);
		((BasicGeneticAlgorithm) geneticAlgorithm).getStrategy().setPopulationSize(10);

		SolutionChromosome bestChromosome = (SolutionChromosome) population.getBestFitIndividual();
		log.info("Best fit individual before: " + bestChromosome);

		geneticAlgorithm.select();

		assertEquals(population.size(), 9);

		log.info("Best fit individual after: "
				+ ((SolutionChromosome) population.getBestFitIndividual()));
		assertTrue(bestChromosome == (SolutionChromosome) population.getBestFitIndividual());
	}
}
