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

	/*
	 * Returns the size as the number of gene sequences
	 */
	public Integer actualSize();

	public Integer targetSize();

	public Chromosome clone();

	/*
	 * Mutates a random Gene
	 */
	public void mutateRandomGene();
}
