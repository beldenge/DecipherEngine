package com.ciphertool.zodiacengine.genetic;

public interface CrossoverAlgorithm<T extends Gene> {

	/*
	 * Performs crossover to a single child by cloning parentA and then
	 * selectively replacing Genes from parentB if they increase the fitness of
	 * the Chromosome.
	 * 
	 * Crossover from parentB to parentA can be achieved simply by reversing the
	 * supplied arguments.
	 */
	public Chromosome<T> crossover(Chromosome<T> parentA, Chromosome<T> parentB);
}
