/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.entities.cipherkey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.annotations.Clean;
import com.ciphertool.genetics.annotations.Dirty;
import com.ciphertool.genetics.entities.Ancestry;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.KeyedChromosome;

@Document(collection = "solutions")
public class CipherKeyChromosome implements KeyedChromosome<String> {
	private static Logger		log					= LoggerFactory.getLogger(CipherKeyChromosome.class);

	@Transient
	private static final int	KEY_SIZE			= 54;

	/**
	 * This initial value is only used as a unique identifier for the Chromosome's ancestry, but a different generated
	 * ID is persisted to database.
	 */
	@Id
	protected String			id					= UUID.randomUUID().toString();

	@Indexed(background = true)
	protected Integer			solutionSetId;

	protected Cipher			cipher;

	@Transient
	private Ancestry			ancestry;

	@Transient
	protected boolean			evaluationNeeded	= true;

	@Transient
	private Double				fitness				= 0.0;

	@Transient
	private int					age					= 0;

	@Transient
	private int					numberOfChildren	= 0;

	private Map<String, Gene>	genes				= new HashMap<String, Gene>();

	@Transient
	private Population			population;

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
	public CipherKeyChromosome(Cipher cipher) {
		if (cipher == null) {
			throw new IllegalArgumentException("Cannot construct CipherKeyChromosome with null cipher.");
		}

		this.cipher = cipher;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Integer getSolutionSetId() {
		return solutionSetId;
	}

	@Override
	public void setSolutionSetId(Integer solutionSetId) {
		this.solutionSetId = solutionSetId;
	}

	/**
	 * @return the cipher
	 */
	public Cipher getCipher() {
		return this.cipher;
	}

	/**
	 * @param cipher
	 *            the cipher to set
	 */
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
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
	public Map<String, Gene> getGenes() {
		return Collections.unmodifiableMap(genes);
	}

	@Override
	@Dirty
	public void putGene(String key, Gene gene) {
		if (null == gene) {
			log.warn("Attempted to insert a null Gene to CipherKeyChromosome.  Returning. " + this);

			return;
		}

		if (this.genes.get(key) != null) {
			log.warn("Attempted to insert a Gene to CipherKeyChromosome with key " + key
					+ ", but the key already exists.  If this was intentional, please use replaceGene() instead.  Returning. "
					+ this);

			return;
		}

		gene.setChromosome(this);

		this.genes.put(key, gene);
	}

	@Override
	@Dirty
	public Gene removeGene(String key) {
		if (null == this.genes || null == this.genes.get(key)) {
			log.warn("Attempted to remove a Gene from CipherKeyChromosome with key " + key
					+ ", but this key does not exist.  Returning null.");

			return null;
		}

		return this.genes.remove(key);
	}

	/*
	 * This does the same thing as putGene(), and exists solely for semantic consistency.
	 */
	@Override
	@Dirty
	public void replaceGene(String key, Gene newGene) {
		if (null == newGene) {
			log.warn("Attempted to replace a Gene from CipherKeyChromosome, but the supplied Gene was null.  Cannot continue. "
					+ this);

			return;
		}

		if (null == this.genes || null == this.genes.get(key)) {
			log.warn("Attempted to replace a Gene from CipherKeyChromosome with key " + key
					+ ", but this key does not exist.  Cannot continue.");

			return;
		}

		newGene.setChromosome(this);

		this.genes.put(key, newGene);
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

		copyChromosome.genes = new HashMap<String, Gene>();
		copyChromosome.setAge(0);
		copyChromosome.setNumberOfChildren(0);
		copyChromosome.setSolutionSetId(this.solutionSetId);
		copyChromosome.setCipher(this.cipher);
		copyChromosome.setEvaluationNeeded(this.evaluationNeeded);

		/*
		 * Since we are copying over the fitness value, we don't need to reset the evaluationNeeded flag because the
		 * cloned default is correct.
		 */
		copyChromosome.setFitness(this.fitness.doubleValue());

		Gene nextGene = null;
		for (String key : this.genes.keySet()) {
			nextGene = this.genes.get(key).clone();

			copyChromosome.putGene(key, nextGene);
		}

		return copyChromosome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((cipher == null) ? 0 : cipher.hashCode());
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		result = prime * result + numberOfChildren;
		result = prime * result + ((solutionSetId == null) ? 0 : solutionSetId.hashCode());
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
		if (cipher == null) {
			if (other.cipher != null) {
				return false;
			}
		} else if (!cipher.getId().equals(other.cipher.getId())) {
			return false;
		}
		if (genes == null) {
			if (other.genes != null) {
				return false;
			}
		} else if (!genes.equals(other.genes)) {
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

	/*
	 * Prints the properties of the solution and then outputs the entire plaintext list in block format.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Solution [id=" + id + ", cipherId=" + cipher.getId() + ", fitness="
				+ String.format("%1$,.2f", fitness) + ", age=" + age + ", numberOfChildren=" + numberOfChildren
				+ ", evaluationNeeded=" + evaluationNeeded + "]\n");

		if (this.cipher != null) {
			CipherKeyGene nextPlaintext = null;
			int actualSize = this.cipher.getCiphertextCharacters().size();
			for (int i = 0; i < actualSize; i++) {

				nextPlaintext = (CipherKeyGene) this.genes.get(this.cipher.getCiphertextCharacters().get(i).getValue());

				if (nextPlaintext.hasMatch()) {
					sb.append("[");
					sb.append(nextPlaintext.getValue());
					sb.append("]");
				} else {
					sb.append(" ");
					sb.append(nextPlaintext.getValue());
					sb.append(" ");
				}

				/*
				 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function doesn't
				 * break.
				 */
				if (((i + 1) % this.cipher.getColumns()) == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}

		return sb.toString();
	}

	@Override
	public double similarityTo(Chromosome other) {
		int total = 0;

		for (Object key : this.genes.keySet()) {
			if (((CipherKeyGene) this.genes.get(key)).getValue().equals(((CipherKeyGene) ((CipherKeyChromosome) other).genes.get(key)).getValue())) {
				total++;
			}
		}

		return ((double) total) / ((double) this.genes.size());
	}

	/**
	 * @return the population
	 */
	@Override
	public Population getPopulation() {
		return population;
	}

	/**
	 * @param population
	 *            the population to set
	 */
	@Override
	public void setPopulation(Population population) {
		this.population = population;
	}

	@Override
	public Ancestry getAncestry() {
		return ancestry;
	}

	@Override
	public void setAncestry(Ancestry ancestry) {
		this.ancestry = ancestry;
	}
}
