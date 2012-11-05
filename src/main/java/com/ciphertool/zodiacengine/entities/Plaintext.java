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

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "plaintext")
@AssociationOverrides(@AssociationOverride(name = "id.solution", joinColumns = {
		@JoinColumn(name = "solution_id", referencedColumnName = "solution_id"),
		@JoinColumn(name = "solution_set_id", referencedColumnName = "solution_set_id") }))
public class Plaintext {
	protected PlaintextId id;
	protected String value;
	protected boolean hasMatch;

	public Plaintext() {
	}

	public Plaintext(PlaintextId plaintextId, String value) {
		this.id = plaintextId;
		this.value = value;
	}

	@EmbeddedId
	public PlaintextId getId() {
		return id;
	}

	public void setId(PlaintextId id) {
		this.id = id;
	}

	@Column(name = "value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "has_match")
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!id.equals(other.id)) {
			return false;
		}

		if (hasMatch != other.hasMatch) {
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
		return "Plaintext [id=" + id + ", value=" + value + ", hasMatch=" + hasMatch + "]";
	}
}