/**
 * Copyright 2012 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.zodiacengine.genetic.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.entities.SolutionId;

public class SolutionChromosome extends Solution implements Chromosome {

	private static Logger log = Logger.getLogger(SolutionChromosome.class);

	private static final long serialVersionUID = -8636317309324068652L;

	private List<Gene> genes;
	private Double fitness;
	private int age = 0;

	public SolutionChromosome() {
		super();
	}

	/**
	 * @param cipherId
	 * @param totalMatches
	 * @param uniqueMatches
	 * @param adjacentMatches
	 */
	public SolutionChromosome(Cipher cipher, int totalMatches, int uniqueMatches,
			int adjacentMatches) {
		super(cipher, totalMatches, uniqueMatches, adjacentMatches);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#getFitness()
	 */
	@Override
	@Transient
	public Double getFitness() {
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
	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#getAge()
	 */
	@Override
	@Transient
	public int getAge() {
		return age;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#setAge(int)
	 */
	@Override
	public void setAge(int age) {
		this.age = age;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#increaseAge(int)
	 */
	@Override
	public void increaseAge() {
		this.age++;
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

			plaintextSequence.getId().setSolution(this);

			/*
			 * We additionally have to reset the ciphertextId since the current
			 * ciphertextIds may no longer be accurate.
			 */
			plaintextSequence.getId().setCiphertextId(beginIndex + i);
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
			log.error(
					"Caught CloneNoteSupportedException while attempting to clone SolutionChromosome.",
					cnse);
		}

		copyChromosome.setId(new SolutionId());
		copyChromosome.setGenes(new ArrayList<Gene>());
		copyChromosome.setPlaintextCharacters(new ArrayList<Plaintext>());
		copyChromosome.setAge(0);

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
					this.genes.get(index - 1).size() - 1)).getId().getCiphertextId() + 1;
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

			plaintextSequence.getId().setSolution(this);

			/*
			 * We additionally have to reset the ciphertextId since the current
			 * ciphertextIds may no longer be accurate.
			 */
			plaintextSequence.getId().setCiphertextId(beginIndex + i);
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
				geneToRemove.getSequences().size() - 1)).getId().getCiphertextId() + 1;

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
	 * Prints the properties of the solution and then outputs the entire
	 * plaintext list in block format.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Solution [id=" + id + ", cipherId=" + cipher.getId() + ", fitness="
				+ String.format("%1$,.2f", fitness) + ", age=" + age + ", totalMatches="
				+ totalMatches + ", unique matches=" + uniqueMatches + ", adjacent matches="
				+ adjacentMatchCount + "]\n");

		if (this.cipher != null) {
			Plaintext nextPlaintext = null;
			for (int i = 0; i < this.plaintextCharacters.size(); i++) {

				nextPlaintext = plaintextCharacters.get(i);

				// subtract 1 since the get method begins with 0
				if (nextPlaintext.getHasMatch()) {
					sb.append("[");
					sb.append(nextPlaintext.getValue());
					sb.append("]");
				} else {
					sb.append(" ");
					sb.append(nextPlaintext.getValue());
					sb.append(" ");
				}

				/*
				 * Print a newline if we are at the end of the row. Add 1 to the
				 * index so the modulus function doesn't break.
				 */
				if (((i + 1) % cipher.getColumns()) == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}

		return sb.toString();
	}
}
