package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
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
	 * Be careful to call this method only when necessary as it is expensive to
	 * repeatedly convert the words to plaintext.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.entities.Solution#getPlaintextCharacters()
	 */
	@Override
	public List<Plaintext> getPlaintextCharacters() {
		this.plaintextCharacters = new ArrayList<Plaintext>();

		convertWordsToPlaintext();

		return this.plaintextCharacters;
	}

	/**
	 * It is necessary to call this method before persisting a solution to
	 * database, since SolutionChromosome stores genes at the Word level, but
	 * the Solution entity stores them at the Plaintext character level
	 */
	public void convertWordsToPlaintext() {
		StringBuffer rawText = new StringBuffer();
		for (Gene w : this.getGenes()) {
			rawText.append(((WordGene) w).getWordId().getWord());
		}

		char[] chars = new char[this.cipher.length()];

		try {
			rawText.getChars(0, this.cipher.length(), chars, 0);
		} catch (StringIndexOutOfBoundsException sioobe) {
			log.error(
					"Caught StringIndexOutOfBoundsException while converting solution words to plaintext. Raw text length is "
							+ rawText.length(), sioobe);
		}

		int id = 1;
		Plaintext pt;
		for (char c : chars) {
			pt = new Plaintext(new PlaintextId(this, id), String.valueOf(c).toLowerCase());
			this.addPlaintext(pt);
			id++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#mutateRandomGene()
	 */
	@Override
	public void mutateRandomGene() {
		int randomIndex = (int) (Math.random() * this.genes.size());

		this.genes.get(randomIndex).mutateSequence();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.entities.Solution#toString()
	 */
	public String toString() {
		this.convertWordsToPlaintext();

		return super.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#size()
	 */
	@Override
	public Integer size() {
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

		copyChromosome.convertWordsToPlaintext();

		return copyChromosome;
	}
}
