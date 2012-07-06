package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionChromosome extends Solution implements Chromosome {

	private static Logger log = Logger.getLogger(SolutionChromosome.class);

	private static final long serialVersionUID = -8636317309324068652L;

	private List<Gene> genes;
	private Integer fitness;

	public SolutionChromosome() {
		super();
	}

	/**
	 * @param cipherId
	 * @param totalMatches
	 * @param uniqueMatches
	 * @param adjacentMatches
	 */
	public SolutionChromosome(int cipherId, int totalMatches, int uniqueMatches, int adjacentMatches) {
		super(cipherId, totalMatches, uniqueMatches, adjacentMatches);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#getFitness()
	 */
	@Override
	@Transient
	public Integer getFitness() {
		return fitness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.Chromosome#setFitness(java.lang.Integer
	 * )
	 */
	@Override
	public void setFitness(Integer fitness) {
		this.fitness = fitness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#getGenes()
	 */
	@Override
	@Transient
	public List<Gene> getGenes() {
		if (this.genes == null) {
			this.genes = new ArrayList<Gene>();
		}
		return genes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.Chromosome#setGenes(java.util.List)
	 */
	@Override
	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.Chromosome#addGene(com.ciphertool
	 * .zodiacengine.genetic.Gene)
	 */
	@Override
	public void addGene(Gene gene) {
		this.genes.add(gene);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#mutateRandomGene()
	 */
	@Override
	public void mutateRandomGene() {
		int randomIndex = (int) (Math.random() * this.genes.size());

		this.genes.get(randomIndex).mutateRandomSequence();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.entities.Solution#toString()
	 */
	public String toString() {
		return super.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#size()
	 */
	@Override
	public Integer actualSize() {
		Integer length = 0;

		for (Gene gene : this.genes) {
			length += gene.size();
		}

		return length;
	}

	public Integer targetSize() {
		return this.cipher.length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SolutionChromosome clone() {
		SolutionChromosome copyChromosome = null;

		try {
			copyChromosome = (SolutionChromosome) super.clone();
		} catch (CloneNotSupportedException cnse) {
			log.error("Caught CloneNoteSupportedException while attempting to clone WordGene.",
					cnse);
		}

		copyChromosome.setGenes(new ArrayList<Gene>());

		for (Gene wordGene : this.genes) {
			copyChromosome.addGene(wordGene.clone());
		}

		copyChromosome.setPlaintextCharacters(new ArrayList<Plaintext>());

		/*
		 * Clone the bi-directional relationship between Plaintext and Solution
		 */
		for (Gene wordGene : copyChromosome.getGenes()) {
			for (Sequence plaintextSequence : wordGene.getSequences()) {
				((PlaintextSequence) plaintextSequence).getPlaintextId()
						.setSolution(copyChromosome);

				copyChromosome.addPlaintext((PlaintextSequence) plaintextSequence);
			}
		}

		return copyChromosome;
	}
}
