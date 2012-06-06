package com.ciphertool.zodiacengine.genetic;

import java.util.List;

public interface Chromosome extends Cloneable {
	/**
	 * @return
	 */
	public List<Gene> getGenes();

	/**
	 * @param genes
	 */
	public void setGenes(List<Gene> genes);

	/**
	 * @param gene
	 */
	public void addGene(Gene gene);

	/**
	 * @return
	 */
	public Integer getFitness();

	/**
	 * @param fitness
	 */
	public void setFitness(Integer fitness);

	public Integer size();

	public void mutate();

	public Chromosome clone();

	/*
	 * Mutates a random Gene
	 */
	public void mutateGene(Gene gene);
}
