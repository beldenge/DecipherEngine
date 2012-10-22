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
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "solution")
@AssociationOverrides(@AssociationOverride(name = "solutionId.solutionSet", joinColumns = @JoinColumn(name = "solution_set_id")))
public class Solution implements Serializable {
	private static final long serialVersionUID = -1293349461638306782L;

	protected SolutionId solutionId;
	protected int cipherId;
	protected int totalMatches;
	protected int uniqueMatches;
	protected int adjacentMatchCount;
	protected transient List<Plaintext> plaintextCharacters;
	protected Cipher cipher;
	private int committedIndex;
	private int uncommittedIndex;

	public Solution() {
	}

	/*
	 * TODO: I think it would be better to use the Cipher rather than the
	 * cipherId in this constructor.
	 */
	public Solution(int cipherId, int totalMatches, int uniqueMatches, int adjacentMatches) {
		this.cipherId = cipherId;
		this.totalMatches = totalMatches;
		this.uniqueMatches = uniqueMatches;
		this.adjacentMatchCount = adjacentMatches;
		this.plaintextCharacters = new ArrayList<Plaintext>();
	}

	/**
	 * @return the solutionId
	 */
	@EmbeddedId
	public SolutionId getSolutionId() {
		return solutionId;
	}

	/**
	 * @param solutionId
	 *            the solutionId to set
	 */
	public void setSolutionId(SolutionId solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name = "cipher_id")
	public int getCipherId() {
		return cipherId;
	}

	public void setCipherId(int cipherId) {
		this.cipherId = cipherId;
	}

	/**
	 * @return
	 */
	@Column(name = "total_matches")
	public int getTotalMatches() {
		return totalMatches;
	}

	/**
	 * @param totalMatches
	 */
	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}

	/**
	 * @return the uniqueMatches
	 */
	@Column(name = "unique_matches")
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
	@Column(name = "adjacent_matches")
	public int getAdjacentMatchCount() {
		return adjacentMatchCount;
	}

	/**
	 * @param adjacentMatchCount
	 *            the adjacentMatchCount to set
	 */
	public void setAdjacentMatchCount(int adjacentMatchCount) {
		this.adjacentMatchCount = adjacentMatchCount;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "plaintextId.solution", cascade = CascadeType.ALL)
	public List<Plaintext> getPlaintextCharacters() {
		if (this.plaintextCharacters == null) {
			this.plaintextCharacters = new ArrayList<Plaintext>();
		}

		return this.plaintextCharacters;
	}

	public void setPlaintextCharacters(List<Plaintext> plaintextCharacters) {
		this.plaintextCharacters = plaintextCharacters;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cipher_id", insertable = false, updatable = false)
	public Cipher getCipher() {
		return cipher;
	}

	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	/**
	 * This is the permanent index used to keep track of how far an incremental
	 * solution has progressed
	 * 
	 * @return the committedIndex
	 */
	@Transient
	public int getCommittedIndex() {
		return committedIndex;
	}

	/**
	 * @param committedIndex
	 *            the committedIndex to set
	 */
	public void setCommittedIndex(int committedIndex) {
		this.committedIndex = committedIndex;
	}

	/**
	 * This is the temporary index used to keep track of how far an incremental
	 * solution has progressed
	 * 
	 * @return the uncommittedIndex
	 */
	@Transient
	public int getUncommittedIndex() {
		return uncommittedIndex;
	}

	/**
	 * @param uncommittedIndex
	 *            the uncommittedIndex to set
	 */
	public void setUncommittedIndex(int uncommittedIndex) {
		this.uncommittedIndex = uncommittedIndex;
	}

	public void addPlaintext(Plaintext plaintext) {
		if (this.plaintextCharacters == null) {
			this.plaintextCharacters = new ArrayList<Plaintext>();
		}

		plaintext.getPlaintextId().setSolution(this);

		this.plaintextCharacters.add(plaintext);
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
		result = prime * result + adjacentMatchCount;
		result = prime * result + cipherId;
		result = prime * result + solutionId.hashCode();
		result = prime * result + totalMatches;
		result = prime * result + uniqueMatches;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
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
		Solution other = (Solution) obj;
		if (adjacentMatchCount != other.adjacentMatchCount) {
			return false;
		}
		if (cipherId != other.cipherId) {
			return false;
		}
		if (!solutionId.equals(other.solutionId)) {
			return false;
		}
		if (totalMatches != other.totalMatches) {
			return false;
		}
		if (uniqueMatches != other.uniqueMatches) {
			return false;
		}

		if (this.plaintextCharacters == null && other.plaintextCharacters != null) {
			return false;
		}
		if (this.plaintextCharacters != null && other.plaintextCharacters == null) {
			return false;
		}
		if (this.plaintextCharacters != null && other.plaintextCharacters != null) {
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
		sb.append("Solution [id=" + solutionId + ", cipherId=" + cipherId + ", totalMatches="
				+ totalMatches + ", unique matches=" + uniqueMatches + ", adjacent matches="
				+ adjacentMatchCount + "]\n");

		/*
		 * Start at 1 instead of 0 so that the modulus function below isn't
		 * messed up.
		 */
		if (this.cipher != null) {
			for (int i = 1; i <= this.plaintextCharacters.size(); i++) {

				// subtract 1 since the get method begins with 0
				if (plaintextCharacters.get(i - 1).getHasMatch()) {
					sb.append("[");
					sb.append(plaintextCharacters.get(i - 1).getValue());
					sb.append("]");
				} else {
					sb.append(" ");
					sb.append(plaintextCharacters.get(i - 1).getValue());
					sb.append(" ");
				}

				// print a newline if we are at the end of the row
				if ((i % cipher.getColumns()) == 0) {
					sb.append("\n");
				} else {
					sb.append(" ");
				}
			}
		}

		return sb.toString();
	}
}
