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

import static org.mockito.Mockito.mock;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.algorithms.ConcurrentBasicGeneticAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LowestCommonGroupCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.SingleSequenceMutationAlgorithm;
import com.ciphertool.genetics.algorithms.selection.ProbabilisticSelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.modes.RouletteSelector;
import com.ciphertool.genetics.algorithms.selection.modes.Selector;
import com.ciphertool.genetics.dao.ExecutionStatisticsDao;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.genetic.dao.PlaintextSequenceDao;
import com.ciphertool.zodiacengine.genetic.util.CipherSolutionKnownSolutionFitnessEvaluator;

public class ConcurrentBasicGeneticAlgorithmTest extends BasicGeneticAlgorithmTest {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ConcurrentBasicGeneticAlgorithmTest.class);

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

		geneticAlgorithm = new ConcurrentBasicGeneticAlgorithm();
		geneticAlgorithm.setPopulation(population);
		geneticAlgorithm.setStrategy(geneticAlgorithmStrategy);
		((ConcurrentBasicGeneticAlgorithm) geneticAlgorithm).setTaskExecutor(taskExecutor);

		ExecutionStatisticsDao executionStatisticsDaoMock = mock(ExecutionStatisticsDao.class);
		((ConcurrentBasicGeneticAlgorithm) geneticAlgorithm)
				.setExecutionStatisticsDao(executionStatisticsDaoMock);
	}
}