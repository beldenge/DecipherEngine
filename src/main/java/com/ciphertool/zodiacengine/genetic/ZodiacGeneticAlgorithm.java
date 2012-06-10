package com.ciphertool.zodiacengine.genetic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

public class ZodiacGeneticAlgorithm implements GeneticAlgorithm {
	private Logger log = Logger.getLogger(getClass());
	private Integer initialPopulationSize;
	private Double selectionRate;
	private Double mutationRate;
	private Double crossoverRate;
	private Integer maxGenerations;
	private Population population;
	private CrossoverAlgorithm crossoverAlgorithm;
	private FitnessEvaluator fitnessEvaluator;

	public ZodiacGeneticAlgorithm() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.GeneticAlgorithm#iterateUntilTermination
	 * ()
	 */
	@Override
	public Chromosome iterateUntilTermination() {
		for (int i = 0; i < maxGenerations; i++) {
			population.evaluateFitness();
		}

		return population.getBestFitIndividual();
	}

	/*
	 * Roulette Wheel Selection
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.GeneticAlgorithm#crossover()
	 */
	@Override
	public void crossover() {
		Chromosome mom = null;
		Chromosome dad = null;
		Chromosome child1 = null;
		Chromosome child2 = null;

		for (int i = 0; i < crossoverRate; i++) {
			mom = this.population.spinRouletteWheel();

			dad = this.population.spinRouletteWheel();

			child1 = crossoverAlgorithm.crossover(mom, dad);

			child2 = crossoverAlgorithm.crossover(dad, mom);
		}

		/*
		 * Remove the parents from the populationa and add the children since
		 * they are guaranteed to be at least as fit as their parents
		 */
		this.population.removeIndividual(mom);
		this.population.removeIndividual(dad);

		this.population.addIndividual(child1);
		this.population.addIndividual(child2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.GeneticAlgorithm#mutate()
	 */
	@Override
	public void mutate() {
		for (int i = 0; i < mutationRate; i++) {
			/*
			 * Mutate a gene within a Chromosome
			 */
			Chromosome original = this.population.spinRouletteWheel();

			Chromosome mutation = original.clone();

			mutation.mutateRandomGene();

			fitnessEvaluator.evaluate(mutation);

			if (mutation.getFitness() > original.getFitness()) {
				this.population.removeIndividual(original);
				this.population.addIndividual(mutation);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.GeneticAlgorithm#spawnInitialPopulation
	 * ()
	 */
	@Override
	public void spawnInitialPopulation() {
		this.population.populateIndividuals(initialPopulationSize);

		this.population.evaluateFitness();
	}

	/**
	 * @return the population
	 */
	public Population getPopulation() {
		return population;
	}

	/**
	 * @param population
	 *            the population to set
	 */
	@Required
	public void setPopulation(Population population) {
		this.population = population;
	}

	/**
	 * @param initialPopulationSize
	 */
	@Required
	public void setInitialPopulationSize(Integer initialPopulationSize) {
		this.initialPopulationSize = initialPopulationSize;
	}

	/**
	 * @param mutationRate
	 *            the mutationRate to set
	 */
	@Required
	public void setMutationRate(Double mutationRate) {
		this.mutationRate = mutationRate;
	}

	/**
	 * @param crossoverRate
	 *            the crossoverRate to set
	 */
	@Required
	public void setCrossoverRate(Double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/**
	 * @param maxGenerations
	 *            the maxGenerations to set
	 */
	@Required
	public void setMaxGenerations(Integer maxGenerations) {
		this.maxGenerations = maxGenerations;
	}

	/**
	 * @param crossoverAlgorithm
	 *            the crossoverAlgorithm to set
	 */
	@Required
	public void setCrossoverAlgorithm(CrossoverAlgorithm crossoverAlgorithm) {
		this.crossoverAlgorithm = crossoverAlgorithm;
	}

	/**
	 * @param fitnessEvaluator
	 *            the fitnessEvaluator to set
	 */
	@Required
	public void setFitnessEvaluator(FitnessEvaluator fitnessEvaluator) {
		this.fitnessEvaluator = fitnessEvaluator;
	}

	/**
	 * @param selectionRate
	 *            the selectionRate to set
	 */
	@Required
	public void setSelectionRate(Double selectionRate) {
		this.selectionRate = selectionRate;
	}
}
