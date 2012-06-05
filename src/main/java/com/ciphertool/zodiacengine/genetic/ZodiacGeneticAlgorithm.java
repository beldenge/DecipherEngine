package com.ciphertool.zodiacengine.genetic;

import org.springframework.beans.factory.annotation.Required;

public class ZodiacGeneticAlgorithm implements GeneticAlgorithm {
	private Integer initialPopulationSize;
	private Double mutationRate;
	private Double crossoverRate;
	private Integer maxGenerations;
	private Population<WordGene> population;
	private int cipherLength = 408;
	private CrossoverAlgorithm crossoverAlgorithm;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.GeneticAlgorithm#iterateUntilTermination
	 * ()
	 */
	@Override
	public Chromosome<WordGene> iterateUntilTermination() {
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
	public Chromosome<WordGene> crossover() {
		Chromosome<WordGene> mom = null;
		Chromosome<WordGene> dad = null;
		Chromosome<WordGene> child1 = null;
		Chromosome<WordGene> child2 = null;

		for (int i = 0; i < crossoverRate; i++) {
			mom = this.population.spinRouletteWheel();

			dad = this.population.spinRouletteWheel();

			child1 = crossoverAlgorithm.crossover(mom, dad);

			child2 = crossoverAlgorithm.crossover(dad, mom);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.GeneticAlgorithm#mutate()
	 */
	@Override
	public void mutate() {

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
		this.population = new Population();

		this.population.populateIndividuals(initialPopulationSize);

		this.population.evaluateFitness();
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
}
