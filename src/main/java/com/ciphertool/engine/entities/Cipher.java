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

package com.ciphertool.engine.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ciphers")
public class Cipher implements Serializable {
	private static final long serialVersionUID = 3417112220260206089L;

	@Id
	private BigInteger id;

	@Indexed(background = true)
	private String name;

	private int columns;

	private int rows;

	private boolean hasKnownSolution;

	private List<Ciphertext> ciphertextCharacters = new ArrayList<Ciphertext>();

	public Cipher() {
	}

	public Cipher(String name, int rows, int columns) {
		this.name = name;
		this.rows = rows;
		this.columns = columns;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public List<Ciphertext> getCiphertextCharacters() {
		return Collections.unmodifiableList(ciphertextCharacters);
	}

	public void addCiphertextCharacter(Ciphertext ciphertext) {
		this.ciphertextCharacters.add(ciphertext);
	}

	public void removeCiphertextCharacter(Ciphertext ciphertext) {
		this.ciphertextCharacters.remove(ciphertext);
	}

	public int length() {
		return rows * columns;
	}

	/**
	 * @return the hasKnownSolution
	 */
	public boolean hasKnownSolution() {
		return hasKnownSolution;
	}

	/**
	 * @param hasKnownSolution
	 *            the hasKnownSolution to set
	 */
	public void setHasKnownSolution(boolean hasKnownSolution) {
		this.hasKnownSolution = hasKnownSolution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ciphertextCharacters == null) ? 0 : ciphertextCharacters.hashCode());
		result = prime * result + columns;
		result = prime * result + (hasKnownSolution ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rows;
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
		Cipher other = (Cipher) obj;
		if (ciphertextCharacters == null) {
			if (other.ciphertextCharacters != null) {
				return false;
			}
		} else if (!ciphertextCharacters.equals(other.ciphertextCharacters)) {
			return false;
		}
		if (columns != other.columns) {
			return false;
		}
		if (hasKnownSolution != other.hasKnownSolution) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (rows != other.rows) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cipher [id=" + id + ", name=" + name + ", columns=" + columns + ", rows=" + rows
				+ ", hasKnownSolution=" + hasKnownSolution + ", ciphertextCharacters=" + ciphertextCharacters + "]";
	}
}
