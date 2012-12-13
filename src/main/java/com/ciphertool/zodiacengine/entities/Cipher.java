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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cipher")
public class Cipher implements Serializable {
	private static final long serialVersionUID = 3417112220260206089L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "columns")
	private int columns;

	@Column(name = "rows")
	private int rows;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id.cipher", cascade = CascadeType.ALL)
	private List<Ciphertext> ciphertextCharacters = new ArrayList<Ciphertext>();

	public Cipher() {
	}

	public Cipher(Integer id) {
		this.id = id;
	}

	public Cipher(String name, int rows, int columns) {
		this.name = name;
		this.rows = rows;
		this.columns = columns;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * We must not use the Ciphertext characters else we may run into a stack
	 * overflow. It shouldn't be necessary anyway since the id makes the cipher
	 * unique.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columns;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rows;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * We must not check the Ciphertext characters else we may run into a stack
	 * overflow. It shouldn't be necessary anyway since the id makes the cipher
	 * unique.
	 */
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
		if (columns != other.columns) {
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
				+ ", ciphertextCharacters=" + ciphertextCharacters + "]";
	}
}
