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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ciphertool.genetics.annotations.Clean;
import com.ciphertool.genetics.annotations.Dirty;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.genetic.iterators.PlaintextIterator;

@Document(collection = "solutions")
public class SolutionChromosome implements Chromosome, Iterable<PlaintextSequence> {

	private static Logger log = Logger.getLogger(SolutionChromosome.class);

	@Id
	protected BigInteger id;

	@Indexed
	protected Integer solutionSetId;

	protected BigInteger cipherId;

	private Date createdDate;

	private List<Gene> genes = new ArrayList<Gene>();

	@Transient
	protected List<Plaintext> plaintextCharacters = new ArrayList<Plaintext>();

	private int rows;

	private int columns;

	protected int totalMatches;

	protected int uniqueMatches;

	protected int adjacentMatches;

	@Transient
	protected boolean needsEvaluation = true;

	@Transient
	private Double fitness = 0.0;

	@Transient
	private int age = 0;

	@Transient
	private int numberOfChildren = 0;

	public SolutionChromosome() {
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
	 * @param rows
	 *            the rows to set
	 * @param columns
	 *            the columns to set
	 */
	public SolutionChromosome(BigInteger cipherId, int totalMatches, int uniqueMatches,
			int adjacentMatches, int rows, int columns) {
		this.cipherId = cipherId;
		this.totalMatches = totalMatches;
		this.uniqueMatches = uniqueMatches;
		this.adjacentMatches = adjacentMatches;

		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the solutionSetId
	 */
	public Integer getSolutionSetId() {
		return solutionSetId;
	}

	/**
	 * @param solutionSetId
	 *            the solutionSetId to set
	 */
	public void setSolutionSetId(Integer solutionSetId) {
		this.solutionSetId = solutionSetId;
	}

	/**
	 * @return the totalMatches
	 */
	public int getTotalMatches() {
		return totalMatches;
	}

	/**
	 * @param totalMatches
	 *            the totalMatches to set
	 */
	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}

	/**
	 * @return the uniqueMatches
	 */
	public int getUniqueMatches() {
		return uniqueMatches;
	}

	/**
	 * @param uniqueMatches
	 *            the uniqueMatches to set
	 */
	public void setUniqueMatches(int uniqueMatches) {
		this.uniqueMatches = uniqueMatches;
	}

	/**
	 * @return the adjacentMatchCount
	 */
	public int getAdjacentMatchCount() {
		return adjacentMatches;
	}

	/**
	 * @param adjacentMatchCount
	 *            the adjacentMatchCount to set
	 */
	public void setAdjacentMatchCount(int adjacentMatchCount) {
		this.adjacentMatches = adjacentMatchCount;
	}

	/**
	 * @return the cipherId
	 */
	public BigInteger getCipherId() {
		return cipherId;
	}

	/**
	 * @param cipherId
	 *            the cipherId to set
	 */
	public void setCipherId(BigInteger cipherId) {
		this.cipherId = cipherId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * This should only be called for purposes of hydrating the entity. The
	 * createdDate should never be modified.
	 * 
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the needsEvaluation
	 */
	public boolean isNeedsEvaluation() {
		return needsEvaluation;
	}

	/**
	 * @param needsEvaluation
	 *            the needsEvaluation to set
	 */
	public void setNeedsEvaluation(boolean needsEvaluation) {
		this.needsEvaluation = needsEvaluation;
	}

	@Override
	public Double getFitness() {
		return fitness;
	}

	@Override
	@Clean
	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public void increaseAge() {
		this.age++;
	}

	@Override
	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	@Override
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	@Override
	public void increaseNumberOfChildren() {
		this.numberOfChildren++;
	}

	@Override
	public List<Gene> getGenes() {
		return Collections.unmodifiableList(genes);
	}

	@Override
	@Dirty
	public void addGene(Gene gene) {
		if (gene == null) {
			log.warn("Attempted to insert a null Gene to SolutionChromosome.  Returning. " + this);

			return;
		}

		int beginIndex = this.getPlaintextCharacters().size();

		this.genes.add(gene);
		gene.setChromosome(this);

		PlaintextSequence plaintextSequence = null;
		for (int i = 0; i < gene.size(); i++) {
			plaintextSequence = (PlaintextSequence) gene.getSequences().get(i);

			this.plaintextCharacters.add(beginIndex + i, plaintextSequence);

			plaintextSequence.setHasMatch(false);

			/*
			 * We additionally have to reset the ciphertextId since the current
			 * ciphertextIds may no longer be accurate.
			 */
			plaintextSequence.setPlaintextId(beginIndex + i);
		}
	}

	@Override
	public Integer actualSize() {
		return this.plaintextCharacters.size();
	}

	public Integer targetSize() {
		return this.rows * this.columns;
	}

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

		copyChromosome.setId(null);
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
					this.genes.get(index - 1).size() - 1)).getPlaintextId() + 1;
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

