package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionChromosome extends Solution implements Chromosome<WordGene> {

	private static Logger log = Logger.getLogger(SolutionChromosome.class);

	private static final long serialVersionUID = -8636317309324068652L;

	private List<WordGene> genes;
	private Integer fitness;
	private int cipherLength;

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
	public List<WordGene> getGenes() {
		if (this.genes == null) {
			this.genes = new ArrayList<WordGene>();
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
	public void setGenes(List<WordGene> genes) {
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
	public void addGene(WordGene gene) {
		this.genes.add(gene);
	}

	/**
	 * It is necessary to call this method before persisting a solution to
	 * database, since SolutionChromosome stores genes at the Word level, but
	 * the Solution entity stores them at the Plaintext character level
	 */
	public void convertWordsToPlaintext() {
		StringBuffer rawText = new StringBuffer();
		for (Word w : this.getGenes()) {
			rawText.append(w.getWordId().getWord());
		}
		char[] chars = new char[cipherLength];
		rawText.getChars(0, cipherLength, chars, 0);
		int id = 1;
		Plaintext pt;
		for (char c : chars) {
			pt = new Plaintext(new PlaintextId(this, id), String.valueOf(c));
			this.addPlaintext(pt);
			id++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#mutate()
	 */
	@Override
	public void mutate() {
		// TODO Auto-generated method stub
	}

	public String toString() {
		this.convertWordsToPlaintext();

		return super.toString();
	}

	/**
	 * @param cipherLength
	 *            the cipherLength to set
	 */
	public void setCipherLength(int cipherLength) {
		this.cipherLength = cipherLength;
	}

	@Override
	public Integer size() {
		return this.cipherLength;
	}

	@Override
	public SolutionChromosome clone() {
		SolutionChromosome copyChromosome = null;

		try {
			copyChromosome = (SolutionChromosome) super.clone();
		} catch (CloneNotSupportedException cnse) {
			log.error("Caught CloneNoteSupportedException while attempting to clone WordGene.",
					cnse);
		}

		copyChromosome.getGenes().clear();

		for (WordGene wordGene : this.genes) {
			copyChromosome.addGene(wordGene.clone());
		}

		return copyChromosome;
	}
}
