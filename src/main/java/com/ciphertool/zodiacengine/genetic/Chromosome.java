package com.ciphertool.zodiacengine.genetic;

import java.util.List;

public interface Chromosome<T extends Gene> extends Cloneable {
	/**
	 * @return
	 */
	public List<T> getGenes();

	/**
	 * @param genes
	 */
	public void setGenes(List<T> genes);

	/**
	 * @param gene
	 */
	public void addGene(T gene);

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

	public Chromosome<T> clone();
}
