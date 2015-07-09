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

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithmType;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithmType;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithm;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithmType;
import com.ciphertool.genetics.algorithms.selection.modes.Selector;
import com.ciphertool.genetics.algorithms.selection.modes.SelectorType;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.fitness.FitnessEvaluatorType;

public class GeneticStrategyBuilder implements StrategyBuilder, ApplicationContextAware {
	private Logger log = Logger.getLogger(getClass());

	private CipherDao cipherDao;
	private FitnessEvaluator knownSolutionFitnessEvaluator;

	private ApplicationContext context;

	@SuppressWarnings("rawtypes")
	@Override
	public GeneticAlgorithmStrategy buildStrategy(Map<String, Object> parameters) {
		Cipher cipher = getCipher(parameters);
		log.info("Cipher: " + cipher.getName());

		FitnessEvaluator fitnessEvaluator = getFitnessEvaluator(parameters);
		log.info("FitnessEvaluator implementation: " + fitnessEvaluator.getClass());

		CrossoverAlgorithm crossoverAlgorithm = getCrossoverAlgorithm(parameters);
		log.info("CrossoverAlgorithm implementation: " + crossoverAlgorithm.getClass());

		MutationAlgorithm mutationAlgorithm = getMutationAlgorithm(parameters);
		log.info("MutationAlgorithm implementation: " + mutationAlgorithm.getClass());

		SelectionAlgorithm selectionAlgorithm = getSelectionAlgorithm(parameters);
		log.info("SelectionAlgorithm implementation: " + selectionAlgorithm.getClass());

		Selector selector = getSelector(parameters);
		log.info("Selector implementation: " + selector.getClass());

		Integer populationSize = getPopulationSize(parameters);
		log.info("Population size: " + populationSize);

		Integer lifespan = getLifespan(parameters);
		log.info("Lifespan: " + lifespan);

		Integer numGenerations = getNumGenerations(parameters);
		log.info("Number of generations: " + numGenerations);

		Double survivalRate = getSurvivalRate(parameters);
		log.info("Survival rate: " + survivalRate);

		Double mutationRate = getMutationRate(parameters);
		log.info("Mutation rate: " + mutationRate);

		Integer maxMutationsPerIndividual = getMaxMutationsPerIndividual(parameters);
		log.info("Max mutations per individual: " + maxMutationsPerIndividual);

		Double crossoverRate = getCrossoverRate(parameters);
		log.info("Crossover rate: " + crossoverRate);

		Boolean mutateDuringCrossover = getMutateDuringCrossover(parameters);
		log.info("Mutate during crossover: " + mutateDuringCrossover);

		Boolean compareToKnownSolution = getCompareToKnown(parameters);
		log.info("Compare to known solution: " + compareToKnownSolution);

		if (compareToKnownSolution) {
			if (!cipher.hasKnownSolution()) {
				throw new IllegalStateException(
						"Cannot compare to known solution because this cipher does not have a known solution.  "
								+ cipher);
			}

			if (knownSolutionFitnessEvaluator == null) {
				throw new IllegalStateException(
						"Cannot compare to known solution because no respective FitnessEvaluator implementation has been set.  "
								+ cipher);
			}
		}

		return new GeneticAlgorithmStrategy(cipher, populationSize, lifespan, numGenerations, survivalRate,
				mutationRate, maxMutationsPerIndividual, crossoverRate, mutateDuringCrossover, fitnessEvaluator,
				crossoverAlgorithm, mutationAlgorithm, selectionAlgorithm, selector, knownSolutionFitnessEvaluator,
				compareToKnownSolution);
	}

	protected Cipher getCipher(Map<String, Object> parameters) {
		Object cipherName = parameters.get(ParameterConstants.CIPHER_NAME);

		if (cipherName == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.CIPHER_NAME + " cannot be null.");
		}

