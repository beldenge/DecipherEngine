package com.ciphertool.zodiacengine.genetic;

public class MaximalCrossoverAlgorithm<T extends Gene> implements CrossoverAlgorithm<T> {
	private GeneListDao geneListDao;

	/**
	 * This crossover algorithm does a maximal amount of changes since it
	 * replaces genes regardless of their begin and end sequence positions
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.CrossoverAlgorithm#crossover(com.ciphertool.zodiacengine.genetic.Chromosome,
	 *      com.ciphertool.zodiacengine.genetic.Chromosome)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Chromosome<T> crossover(Chromosome<T> parentA, Chromosome<T> parentB) {
		Chromosome<T> child = (Chromosome<T>) parentA.clone();

		int childSequencePosition = 0;
		int childGeneIndex = 0;
		T geneCopy = null;
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
			childSequencePosition += child.getGenes().get(childGeneIndex).size();
			childGeneIndex++;

			geneCopy = child.getGenes().get(childGeneIndex);

			originalFitness = child.getFitness();

			child.getGenes()
					.set(childGeneIndex, (T) parentB.getGenes().get(childGeneIndex).clone());

			if (child.getFitness() <= originalFitness) {
				child.getGenes().set(childGeneIndex, geneCopy);
			}
		}

		/*
		 * Fill in the end of the Chromosome in case replacements caused the
		 * Chromosome to decrease in size
		 */
		while (childSequencePosition < parentB.size()) {
			T nextGene = (T) geneListDao.findRandomGene();

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
	public void setGeneListDao(GeneListDao geneListDao) {
		this.geneListDao = geneListDao;
	}
}
