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

import com.ciphertool.genetics.annotations.Dirty;

public class Plaintext {

	protected Integer plaintextId;

	protected String value;

	protected boolean hasMatch;

	public Plaintext() {
	}

	public Plaintext(Integer plaintextId, String value) {
		this.plaintextId = plaintextId;
		this.value = value;
	}

	public Integer getPlaintextId() {
		return plaintextId;
	}

	public void setPlaintextId(Integer plaintextId) {
		this.plaintextId = plaintextId;
	}

	public String getValue() {
		return value;
	}

	@Dirty
	public void setValue(String value) {
		this.value = value;
	}

	public boolean getHasMatch() {
		return hasMatch;
	}

	public void setHasMatch(boolean hasMatch) {
		this.hasMatch = hasMatch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (hasMatch ? 1231 : 1237);
		result = prime * result + ((plaintextId == null) ? 0 : plaintextId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Plaintext)) {
			return false;
		}

		Plaintext other = (Plaintext) obj;

		/*
		 * We are not checking for null plaintextId because it should never
		 * happen and we want an exception to be thrown if so.
		 */
		if (!plaintextId.equals(other.plaintextId)) {
			return false;
		}

		/*
		 * We are not checking for null value because it should never happen and
		 * we want an exception to be thrown if so.
		 */
		if (!value.equals(other.value)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Plaintext [id=" + plaintextId + ", value=" + value + ", hasMatch=" + hasMatch + "]";
	}
}