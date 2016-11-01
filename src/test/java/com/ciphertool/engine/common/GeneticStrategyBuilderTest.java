/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.common;

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

import com.ciphertool.engine.dao.CipherDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.fitness.impl.CipherKeyKnownSolutionFitnessEvaluator;
import com.ciphertool.engine.fitness.impl.CipherKeyUniqueIndexedWordGraphFitnessEvaluator;
import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.impl.EqualOpportunityGeneCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.impl.MultipleMutationAlgorithm;
import com.ciphertool.genetics.algorithms.selection.ProbabilisticSelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.modes.Selector;
import com.ciphertool.genetics.algorithms.selection.modes.TournamentSelector;
import com.ciphertool.genetics.fitness.FitnessEvaluator;

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
		FitnessEvaluator fitnessEvaluatorToReturn = new CipherKeyUniqueIndexedWordGraphFitnessEvaluator();

		parametersMap.put(fitnessEvaluatorParameter, fitnessEvaluatorToReturn);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		FitnessEvaluator fitnessEvaluatorReturned = geneticStrategyBuilder.getFitnessEvaluator(parametersMap);

		assertSame(fitnessEvaluatorToReturn, fitnessEvaluatorReturned);
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
		CrossoverAlgorithm crossoverAlgorithmToReturn = new EqualOpportunityGeneCrossoverAlgorithm();

		parametersMap.put(crossoverAlgorithmParameter, crossoverAlgorithmToReturn);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		CrossoverAlgorithm crossoverAlgorithmReturned = geneticStrategyBuilder.getCrossoverAlgorithm(parametersMap);

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
		MultipleMutationAlgorithm mutationAlgorithmToReturn = new MultipleMutationAlgorithm();

		parametersMap.put(mutationAlgorithmParameter, mutationAlgorithmToReturn);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		MutationAlgorithm mutationAlgorithmReturned = geneticStrategyBuilder.getMutationAlgorithm(parametersMap);

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
		ProbabilisticSelectionAlgorithm selectionAlgorithmToReturn = new ProbabilisticSelectionAlgorithm();

		parametersMap.put(selectionAlgorithmParameter, selectionAlgorithmToReturn);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		SelectionAlgorithm selectionAlgorithmReturned = geneticStrategyBuilder.getSelectionAlgorithm(parametersMap);

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
		TournamentSelector selectorMethodToReturn = new TournamentSelector();

		parametersMap.put(selectorMethodParameter, selectorMethodToReturn);

		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		Selector selectorMethodReturned = geneticStrategyBuilder.getSelector(parametersMap);

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

	@SuppressWarnings("rawtypes")
	@Test
	public void testBuildStrategy() {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		GeneticStrategyBuilder geneticStrategyBuilder = new GeneticStrategyBuilder();

		/*
		 * Set up all parameters
		 */
		String cipherNameParameter = ParameterConstants.CIPHER_NAME;
		String cipherNameValue = "zodiac408";
		parametersMap.put(cipherNameParameter, cipherNameValue);

		String fitnessEvaluatorParameter = ParameterConstants.FITNESS_EVALUATOR;
		FitnessEvaluator fitnessEvaluatorToReturn = new CipherKeyUniqueIndexedWordGraphFitnessEvaluator();
		parametersMap.put(fitnessEvaluatorParameter, fitnessEvaluatorToReturn);

		String crossoverAlgorithmParameter = ParameterConstants.CROSSOVER_ALGORITHM;
		CrossoverAlgorithm crossoverAlgorithmToReturn = new EqualOpportunityGeneCrossoverAlgorithm();
		parametersMap.put(crossoverAlgorithmParameter, crossoverAlgorithmToReturn);

		String mutationAlgorithmParameter = ParameterConstants.MUTATION_ALGORITHM;
		MutationAlgorithm mutationAlgorithmToReturn = new MultipleMutationAlgorithm();
		parametersMap.put(mutationAlgorithmParameter, mutationAlgorithmToReturn);

		String selectionAlgorithmParameter = ParameterConstants.SELECTION_ALGORITHM;
		ProbabilisticSelectionAlgorithm selectionAlgorithmToReturn = new ProbabilisticSelectionAlgorithm();
		parametersMap.put(selectionAlgorithmParameter, selectionAlgorithmToReturn);

		String selectorMethodParameter = ParameterConstants.SELECTOR_METHOD;
		TournamentSelector selectorToReturn = new TournamentSelector();
		parametersMap.put(selectorMethodParameter, selectorToReturn);

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

		CipherKeyKnownSolutionFitnessEvaluator knownSolutionfitnessEvalutorToSet = new CipherKeyKnownSolutionFitnessEvaluator();
		geneticStrategyBuilder.setKnownSolutionFitnessEvaluator(knownSolutionfitnessEvalutorToSet);

		/*
		 * Give it a whirl
		 */
		GeneticAlgorithmStrategy geneticAlgorithmStrategy = geneticStrategyBuilder.buildStrategy(parametersMap);

		/*
		 * Validate everything
		 */
		assertSame(cipher, geneticAlgorithmStrategy.getGeneticStructure());
		assertSame(fitnessEvaluatorToReturn, geneticAlgorithmStrategy.getFitnessEvaluator());
		assertSame(crossoverAlgorithmToReturn, geneticAlgorithmStrategy.getCrossoverAlgorithm());
		assertSame(mutationAlgorithmToReturn, geneticAlgorithmStrategy.getMutationAlgorithm());
		assertSame(selectionAlgorithmToReturn, geneticAlgorithmStrategy.getSelectionAlgorithm());
		assertSame(selectorToReturn, geneticAlgorithmStrategy.getSelector());
		assertSame(knownSolutionfitnessEvalutorToSet, geneticAlgorithmStrategy.getKnownSolutionFitnessEvaluator());
		assertSame(populationSizeValue, geneticAlgorithmStrategy.getPopulationSize());
		assertSame(lifespanValue, geneticAlgorithmStrategy.getLifespan());
		assertSame(numGenerationsValue, geneticAlgorithmStrategy.getMaxGenerations());
		assertSame(survivalRateValue, geneticAlgorithmStrategy.getSurvivalRate());
		assertSame(mutationRateValue, geneticAlgorithmStrategy.getMutationRate());
		assertSame(maxMutationsPerIndividualValue, geneticAlgorithmStrategy.getMaxMutationsPerIndividual());
		assertSame(crossoverRateValue, geneticAlgorithmStrategy.getCrossoverRate());
		assertSame(compareToKnownValue, geneticAlgorithmStrategy.getCompareToKnownSolution());

		/*
		 * Try again without comparing to known solution
		 */
		parametersMap.put(compareToKnownParameter, false);

		geneticAlgorithmStrategy = geneticStrategyBuilder.buildStrategy(parametersMap);

		assertSame(cipher, geneticAlgorithmStrategy.getGeneticStructure());
		assertSame(fitnessEvaluatorToReturn, geneticAlgorithmStrategy.getFitnessEvaluator());
		assertSame(crossoverAlgorithmToReturn, geneticAlgorithmStrategy.getCrossoverAlgorithm());
		assertSame(mutationAlgorithmToReturn, geneticAlgorithmStrategy.getMutationAlgorithm());
		assertSame(selectionAlgorithmToReturn, geneticAlgorithmStrategy.getSelectionAlgorithm());
		assertSame(selectorToReturn, geneticAlgorithmStrategy.getSelector());
		assertSame(knownSolutionfitnessEvalutorToSet, geneticAlgorithmStrategy.getKnownSolutionFitnessEvaluator());
		assertSame(populationSizeValue, geneticAlgorithmStrategy.getPopulationSize());
		assertSame(lifespanValue, geneticAlgorithmStrategy.getLifespan());
		assertSame(numGenerationsValue, geneticAlgorithmStrategy.getMaxGenerations());
		assertSame(survivalRateValue, geneticAlgorithmStrategy.getSurvivalRate());
		assertSame(mutationRateValue, geneticAlgorithmStrategy.getMutationRate());
		assertSame(maxMutationsPerIndividualValue, geneticAlgorithmStrategy.getMaxMutationsPerIndividual());
		assertSame(crossoverRateValue, geneticAlgorithmStrategy.getCrossoverRate());
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
