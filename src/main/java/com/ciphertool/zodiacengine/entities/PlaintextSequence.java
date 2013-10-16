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

package com.ciphertool.zodiacengine.entities;

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Transient;

import com.ciphertool.genetics.annotations.Dirty;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;

public class PlaintextSequence extends Plaintext implements Sequence {
	private static Logger log = Logger.getLogger(WordGene.class);

	@Transient
	private Gene gene;

	public PlaintextSequence() {
	}

	public PlaintextSequence(Integer plaintextId, String value, Gene gene) {
		super(plaintextId, value);

		this.gene = gene;
	}

	@Override
	public Gene getGene() {
		return this.gene;
	}

	@Override
	public void setGene(Gene gene) {
		this.gene = gene;
	}

	@Override
	public PlaintextSequence clone() {
		PlaintextSequence copySequence = null;

		try {
			copySequence = (PlaintextSequence) super.clone();
		} catch (CloneNotSupportedException cnse) {
			log.error(
					"Caught CloneNotSupportedException while attempting to clone PlaintextSequence.",
					cnse);
		}
		copySequence.setHasMatch(false);

		copySequence.setPlaintextId(this.plaintextId.intValue());

		/*
		 * The Gene must bet set at a higher level.
		 */
		copySequence.gene = null;

		return copySequence;
	}

	/**
	 * Shifts all the plaintext characters to the right the specified number of
	 * places.
	 * 
	 * @param places
	 */
	@Dirty
	public void shiftRight(int places) {
		this.plaintextId = this.plaintextId + places;
	}

	/**
	 * Shifts all the plaintext characters to the left the specified number of
	 * places.
	 * 
	 * @param places
	 */
	@Dirty
	public void shiftLeft(int places) {
		this.plaintextId = this.plaintextId - places;
	}

	@Override
	public Integer getSequenceId() {
		return this.plaintextId;
	}

	/*
	 * This method actually overrides both the interface and the superclass.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.entities.Plaintext#getValue()
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	@Dirty
	public void setValue(Object obj) {
		this.value = (String) obj;
	}
}
