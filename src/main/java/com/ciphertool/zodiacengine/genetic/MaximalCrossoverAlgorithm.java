package com.ciphertool.zodiacengine.genetic;

import org.springframework.beans.factory.annotation.Required;

public class MaximalCrossoverAlgorithm implements CrossoverAlgorithm {
	private GeneListDao geneListDao;
	private FitnessEvaluator fitnessEvaluator;

	/**
	 * This crossover algorithm does a maximal amount of changes since it
	 * replaces genes regardless of their begin and end sequence positions
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.CrossoverAlgorithm#crossover(com.ciphertool.zodiacengine.genetic.Chromosome,
	 *      com.ciphertool.zodiacengine.genetic.Chromosome)
	 */
	@Override
	public Chromosome crossover(Chromosome parentA, Chromosome parentB) {
		Chromosome child = (Chromosome) parentA.clone();

		int childSequencePosition = 0;
		int childGeneIndex = 0;
		Gene geneCopy = null;
		Integer originalFitness = 0;

		/*
		 * Make sure we don't exceed parentB's index, or else we will get an
		 * IndexOutOfBoundsException
		 */
		while (childGeneIndex < child.getGenes().size()
				&& childGeneIndex < parentB.getGenes().size()) {
			/*
			 * Replace from parentB and reevaluate to see if it improves.
			 */
			geneCopy = child.getGenes().get(childGeneIndex);

			originalFitness = child.getFitness();

			child.getGenes().set(childGeneIndex, parentB.getGenes().get(childGeneIndex).clone());

			fitnessEvaluator.evaluate(child);

			/*
			 * Revert to the original gene if this did not increase fitness
			 */
			if (child.getFitness() <= originalFitness) {
				child.getGenes().set(childGeneIndex, geneCopy);
			}

			childSequencePosition += child.getGenes().get(childGeneIndex).size();
			childGeneIndex++;
		}

		/*
		 * Fill in the end of the Chromosome in case replacements caused the
		 * Chromosome to decrease in size
		 */
		while (childSequencePosition < parentB.size()) {
			Gene nextGene = geneListDao.findRandomGene();

			childSequencePosition += nextGene.size();

			child.addGene(nextGene);
		}

		/*
		 * Child is guaranteed to have at least as good fitness as its parent
		 */
		return child;
	}

	/**
	 * @param geneListDao
	 *            the geneListDao to set
	 */
	@Required
	public void setGeneListDao(GeneListDao geneListDao) {
		this.geneListDao = geneListDao;
	}

	/**
	 * @param fitnessEvaluator
	 *            the fitnessEvaluator to set
	 */
	@Required
	public void setFitnessEvaluator(FitnessEvaluator fitnessEvaluator) {
		this.fitnessEvaluator = fitnessEvaluator;
	}
}
