package com.ciphertool.zodiacengine.genetic.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
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
		if (this.genes == null) {
			this.genes = new ArrayList<Gene>();
		}

		if (this.plaintextCharacters == null) {
			this.plaintextCharacters = new ArrayList<Plaintext>();
		}

		if (gene == null) {
			log.warn("Attempted to insert a null Gene to SolutionChromosome.  Returning. " + this);

			return;
		}

		this.genes.add(gene);
		gene.setChromosome(this);

		int beginIndex = this.plaintextCharacters.size();

		PlaintextSequence plaintextSequence = null;
		for (int i = 0; i < gene.size(); i++) {
			plaintextSequence = (PlaintextSequence) gene.getSequences().get(i);

			this.plaintextCharacters.add(beginIndex + i, plaintextSequence);

			plaintextSequence.setHasMatch(false);

			plaintextSequence.getPlaintextId().setSolution(this);

			/*
			 * We additionally have to reset the ciphertextId since the current
			 * ciphertextIds may no longer be accurate.
			 */
			plaintextSequence.getPlaintextId().setCiphertextId(beginIndex + i);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#size()
	 */
	@Override
	public Integer actualSize() {
		Integer length = 0;

		if (this.genes != null) {
			for (Gene gene : this.genes) {
				length += gene.size();
			}
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
		copyChromosome.setPlaintextCharacters(new ArrayList<Plaintext>());

		Gene nextGene = null;
		for (Gene wordGene : this.genes) {
			nextGene = wordGene.clone();

			nextGene.setChromosome(copyChromosome);

			copyChromosome.addGene(nextGene);
		}

		return copyChromosome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#insertGene(int,
	 * com.ciphertool.zodiacengine.genetic.Gene)
	 */
	@Override
	public void insertGene(int index, Gene gene) {
		if (this.genes == null) {
			this.genes = new ArrayList<Gene>();
		}

		if (this.plaintextCharacters == null) {
			this.plaintextCharacters = new ArrayList<Plaintext>();
		}

		if (gene == null) {
			log.warn("Attempted to insert a null Gene to SolutionChromosome.  Returning. " + this);

			return;
		}

		gene.setChromosome(this);

		this.genes.add(index, gene);

		int beginIndex = 0;

		/*
		 * If this isn't the first Gene, we want the next Sequence ID to be one
		 * greater than the last Gene's greatest Sequence ID.
		 */
		if (index > 0) {
			beginIndex = ((PlaintextSequence) this.genes.get(index - 1).getSequences().get(
					this.genes.get(index - 1).size() - 1)).getPlaintextId().getCiphertextId() + 1;
		}

		int actualSize = this.plaintextCharacters.size();

		/*
		 * We additionally have to shift the ciphertextIds since the current
		 * ciphertextIds will no longer be accurate.
		 */
		for (int i = beginIndex; i < actualSize; i++) {
			((PlaintextSequence) this.plaintextCharacters.get(i)).shiftRight(gene.size());
		}

		PlaintextSequence plaintextSequence = null;
		for (int i = 0; i < gene.size(); i++) {
			plaintextSequence = (PlaintextSequence) gene.getSequences().get(i);

			this.plaintextCharacters.add(beginIndex + i, plaintextSequence);

			plaintextSequence.setHasMatch(false);

			plaintextSequence.getPlaintextId().setSolution(this);

			/*
			 * We additionally have to reset the ciphertextId since the current
			 * ciphertextIds may no longer be accurate.
			 */
			plaintextSequence.getPlaintextId().setCiphertextId(beginIndex + i);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#removeGene(int)
	 */
	@Override
	public Gene removeGene(int index) {
		if (this.genes == null) {
			log.warn("Attempted to remove a Gene from SolutionChromosome at index " + index
					+ ", but the List of Genes is null. " + this);

			return null;
		}

		Gene geneToRemove = this.genes.get(index);

		/*
		 * We want the next Sequence ID to be one greater than the current
		 * Gene's greatest Sequence ID.
		 */
		int beginIndex = ((PlaintextSequence) geneToRemove.getSequences().get(
				geneToRemove.getSequences().size() - 1)).getPlaintextId().getCiphertextId() + 1;

		int actualSize = this.plaintextCharacters.size();

		/*
		 * We additionally have to shift the ciphertextIds since the current
		 * ciphertextIds will no longer be accurate.
		 */
		for (int i = beginIndex; i < actualSize; i++) {
			((PlaintextSequence) this.plaintextCharacters.get(i)).shiftLeft(geneToRemove.size());
		}

		/*
		 * We loop across the indices backwards since, if starting from the
		 * beginning, they should decrement each time an element is removed.
		 */
		for (int i = geneToRemove.size() - 1; i >= 0; i--) {
			geneToRemove.removeSequence(geneToRemove.getSequences().get(i));
		}

		return this.genes.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#replaceGene(int,
	 * com.ciphertool.zodiacengine.genetic.Gene)
	 */
	@Override
	public void replaceGene(int index, Gene newGene) {
		this.removeGene(index);

		this.insertGene(index, newGene);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#mutateGene(int)
	 */
	@Override
	public void mutateGene(int index) {
		if (index > this.genes.size() - 1) {
			log.info("Attempted to mutate a Gene in SolutionChromosome with index of " + index
					+ " (zero-indexed), but the size is only " + this.genes.size()
					+ ".  Cannot continue.");

			return;
		}

		this.genes.get(index).mutateRandomSequence();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#mutateRandomGene()
	 */
	@Override
	public void mutateRandomGene() {
		int randomIndex = (int) (Math.random() * this.genes.size());

		this.mutateGene(randomIndex);
	}
}
