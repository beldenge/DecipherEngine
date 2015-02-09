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

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Transient;

import com.ciphertool.genetics.annotations.Dirty;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;

public class CipherKeyGene implements Gene {

	private static Logger log = Logger.getLogger(CipherKeyGene.class);
	
	@Transient
	private Chromosome chromosome;
	
	private String key;
	
	private String value;
	
	public CipherKeyGene() {
	}
	
	/**
	 * Full-args constructor
	 * 
	 * @param chromosome the Chromosome
	 * @param key the cipher key
	 * @param value the String value for this cipher key
	 */
	public CipherKeyGene(Chromosome chromosome, String key, String value) {
		if (key == null || key.isEmpty()) {
			log.error("Found null key in full-args constructor.  Unable to construct CipherKeyGene.");

			return;
		}
		
		this.chromosome = chromosome;
		this.key = key;
		this.value = value;
	}

	@Override
	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
	}

	@Override
	public Chromosome getChromosome() {
		return this.chromosome;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	@Dirty
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	@Dirty
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public Gene clone() {
		CipherKeyGene copyGene = new CipherKeyGene();

		/*
		 * The Chromosome should be set at a higher level, so we just set it to
		 * null which should be overwritten.
		 */
		copyGene.chromosome = null;
		
		return copyGene;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		
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
		if (!(obj instanceof CipherKeyGene)) {
			return false;
		}
		
		CipherKeyGene other = (CipherKeyGene) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "CipherKeyGene [key=" + key + ", value=" + value + "]";
	}
}
