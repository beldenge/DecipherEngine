package com.ciphertool.zodiacengine.genetic;

public class MinimalCrossoverAlgorithm<T extends Gene> implements CrossoverAlgorithm<T> {
	/**
	 * This crossover algorithm does a minimal amount of changes since it only
	 * replaces genes that begin and end at the exact same sequence positions
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.CrossoverAlgorithm#crossover(com.ciphertool.zodiacengine.genetic.Chromosome,
	 *      com.ciphertool.zodiacengine.genetic.Chromosome)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Chromosome<T> crossover(Chromosome<T> parentA, Chromosome<T> parentB) {
		Chromosome<T> child = (Chromosome<T>) parentA.clone();

		int childSequencePosition = 0;
		int parentSequencePosition = 0;
		int childGeneIndex = 0;
		int parentGeneIndex = 0;
		T geneCopy = null;
		Integer originalFitness = 0;

		/*
		 * Make sure we don't exceed parentB's index, or else we will get an
		 * IndexOutOfBoundsException
		 */
		while (childGeneIndex < child.getGenes().size()
				&& childGeneIndex < parentB.getGenes().size()) {
			/*
			 * Replace from parentB and reevaluate to see if it improves. We are
			 * extra careful here since genes won't match exactly with sequence
			 * position.
			 */
			if (childSequencePosition == parentSequencePosition) {
				childSequencePosition += child.getGenes().get(childGeneIndex).size();
				parentSequencePosition += parentB.getGenes().get(parentGeneIndex).size();

				childGeneIndex++;
				parentGeneIndex++;

				if (child.getGenes().get(childGeneIndex).size() == parentB.getGenes().get(
						parentGeneIndex).size()) {
					geneCopy = child.getGenes().get(childGeneIndex);

					originalFitness = child.getFitness();

					child.getGenes().set(childGeneIndex,
							(T) parentB.getGenes().get(parentGeneIndex).clone());

					if (child.getFitness() <= originalFitness) {
						child.getGenes().set(childGeneIndex, geneCopy);
					}
				}
			} else if (childSequencePosition > parentSequencePosition) {
				parentSequencePosition += parentB.getGenes().get(parentGeneIndex).size();
				parentGeneIndex++;
			} else {
				childSequencePosition += child.getGenes().get(childGeneIndex).size();
				childGeneIndex++;
			}
		}

		/*
		 * Child is guaranteed to have at least as good fitness as its parent
		 */
		return child;
	}
}
