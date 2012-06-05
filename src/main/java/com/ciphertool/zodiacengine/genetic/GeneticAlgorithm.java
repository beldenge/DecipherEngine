package com.ciphertool.zodiacengine.genetic;

public interface GeneticAlgorithm {
	/**
	 * @return
	 */
	public Chromosome<?> iterateUntilTermination();

	public void spawnInitialPopulation();

	/**
	 * @return
	 */
	public Chromosome<?> crossover();

	public void mutate();
}