		if (!(cipherName instanceof String)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.CIPHER_NAME
					+ " must be of type String.");
		}

		Cipher cipher = cipherDao.findByCipherName((String) cipherName);

		if (cipher == null) {
			throw new IllegalStateException("Unable to find the cipher with name: " + (String) cipherName
					+ ".  Unable to build GeneticAlgorithmStrategy.");
		}

		return cipher;
	}

	protected FitnessEvaluator getFitnessEvaluator(Map<String, Object> parameters) {
		Object fitnessEvaluatorName = parameters.get(ParameterConstants.FITNESS_EVALUATOR);

		if (fitnessEvaluatorName == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.FITNESS_EVALUATOR
					+ " cannot be null.");
		}

		if (!(fitnessEvaluatorName instanceof String)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.FITNESS_EVALUATOR
					+ " must be of type String.");
		}

		FitnessEvaluator fitnessEvaluator = (FitnessEvaluator) context.getBean(FitnessEvaluatorType.valueOf(
				(String) fitnessEvaluatorName).getType());

		return fitnessEvaluator;
	}

	@SuppressWarnings("rawtypes")
	protected CrossoverAlgorithm getCrossoverAlgorithm(Map<String, Object> parameters) {
		Object crossoverAlgorithmName = parameters.get(ParameterConstants.CROSSOVER_ALGORITHM);

		if (crossoverAlgorithmName == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.CROSSOVER_ALGORITHM
					+ " cannot be null.");
		}

		if (!(crossoverAlgorithmName instanceof String)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.CROSSOVER_ALGORITHM
					+ " must be of type String.");
		}

		CrossoverAlgorithm crossoverAlgorithm = (CrossoverAlgorithm) context.getBean(CrossoverAlgorithmType.valueOf(
				(String) crossoverAlgorithmName).getType());

		return crossoverAlgorithm;
	}

	@SuppressWarnings("rawtypes")
	protected MutationAlgorithm getMutationAlgorithm(Map<String, Object> parameters) {
		Object mutationAlgorithmName = parameters.get(ParameterConstants.MUTATION_ALGORITHM);

		if (mutationAlgorithmName == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MUTATION_ALGORITHM
					+ " cannot be null.");
		}

		if (!(mutationAlgorithmName instanceof String)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MUTATION_ALGORITHM
					+ " must be of type String.");
		}

		MutationAlgorithm mutationAlgorithm = (MutationAlgorithm) context.getBean(MutationAlgorithmType.valueOf(
				(String) mutationAlgorithmName).getType());

		return mutationAlgorithm;
	}

	protected SelectionAlgorithm getSelectionAlgorithm(Map<String, Object> parameters) {
		Object selectionAlgorithmName = parameters.get(ParameterConstants.SELECTION_ALGORITHM);

		if (selectionAlgorithmName == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.SELECTION_ALGORITHM
					+ " cannot be null.");
		}

		if (!(selectionAlgorithmName instanceof String)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.SELECTION_ALGORITHM
					+ " must be of type String.");
		}

		SelectionAlgorithm selectionAlgorithm = (SelectionAlgorithm) context.getBean(SelectionAlgorithmType.valueOf(
				(String) selectionAlgorithmName).getType());

		return selectionAlgorithm;
	}

	protected Selector getSelector(Map<String, Object> parameters) {
		Object selectorName = parameters.get(ParameterConstants.SELECTOR_METHOD);

		if (selectorName == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.SELECTOR_METHOD
					+ " cannot be null.");
		}

		if (!(selectorName instanceof String)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.SELECTOR_METHOD
					+ " must be of type String.");
		}

		Selector selector = (Selector) context.getBean(SelectorType.valueOf((String) selectorName).getType());

		return selector;
	}

	protected Integer getPopulationSize(Map<String, Object> parameters) {
		Object populationSize = parameters.get(ParameterConstants.POPULATION_SIZE);

		if (populationSize == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.POPULATION_SIZE
					+ " cannot be null.");
		}

		if (!(populationSize instanceof Integer)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.POPULATION_SIZE
					+ " must be of type Integer.");
		}

		return (Integer) populationSize;
	}

	protected Integer getLifespan(Map<String, Object> parameters) {
		Object lifespan = parameters.get(ParameterConstants.LIFESPAN);

		if (lifespan == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.LIFESPAN + " cannot be null.");
		}

		if (!(lifespan instanceof Integer)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.LIFESPAN
					+ " must be of type Integer.");
		}

		return (Integer) lifespan;
	}

	protected Integer getNumGenerations(Map<String, Object> parameters) {
		Object numGenerations = parameters.get(ParameterConstants.NUMBER_OF_GENERATIONS);

		if (numGenerations == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.NUMBER_OF_GENERATIONS
					+ " cannot be null.");
		}

		if (!(numGenerations instanceof Integer)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.NUMBER_OF_GENERATIONS
					+ " must be of type Integer.");
		}

		return (Integer) numGenerations;
	}

	protected Double getSurvivalRate(Map<String, Object> parameters) {
		Object survivalRate = parameters.get(ParameterConstants.SURVIVAL_RATE);

		if (survivalRate == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.SURVIVAL_RATE + " cannot be null.");
		}

		if (!(survivalRate instanceof Double)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.SURVIVAL_RATE
					+ " must be of type Double.");
		}

		return (Double) survivalRate;
	}

	protected Double getMutationRate(Map<String, Object> parameters) {
		Object mutationRate = parameters.get(ParameterConstants.MUTATION_RATE);

		if (mutationRate == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MUTATION_RATE + " cannot be null.");
		}

		if (!(mutationRate instanceof Double)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MUTATION_RATE
					+ " must be of type Double.");
		}

		return (Double) mutationRate;
	}

	protected Integer getMaxMutationsPerIndividual(Map<String, Object> parameters) {
		Object maxMutationsPerIndividual = parameters.get(ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL);

		if (maxMutationsPerIndividual == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL
					+ " cannot be null.");
		}

		if (!(maxMutationsPerIndividual instanceof Integer)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MAX_MUTATIONS_PER_INDIVIDUAL
					+ " must be of type Integer.");
		}

		return (Integer) maxMutationsPerIndividual;
	}

	protected Double getCrossoverRate(Map<String, Object> parameters) {
		Object crossoverRate = parameters.get(ParameterConstants.CROSSOVER_RATE);

		if (crossoverRate == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.CROSSOVER_RATE
					+ " cannot be null.");
		}

		if (!(crossoverRate instanceof Double)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.CROSSOVER_RATE
					+ " must be of type Double.");
		}

		return (Double) crossoverRate;
	}

	protected Boolean getMutateDuringCrossover(Map<String, Object> parameters) {
		Object mutateDuringCrossover = parameters.get(ParameterConstants.MUTATE_DURING_CROSSOVER);

		if (mutateDuringCrossover == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MUTATE_DURING_CROSSOVER
					+ " cannot be null.");
		}

		if (!(mutateDuringCrossover instanceof Boolean)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.MUTATE_DURING_CROSSOVER
					+ " must be of type Boolean.");
		}

		return (Boolean) mutateDuringCrossover;
	}

	protected Boolean getCompareToKnown(Map<String, Object> parameters) {
		Object compareToKnown = parameters.get(ParameterConstants.COMPARE_TO_KNOWN_SOLUTION);

		if (compareToKnown == null) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.COMPARE_TO_KNOWN_SOLUTION
					+ " cannot be null.");
		}

		if (!(compareToKnown instanceof Boolean)) {
			throw new IllegalArgumentException("The parameter " + ParameterConstants.COMPARE_TO_KNOWN_SOLUTION
					+ " must be of type Boolean.");
		}

		return (Boolean) compareToKnown;
	}

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		this.cipherDao = cipherDao;
	}

	/**
	 * This is NOT required. We will not always know the solution. In fact, that should be the rare case.
	 * 
	 * @param knownSolutionFitnessEvaluator
	 *            the knownSolutionFitnessEvaluator to set
	 */
	public void setKnownSolutionFitnessEvaluator(FitnessEvaluator knownSolutionFitnessEvaluator) {
		this.knownSolutionFitnessEvaluator = knownSolutionFitnessEvaluator;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
