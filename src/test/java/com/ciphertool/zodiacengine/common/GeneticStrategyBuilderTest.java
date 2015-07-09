/**
 * Copyright 2015 George Belden
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

package com.ciphertool.zodiacengine.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithmType;
import com.ciphertool.genetics.algorithms.crossover.LowestCommonGroupCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.ConservativeMutationAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithmType;
import com.ciphertool.genetics.algorithms.selection.ProbabilisticSelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithmType;
import com.ciphertool.genetics.algorithms.selection.modes.Selector;
import com.ciphertool.genetics.algorithms.selection.modes.SelectorType;
import com.ciphertool.genetics.algorithms.selection.modes.TournamentSelector;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.fitness.FitnessEvaluatorType;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionUniqueWordFitnessEvaluator;

public class GeneticStrategyBuilderTest {
	@Test
	public void testGetCipher() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String cipherNameParameter = ParameterConstants.CIPHER_NAME;
		String cipherNameValue = "zodiac408";

		parametersMap.put(cipherNameParameter, cipherNameValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Cipher cipher = new Cipher();
		CipherDao cipherDaoMock = mock(CipherDao.class);
		when(cipherDaoMock.findByCipherName(eq(cipherNameValue))).thenReturn(cipher);
		geneticStrategyBuilder.setCipherDao(cipherDaoMock);

		Cipher cipherReturned = geneticStrategyBuilder.getCipher(parametersMap);

		verify(cipherDaoMock, times(1)).findByCipherName(eq(cipherNameValue));
		verifyNoMoreInteractions(cipherDaoMock);
		assertSame(cipher, cipherReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCipherNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String cipherNameParameter = ParameterConstants.CIPHER_NAME;

		parametersMap.put(cipherNameParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCipher(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCipherIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String cipherNameParameter = ParameterConstants.CIPHER_NAME;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(cipherNameParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCipher(parametersMap);
	}

	@Test
	public void testGetFitnessEvaluator() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String fitnessEvaluatorParameter = ParameterConstants.FITNESS_EVALUATOR;
		String fitnessEvaluatorValue = FitnessEvaluatorType.CIPHER_SOLUTION_UNIQUE_WORD.name();

		parametersMap.put(fitnessEvaluatorParameter, fitnessEvaluatorValue);

		CipherSolutionUniqueWordFitnessEvaluator fitnessEvalutorToReturn = new CipherSolutionUniqueWordFitnessEvaluator();

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		ApplicationContext applicationContextMock = mock(ApplicationContext.class);
		when(applicationContextMock.getBean(eq(CipherSolutionUniqueWordFitnessEvaluator.class))).thenReturn(
				fitnessEvalutorToReturn);
		geneticStrategyBuilder.setApplicationContext(applicationContextMock);

		FitnessEvaluator fitnessEvaluatorReturned = geneticStrategyBuilder.getFitnessEvaluator(parametersMap);

		verify(applicationContextMock, times(1)).getBean(eq(CipherSolutionUniqueWordFitnessEvaluator.class));
		verifyNoMoreInteractions(applicationContextMock);
		assertSame(fitnessEvalutorToReturn, fitnessEvaluatorReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFitnessEvaluatorNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String fitnessEvaluatorParameter = ParameterConstants.FITNESS_EVALUATOR;

		parametersMap.put(fitnessEvaluatorParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getFitnessEvaluator(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFitnessEvaluatorIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String fitnessEvaluatorParameter = ParameterConstants.FITNESS_EVALUATOR;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(fitnessEvaluatorParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getFitnessEvaluator(parametersMap);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetCrossoverAlgorithm() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String crossoverAlgorithmParameter = ParameterConstants.CROSSOVER_ALGORITHM;
		String crossoverAlgorithmValue = CrossoverAlgorithmType.LOWEST_COMMON_GROUP.name();

		parametersMap.put(crossoverAlgorithmParameter, crossoverAlgorithmValue);

		LowestCommonGroupCrossoverAlgorithm crossoverAlgorithmToReturn = new LowestCommonGroupCrossoverAlgorithm();

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		ApplicationContext applicationContextMock = mock(ApplicationContext.class);
		when(applicationContextMock.getBean(eq(LowestCommonGroupCrossoverAlgorithm.class))).thenReturn(
				crossoverAlgorithmToReturn);
		geneticStrategyBuilder.setApplicationContext(applicationContextMock);

		CrossoverAlgorithm crossoverAlgorithmReturned = geneticStrategyBuilder.getCrossoverAlgorithm(parametersMap);

		verify(applicationContextMock, times(1)).getBean(eq(LowestCommonGroupCrossoverAlgorithm.class));
		verifyNoMoreInteractions(applicationContextMock);
		assertSame(crossoverAlgorithmToReturn, crossoverAlgorithmReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCrossoverAlgorithmNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String crossoverAlgorithmParameter = ParameterConstants.CROSSOVER_ALGORITHM;

		parametersMap.put(crossoverAlgorithmParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCrossoverAlgorithm(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCrossoverAlgorithmIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String crossoverAlgorithmParameter = ParameterConstants.CROSSOVER_ALGORITHM;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(crossoverAlgorithmParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCrossoverAlgorithm(parametersMap);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetMutationAlgorithm() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutationAlgorithmParameter = ParameterConstants.MUTATION_ALGORITHM;
		String mutationAlgorithmValue = MutationAlgorithmType.CONSERVATIVE.name();

		parametersMap.put(mutationAlgorithmParameter, mutationAlgorithmValue);

		ConservativeMutationAlgorithm mutationAlgorithmToReturn = new ConservativeMutationAlgorithm();

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		ApplicationContext applicationContextMock = mock(ApplicationContext.class);
		when(applicationContextMock.getBean(eq(ConservativeMutationAlgorithm.class))).thenReturn(
				mutationAlgorithmToReturn);
		geneticStrategyBuilder.setApplicationContext(applicationContextMock);

		MutationAlgorithm mutationAlgorithmReturned = geneticStrategyBuilder.getMutationAlgorithm(parametersMap);

		verify(applicationContextMock, times(1)).getBean(eq(ConservativeMutationAlgorithm.class));
		verifyNoMoreInteractions(applicationContextMock);
		assertSame(mutationAlgorithmToReturn, mutationAlgorithmReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMutationAlgorithmNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutationAlgorithmParameter = ParameterConstants.MUTATION_ALGORITHM;

		parametersMap.put(mutationAlgorithmParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMutationAlgorithm(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMutationAlgorithmIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutationAlgorithmParameter = ParameterConstants.MUTATION_ALGORITHM;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(mutationAlgorithmParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMutationAlgorithm(parametersMap);
	}

	@Test
	public void testGetSelectionAlgorithm() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String selectionAlgorithmParameter = ParameterConstants.SELECTION_ALGORITHM;
		String selectionAlgorithmValue = SelectionAlgorithmType.PROBABILISTIC.name();

		parametersMap.put(selectionAlgorithmParameter, selectionAlgorithmValue);

		ProbabilisticSelectionAlgorithm selectionAlgorithmToReturn = new ProbabilisticSelectionAlgorithm();

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		ApplicationContext applicationContextMock = mock(ApplicationContext.class);
		when(applicationContextMock.getBean(eq(ProbabilisticSelectionAlgorithm.class))).thenReturn(
				selectionAlgorithmToReturn);
		geneticStrategyBuilder.setApplicationContext(applicationContextMock);

		SelectionAlgorithm selectionAlgorithmReturned = geneticStrategyBuilder.getSelectionAlgorithm(parametersMap);

		verify(applicationContextMock, times(1)).getBean(eq(ProbabilisticSelectionAlgorithm.class));
		verifyNoMoreInteractions(applicationContextMock);
		assertSame(selectionAlgorithmToReturn, selectionAlgorithmReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSelectionAlgorithmNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String selectionAlgorithmParameter = ParameterConstants.SELECTION_ALGORITHM;

		parametersMap.put(selectionAlgorithmParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getSelectionAlgorithm(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSelectionAlgorithmIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String selectionAlgorithmParameter = ParameterConstants.SELECTION_ALGORITHM;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(selectionAlgorithmParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getSelectionAlgorithm(parametersMap);
	}

	@Test
	public void testGetSelectorMethod() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String selectorMethodParameter = ParameterConstants.SELECTOR_METHOD;
		String selectorMethodValue = SelectorType.TOURNAMENT.name();

		parametersMap.put(selectorMethodParameter, selectorMethodValue);

		TournamentSelector selectorMethodToReturn = new TournamentSelector();

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		ApplicationContext applicationContextMock = mock(ApplicationContext.class);
		when(applicationContextMock.getBean(eq(TournamentSelector.class))).thenReturn(selectorMethodToReturn);
		geneticStrategyBuilder.setApplicationContext(applicationContextMock);

		Selector selectorMethodReturned = geneticStrategyBuilder.getSelector(parametersMap);

		verify(applicationContextMock, times(1)).getBean(eq(TournamentSelector.class));
		verifyNoMoreInteractions(applicationContextMock);
		assertSame(selectorMethodToReturn, selectorMethodReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSelectorMethodNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String selectorMethodParameter = ParameterConstants.SELECTOR_METHOD;

		parametersMap.put(selectorMethodParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getSelector(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSelectorMethodIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String selectorMethodParameter = ParameterConstants.SELECTOR_METHOD;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(selectorMethodParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getSelector(parametersMap);
	}

	@Test
	public void testGetPopulationSize() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String populationSizeParameter = ParameterConstants.POPULATION_SIZE;
		Integer populationSizeValue = 1000;

		parametersMap.put(populationSizeParameter, populationSizeValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Integer populationSizeReturned = geneticStrategyBuilder.getPopulationSize(parametersMap);

		assertSame(populationSizeValue, populationSizeReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPopulationSizeNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String populationSizeParameter = ParameterConstants.POPULATION_SIZE;

		parametersMap.put(populationSizeParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getPopulationSize(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPopulationSizeIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String populationSizeParameter = ParameterConstants.POPULATION_SIZE;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(populationSizeParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getPopulationSize(parametersMap);
	}

	@Test
	public void testGetLifespan() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String lifespanParameter = ParameterConstants.LIFESPAN;
		Integer lifespanValue = 15;

		parametersMap.put(lifespanParameter, lifespanValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Integer lifespanReturned = geneticStrategyBuilder.getLifespan(parametersMap);

		assertSame(lifespanValue, lifespanReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLifespanNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String lifespanParameter = ParameterConstants.LIFESPAN;

		parametersMap.put(lifespanParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getLifespan(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLifespanIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String lifespanParameter = ParameterConstants.LIFESPAN;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(lifespanParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getLifespan(parametersMap);
	}

	@Test
	public void testGetNumGenerations() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String numGenerationsParameter = ParameterConstants.NUMBER_OF_GENERATIONS;
		Integer numGenerationsValue = 50;

		parametersMap.put(numGenerationsParameter, numGenerationsValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Integer numGenerationsReturned = geneticStrategyBuilder.getNumGenerations(parametersMap);

		assertSame(numGenerationsValue, numGenerationsReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNumGenerationsNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String numGenerationsParameter = ParameterConstants.NUMBER_OF_GENERATIONS;

		parametersMap.put(numGenerationsParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getNumGenerations(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNumGenerationsIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String numGenerationsParameter = ParameterConstants.NUMBER_OF_GENERATIONS;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(numGenerationsParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getNumGenerations(parametersMap);
	}

	@Test
	public void testGetSurvivalRate() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String survivalRateParameter = ParameterConstants.SURVIVAL_RATE;
		Double survivalRateValue = 0.9;

		parametersMap.put(survivalRateParameter, survivalRateValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Double survivalRateReturned = geneticStrategyBuilder.getSurvivalRate(parametersMap);

		assertSame(survivalRateValue, survivalRateReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSurvivalRateNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String survivalRateParameter = ParameterConstants.SURVIVAL_RATE;

		parametersMap.put(survivalRateParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getSurvivalRate(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSurvivalRateIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String survivalRateParameter = ParameterConstants.SURVIVAL_RATE;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(survivalRateParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getSurvivalRate(parametersMap);
	}

	@Test
	public void testGetMutationRate() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutationRateParameter = ParameterConstants.MUTATION_RATE;
		Double mutationRateValue = 0.05;

		parametersMap.put(mutationRateParameter, mutationRateValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Double mutationRateReturned = geneticStrategyBuilder.getMutationRate(parametersMap);

		assertSame(mutationRateValue, mutationRateReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMutationRateNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutationRateParameter = ParameterConstants.MUTATION_RATE;

		parametersMap.put(mutationRateParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMutationRate(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMutationRateIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutationRateParameter = ParameterConstants.MUTATION_RATE;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(mutationRateParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMutationRate(parametersMap);
	}

	@Test
	public void testGetMaxMutationsPerIndividual() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String maxMutationsPerIndividualParameter = ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL;
		Integer maxMutationsPerIndividualValue = 5;

		parametersMap.put(maxMutationsPerIndividualParameter, maxMutationsPerIndividualValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Integer maxMutationsPerIndividualReturned = geneticStrategyBuilder.getMaxMutationsPerIndividual(parametersMap);

		assertSame(maxMutationsPerIndividualValue, maxMutationsPerIndividualReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMaxMutationsPerIndividualNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String maxMutationsPerIndividualParameter = ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL;

		parametersMap.put(maxMutationsPerIndividualParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMaxMutationsPerIndividual(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMaxMutationsPerIndividualIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String maxMutationsPerIndividualParameter = ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(maxMutationsPerIndividualParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMaxMutationsPerIndividual(parametersMap);
	}

	@Test
	public void testGetCrossoverRate() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String crossoverRateParameter = ParameterConstants.CROSSOVER_RATE;
		Double crossoverRateValue = 0.1;

		parametersMap.put(crossoverRateParameter, crossoverRateValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Double crossoverRateReturned = geneticStrategyBuilder.getCrossoverRate(parametersMap);

		assertSame(crossoverRateValue, crossoverRateReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCrossoverRateNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String crossoverRateParameter = ParameterConstants.CROSSOVER_RATE;

		parametersMap.put(crossoverRateParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCrossoverRate(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCrossoverRateIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String crossoverRateParameter = ParameterConstants.CROSSOVER_RATE;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(crossoverRateParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCrossoverRate(parametersMap);
	}

	@Test
	public void testGetMutateDuringCrossover() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutateDuringCrossoverParameter = ParameterConstants.MUTATE_DURING_CROSSOVER;
		Boolean mutateDuringCrossoverValue = true;

		parametersMap.put(mutateDuringCrossoverParameter, mutateDuringCrossoverValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Boolean mutateDuringCrossoverReturned = geneticStrategyBuilder.getMutateDuringCrossover(parametersMap);

		assertSame(mutateDuringCrossoverValue, mutateDuringCrossoverReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMutateDuringCrossoverNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutateDuringCrossoverParameter = ParameterConstants.MUTATE_DURING_CROSSOVER;

		parametersMap.put(mutateDuringCrossoverParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMutateDuringCrossover(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMutateDuringCrossoverIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String mutateDuringCrossoverParameter = ParameterConstants.MUTATE_DURING_CROSSOVER;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(mutateDuringCrossoverParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getMutateDuringCrossover(parametersMap);
	}

	@Test
	public void testGetCompareToKnown() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String compareToKnownParameter = ParameterConstants.COMPARE_TO_KNOWN_SOLUTION;
		Boolean compareToKnownValue = true;

		parametersMap.put(compareToKnownParameter, compareToKnownValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Boolean compareToKnownReturned = geneticStrategyBuilder.getCompareToKnown(parametersMap);

		assertSame(compareToKnownValue, compareToKnownReturned);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCompareToKnownNullParameter() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String compareToKnownParameter = ParameterConstants.COMPARE_TO_KNOWN_SOLUTION;

		parametersMap.put(compareToKnownParameter, null);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCompareToKnown(parametersMap);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCompareToKnownIncorrectType() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		String compareToKnownParameter = ParameterConstants.COMPARE_TO_KNOWN_SOLUTION;
		Object incorrectlyTypedValue = new Object();

		parametersMap.put(compareToKnownParameter, incorrectlyTypedValue);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		geneticStrategyBuilder.getCompareToKnown(parametersMap);
	}

	@Test
	public void testBuildStrategy() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();
		ApplicationContext applicationContextMock = mock(ApplicationContext.class);
		geneticStrategyBuilder.setApplicationContext(applicationContextMock);

		/*
		 * Set up all parameters
		 */
		String cipherNameParameter = ParameterConstants.CIPHER_NAME;
		String cipherNameValue = "zodiac408";
		parametersMap.put(cipherNameParameter, cipherNameValue);

		String fitnessEvaluatorParameter = ParameterConstants.FITNESS_EVALUATOR;
		String fitnessEvaluatorValue = FitnessEvaluatorType.CIPHER_SOLUTION_UNIQUE_WORD.name();
		parametersMap.put(fitnessEvaluatorParameter, fitnessEvaluatorValue);

		String crossoverAlgorithmParameter = ParameterConstants.CROSSOVER_ALGORITHM;
		String crossoverAlgorithmValue = CrossoverAlgorithmType.LOWEST_COMMON_GROUP.name();
		parametersMap.put(crossoverAlgorithmParameter, crossoverAlgorithmValue);

		String mutationAlgorithmParameter = ParameterConstants.MUTATION_ALGORITHM;
		String mutationAlgorithmValue = MutationAlgorithmType.CONSERVATIVE.name();
		parametersMap.put(mutationAlgorithmParameter, mutationAlgorithmValue);

		String selectionAlgorithmParameter = ParameterConstants.SELECTION_ALGORITHM;
		String selectionAlgorithmValue = SelectionAlgorithmType.PROBABILISTIC.name();
		parametersMap.put(selectionAlgorithmParameter, selectionAlgorithmValue);

		String selectorMethodParameter = ParameterConstants.SELECTOR_METHOD;
		String selectorMethodValue = SelectorType.TOURNAMENT.name();
		parametersMap.put(selectorMethodParameter, selectorMethodValue);

		String populationSizeParameter = ParameterConstants.POPULATION_SIZE;
		Integer populationSizeValue = 1000;
		parametersMap.put(populationSizeParameter, populationSizeValue);

		String lifespanParameter = ParameterConstants.LIFESPAN;
		Integer lifespanValue = 15;
		parametersMap.put(lifespanParameter, lifespanValue);

		String numGenerationsParameter = ParameterConstants.NUMBER_OF_GENERATIONS;
		Integer numGenerationsValue = 50;
		parametersMap.put(numGenerationsParameter, numGenerationsValue);

		String survivalRateParameter = ParameterConstants.SURVIVAL_RATE;
		Double survivalRateValue = 0.9;
		parametersMap.put(survivalRateParameter, survivalRateValue);

		String mutationRateParameter = ParameterConstants.MUTATION_RATE;
		Double mutationRateValue = 0.05;
		parametersMap.put(mutationRateParameter, mutationRateValue);

		String maxMutationsPerIndividualParameter = ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL;
		Integer maxMutationsPerIndividualValue = 5;
		parametersMap.put(maxMutationsPerIndividualParameter, maxMutationsPerIndividualValue);

		String crossoverRateParameter = ParameterConstants.CROSSOVER_RATE;
		Double crossoverRateValue = 0.1;
		parametersMap.put(crossoverRateParameter, crossoverRateValue);

		String mutateDuringCrossoverParameter = ParameterConstants.MUTATE_DURING_CROSSOVER;
		Boolean mutateDuringCrossoverValue = true;
		parametersMap.put(mutateDuringCrossoverParameter, mutateDuringCrossoverValue);

		String compareToKnownParameter = ParameterConstants.COMPARE_TO_KNOWN_SOLUTION;
		Boolean compareToKnownValue = true;
		parametersMap.put(compareToKnownParameter, compareToKnownValue);

		/*
		 * Set up required mocks
		 */
		Cipher cipher = new Cipher();
		cipher.setHasKnownSolution(true);
		CipherDao cipherDaoMock = mock(CipherDao.class);
		when(cipherDaoMock.findByCipherName(eq(cipherNameValue))).thenReturn(cipher);
		geneticStrategyBuilder.setCipherDao(cipherDaoMock);

		CipherSolutionUniqueWordFitnessEvaluator fitnessEvalutorToReturn = new CipherSolutionUniqueWordFitnessEvaluator();
		when(applicationContextMock.getBean(eq(CipherSolutionUniqueWordFitnessEvaluator.class))).thenReturn(
				fitnessEvalutorToReturn);

		LowestCommonGroupCrossoverAlgorithm crossoverAlgorithmToReturn = new LowestCommonGroupCrossoverAlgorithm();
		when(applicationContextMock.getBean(eq(LowestCommonGroupCrossoverAlgorithm.class))).thenReturn(
				crossoverAlgorithmToReturn);

		ConservativeMutationAlgorithm mutationAlgorithmToReturn = new ConservativeMutationAlgorithm();
		when(applicationContextMock.getBean(eq(ConservativeMutationAlgorithm.class))).thenReturn(
				mutationAlgorithmToReturn);

		ProbabilisticSelectionAlgorithm selectionAlgorithmToReturn = new ProbabilisticSelectionAlgorithm();
		when(applicationContextMock.getBean(eq(ProbabilisticSelectionAlgorithm.class))).thenReturn(
				selectionAlgorithmToReturn);

		TournamentSelector selectorMethodToReturn = new TournamentSelector();
		when(applicationContextMock.getBean(eq(TournamentSelector.class))).thenReturn(selectorMethodToReturn);

		CipherSolutionKnownSolutionFitnessEvaluator knownSolutionfitnessEvalutorToSet = new CipherSolutionKnownSolutionFitnessEvaluator();
		geneticStrategyBuilder.setKnownSolutionFitnessEvaluator(knownSolutionfitnessEvalutorToSet);

		/*
		 * Give it a whirl
		 */
		GeneticAlgorithmStrategy geneticAlgorithmStrategy = geneticStrategyBuilder.buildStrategy(parametersMap);

		/*
		 * Validate everything
		 */
		assertSame(cipher, geneticAlgorithmStrategy.getGeneticStructure());
		assertSame(fitnessEvalutorToReturn, geneticAlgorithmStrategy.getFitnessEvaluator());
		assertSame(crossoverAlgorithmToReturn, geneticAlgorithmStrategy.getCrossoverAlgorithm());
		assertSame(mutationAlgorithmToReturn, geneticAlgorithmStrategy.getMutationAlgorithm());
		assertSame(selectionAlgorithmToReturn, geneticAlgorithmStrategy.getSelectionAlgorithm());
		assertSame(selectorMethodToReturn, geneticAlgorithmStrategy.getSelector());
		assertSame(knownSolutionfitnessEvalutorToSet, geneticAlgorithmStrategy.getKnownSolutionFitnessEvaluator());
		assertSame(populationSizeValue, geneticAlgorithmStrategy.getPopulationSize());
		assertSame(lifespanValue, geneticAlgorithmStrategy.getLifespan());
		assertSame(numGenerationsValue, geneticAlgorithmStrategy.getMaxGenerations());
		assertSame(survivalRateValue, geneticAlgorithmStrategy.getSurvivalRate());
		assertSame(mutationRateValue, geneticAlgorithmStrategy.getMutationRate());
		assertSame(maxMutationsPerIndividualValue, geneticAlgorithmStrategy.getMaxMutationsPerIndividual());
		assertSame(crossoverRateValue, geneticAlgorithmStrategy.getCrossoverRate());
		assertSame(mutateDuringCrossoverValue, geneticAlgorithmStrategy.getMutateDuringCrossover());
		assertSame(compareToKnownValue, geneticAlgorithmStrategy.getCompareToKnownSolution());

		/*
		 * Try again without comparing to known solution
		 */
		parametersMap.put(compareToKnownParameter, false);

		geneticAlgorithmStrategy = geneticStrategyBuilder.buildStrategy(parametersMap);

		assertSame(cipher, geneticAlgorithmStrategy.getGeneticStructure());
		assertSame(fitnessEvalutorToReturn, geneticAlgorithmStrategy.getFitnessEvaluator());
		assertSame(crossoverAlgorithmToReturn, geneticAlgorithmStrategy.getCrossoverAlgorithm());
		assertSame(mutationAlgorithmToReturn, geneticAlgorithmStrategy.getMutationAlgorithm());
		assertSame(selectionAlgorithmToReturn, geneticAlgorithmStrategy.getSelectionAlgorithm());
		assertSame(selectorMethodToReturn, geneticAlgorithmStrategy.getSelector());
		assertSame(knownSolutionfitnessEvalutorToSet, geneticAlgorithmStrategy.getKnownSolutionFitnessEvaluator());
		assertSame(populationSizeValue, geneticAlgorithmStrategy.getPopulationSize());
		assertSame(lifespanValue, geneticAlgorithmStrategy.getLifespan());
		assertSame(numGenerationsValue, geneticAlgorithmStrategy.getMaxGenerations());
		assertSame(survivalRateValue, geneticAlgorithmStrategy.getSurvivalRate());
		assertSame(mutationRateValue, geneticAlgorithmStrategy.getMutationRate());
		assertSame(maxMutationsPerIndividualValue, geneticAlgorithmStrategy.getMaxMutationsPerIndividual());
		assertSame(crossoverRateValue, geneticAlgorithmStrategy.getCrossoverRate());
		assertSame(mutateDuringCrossoverValue, geneticAlgorithmStrategy.getMutateDuringCrossover());
		assertFalse(geneticAlgorithmStrategy.getCompareToKnownSolution());

		/*
		 * Try comparing to known solution when none exists
		 */
		parametersMap.put(compareToKnownParameter, true);

		cipher.setHasKnownSolution(false);
		when(cipherDaoMock.findByCipherName(eq(cipherNameValue))).thenReturn(cipher);

		boolean exceptionCaught = false;
		try {
			geneticStrategyBuilder.buildStrategy(parametersMap);
		} catch (IllegalStateException ise) {
			exceptionCaught = true;
		}

		assertTrue(exceptionCaught);

		/*
		 * Try comparing to known solution when no known solution fitness evaluator implementation exists
		 */
		cipher.setHasKnownSolution(true);
		when(cipherDaoMock.findByCipherName(eq(cipherNameValue))).thenReturn(cipher);

		geneticStrategyBuilder.setKnownSolutionFitnessEvaluator(null);

		exceptionCaught = false;
		try {
			geneticStrategyBuilder.buildStrategy(parametersMap);
		} catch (IllegalStateException ise) {
			exceptionCaught = true;
		}

		assertTrue(exceptionCaught);
	}
}