			/*
			 * We additionally have to reset the ciphertextId since the current
			 * ciphertextIds may no longer be accurate.
			 */
			plaintextSequence.setPlaintextId(beginIndex + i);
		}
	}

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
				geneToRemove.getSequences().size() - 1)).getPlaintextId() + 1;

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

	public List<Plaintext> getPlaintextCharacters() {
		return Collections.unmodifiableList(this.plaintextCharacters);
	}

	@Dirty
	public void addPlaintext(Plaintext plaintext) {
		this.plaintextCharacters.add(plaintext);
	}

	@Dirty
	public void insertPlaintext(int index, Plaintext plaintext) {
		this.plaintextCharacters.add(index, plaintext);
	}

	@Dirty
	public void removePlaintext(Plaintext plaintext) {
		this.plaintextCharacters.remove(plaintext);
	}

	@Dirty
	public void resetPlaintextCharacters() {
		this.plaintextCharacters = new ArrayList<Plaintext>();
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

	@Override
	@Dirty
	public void resetGenes() {
		this.genes = new ArrayList<Gene>();
	}

	@Override
	public boolean isDirty() {
		return needsEvaluation;
	}

	@Override
	public void setDirty(boolean isDirty) {
		this.needsEvaluation = isDirty;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * We must not use the Plaintext characters else we may run into a stack
	 * overflow. It shouldn't be necessary anyway since the id makes the
	 * solution unique.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adjacentMatches;
		result = prime * result + ((cipherId == null) ? 0 : cipherId.hashCode());
		result = prime * result + id.hashCode();
		result = prime * result + solutionSetId.hashCode();
		result = prime * result + totalMatches;
		result = prime * result + uniqueMatches;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SolutionChromosome other = (SolutionChromosome) obj;

		if (cipherId == null) {
			if (other.cipherId != null) {
				return false;
			}
		} else if (!cipherId.equals(other.cipherId)) {
			return false;
		}

		if (solutionSetId == null) {
			if (other.solutionSetId != null) {
				return false;
			}
		} else if (!solutionSetId.equals(other.solutionSetId)) {
			return false;
		}

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (this.plaintextCharacters == null) {
			if (other.plaintextCharacters != null) {
				return false;
			}
		} else if (other.plaintextCharacters == null) {
			return false;
		} else {
			if (this.plaintextCharacters.size() != other.plaintextCharacters.size()) {
				return false;
			}

			for (int i = 0; i < this.plaintextCharacters.size(); i++) {
				if (!this.plaintextCharacters.get(i).equals(other.plaintextCharacters.get(i))) {
					return false;
				}
			}
		}

		return true;
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
		sb.append("Solution [id=" + id + ", cipherId=" + cipherId + ", fitness="
				+ String.format("%1$,.2f", fitness) + ", age=" + age + ", numberOfChildren="
				+ numberOfChildren + ", totalMatches=" + totalMatches + ", uniqueMatches="
				+ uniqueMatches + ", adjacentMatches=" + adjacentMatches + "]\n");

		if (this.cipherId != null) {
			Plaintext nextPlaintext = null;
			int actualSize = this.plaintextCharacters.size();
			for (int i = 0; i < actualSize; i++) {

				nextPlaintext = this.plaintextCharacters.get(i);

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
				if (((i + 1) % this.columns) == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}

		return sb.toString();
	}

	@Override
	public Iterator<PlaintextSequence> iterator() {
		return new PlaintextIterator(this.genes);
	}
}
