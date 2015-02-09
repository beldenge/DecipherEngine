/**
 * Copyright 2015 George Belden
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

package com.ciphertool.zodiacengine.entities.cipherkey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
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

@Document(collection = "solutions")
public class CipherKeyChromosome implements Chromosome {
	
	private static Logger log = Logger.getLogger(CipherKeyChromosome.class);

	private static final int KEY_SIZE = 54;
	
	@Id
	protected BigInteger id;

	@Indexed
	protected Integer solutionSetId;

	protected BigInteger cipherId;
	
	private int rows;

	private int columns;
	
	@Transient
	protected boolean evaluationNeeded = true;

	@Transient
	private Double fitness = 0.0;

	@Transient
	private int age = 0;

	@Transient
	private int numberOfChildren = 0;

	private List<Gene> genes = new ArrayList<Gene>();
	
	public CipherKeyChromosome() {
	}

	/**
	 * @param cipherId
	 *            the cipherId to set
	 * @param rows
	 *            the rows to set
	 * @param columns
	 *            the columns to set
	 */
	public CipherKeyChromosome(BigInteger cipherId, int rows, int columns) {
		if (cipherId == null) {
			throw new IllegalArgumentException(
					"Cannot construct CipherKeyChromosome with null cipherId.");
		}

		this.cipherId = cipherId;

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
	 * Should only be used by unit tests
	 * 
	 * @return the rows
	 */
	protected int getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Should only be used by unit tests
	 * 
	 * @return the columns
	 */
	protected int getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 *            the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	/**
	 * @return the evaluationNeeded
	 */
	@Override
	public boolean isEvaluationNeeded() {
		return evaluationNeeded;
	}

	/**
	 * @param evaluationNeeded
	 *            the evaluationNeeded to set
	 */
	@Override
	public void setEvaluationNeeded(boolean evaluationNeeded) {
		this.evaluationNeeded = evaluationNeeded;
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

		gene.setChromosome(this);
		
		this.genes.add(gene);
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
	}

	@Override
	@Dirty
	public Gene removeGene(int index) {
		if (this.genes == null || this.genes.size() <= index) {
			log.warn("Attempted to remove a Gene from CipherKeyChromosome at index " + index
					+ ", but the List of Genes has max index of "
					+ (this.genes == null ? 0 : this.genes.size()) + ".  Returning null." + this);

			return null;
		}
		
		return this.genes.remove(index);
	}

	@Override
	@Dirty
	public void replaceGene(int index, Gene newGene) {
		if (newGene == null) {
			log.warn("Attempted to replace a Gene from CipherKeyChromosome, but the supplied Gene was null.  Cannot continue. "
					+ this);

			return;
		}

		if (this.genes == null || this.genes.size() <= index) {
			log.warn("Attempted to replace a Gene from CipherKeyChromosome at index " + index
					+ ", but the List of Genes has max index of "
					+ (this.genes == null ? 0 : this.genes.size()) + ".  Cannot continue." + this);

			return;
		}

		newGene.setChromosome(this);

		this.removeGene(index);

		this.insertGene(index, newGene);
	}

	@Override
	public Integer actualSize() {
		return this.genes.size();
	}

	@Override
	public Integer targetSize() {
		return KEY_SIZE;
	}

	@Override
	public Chromosome clone() {
		CipherKeyChromosome copyChromosome = new CipherKeyChromosome();

		copyChromosome.genes = new ArrayList<Gene>();
		copyChromosome.setId(null);
		copyChromosome.setAge(0);
		copyChromosome.setNumberOfChildren(0);
		copyChromosome.setSolutionSetId(this.solutionSetId);
		copyChromosome.setCipherId(this.cipherId);
		copyChromosome.setRows(this.rows);
		copyChromosome.setColumns(this.columns);
		copyChromosome.setEvaluationNeeded(this.evaluationNeeded);

		/*
		 * Since we are copying over the fitness value, we don't need to reset
		 * the evaluationNeeded flag because the cloned default is correct.
		 */
		copyChromosome.setFitness(this.fitness.doubleValue());
		
		/*
		 * We don't need to clone the solutionSetId or cipherId as even though
		 * they are objects, they should remain static.
		 */

		Gene nextGene = null;
		for (Gene cipherKeyGene : this.genes) {
			nextGene = cipherKeyGene.clone();

			copyChromosome.addGene(nextGene);
		}

		return copyChromosome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result
				+ ((cipherId == null) ? 0 : cipherId.hashCode());
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numberOfChildren;
		result = prime * result
				+ ((solutionSetId == null) ? 0 : solutionSetId.hashCode());
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
		if (!(obj instanceof CipherKeyChromosome)) {
			return false;
		}
		CipherKeyChromosome other = (CipherKeyChromosome) obj;
		if (age != other.age) {
			return false;
		}
		if (cipherId == null) {
			if (other.cipherId != null) {
				return false;
			}
		} else if (!cipherId.equals(other.cipherId)) {
			return false;
		}
		if (genes == null) {
			if (other.genes != null) {
				return false;
			}
		} else if (!genes.equals(other.genes)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (numberOfChildren != other.numberOfChildren) {
			return false;
		}
		if (solutionSetId == null) {
			if (other.solutionSetId != null) {
				return false;
			}
		} else if (!solutionSetId.equals(other.solutionSetId)) {
			return false;
		}
		return true;
	}
}
