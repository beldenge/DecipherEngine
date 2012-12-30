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
import java.util.Collections;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.annotations.Clean;
import com.ciphertool.genetics.annotations.Dirty;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.entities.SolutionId;

public class SolutionChromosome extends Solution implements Chromosome {

	private static Logger log = Logger.getLogger(SolutionChromosome.class);

	private static final long serialVersionUID = -8636317309324068652L;

	@Transient
	private List<Gene> genes = new ArrayList<Gene>();

	@Transient
	private Double fitness = 0.0;

	@Transient
	private int age = 0;

	@Transient
	private int numberOfChildren = 0;

	public SolutionChromosome() {
		super();
	}

	/**
	 * @param cipherId
	 *            the cipherId to set
	 * @param totalMatches
	 *            the totalMatches to set
	 * @param uniqueMatches
	 *            the uniqueMatches to set
	 * @param adjacentMatches
	 *            the adjacentMatches to set
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
	@Clean
	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#getAge()
	 */
	@Override
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
	 * @see com.ciphertool.genetics.entities.Chromosome#getNumberOfChildren()
	 */
	@Override
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#setNumberOfChildren(int)
	 */
	@Override
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.genetics.entities.Chromosome#increaseNumberOfChildren()
	 */
	@Override
	public void increaseNumberOfChildren() {
		this.numberOfChildren++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Chromosome#getGenes()
	 */
	@Override
	public List<Gene> getGenes() {
		return Collections.unmodifiableList(genes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.genetic.Chromosome#addGene(com.ciphertool
	 * .zodiacengine.genetic.Gene)
	 */
	@Override
	@Dirty
	public void addGene(Gene gene) {
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
		return this.plaintextCharacters.size();
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
					"Caught CloneNotSupportedException while attempting to clone SolutionChromosome.",
					cnse);
		}

		copyChromosome.setId(new SolutionId());
		copyChromosome.resetGenes();
		copyChromosome.resetPlaintextCharacters();
		copyChromosome.setAge(0);
		copyChromosome.setNumberOfChildren(0);
		copyChromosome.needsEvaluation = true;

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
	@Dirty
	public void insertGene(int index, Gene gene) {
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
	@Dirty
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
			plaintextCharacters.remove(geneToRemove.getSequences().get(i).getSequenceId()
					.intValue());
		}

		geneToRemove.resetSequences();

		return this.genes.remove(index);
	}

	/*
	 * This should just be a combination of the remove and insert methods.
	 * 
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
	 * @see com.ciphertool.genetics.entities.Chromosome#resetGenes()
	 */
	@Override
	@Dirty
	public void resetGenes() {
		this.genes = new ArrayList<Gene>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return needsEvaluation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Chromosome#setDirty(boolean)
	 */
	@Override
	public void setDirty(boolean isDirty) {
		this.needsEvaluation = isDirty;
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
				+ String.format("%1$,.2f", fitness) + ", age=" + age + ", numberOfChildren="
				+ numberOfChildren + ", totalMatches=" + totalMatches + ", unique matches="
				+ uniqueMatches + ", adjacent matches=" + adjacentMatchCount + "]\n");

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
