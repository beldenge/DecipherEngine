package com.ciphertool.zodiacengine.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "solution")
public class Solution implements Serializable {
	private static final long serialVersionUID = -1293349461638306782L;

	private int id;
	private int cipherId;
	private int totalMatches;
	private int uniqueMatches;
	private int adjacentMatchCount;
	private transient List<Plaintext> plaintextCharacters;
	private Cipher cipher;
	private int committedIndex;
	private int uncommittedIndex;

	public Solution() {
	}

	public Solution(int cipherId, int totalMatches, int uniqueMatches, int adjacentMatches) {
		this.cipherId = cipherId;
		this.totalMatches = totalMatches;
		this.uniqueMatches = uniqueMatches;
		this.adjacentMatchCount = adjacentMatches;
		this.plaintextCharacters = new ArrayList<Plaintext>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return plaintextCharacters;
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
		this.plaintextCharacters.add(plaintext);
		plaintext.getPlaintextId().setSolution(this);
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
		result = prime * result + id;
		result = prime * result + totalMatches;
		result = prime * result + uniqueMatches;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * We must not check the Plaintext characters else we may run into a stack
	 * overflow. It shouldn't be necessary anyway since the id makes the
	 * solution unique.
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
		if (id != other.id) {
			return false;
		}
		if (totalMatches != other.totalMatches) {
			return false;
		}
		if (uniqueMatches != other.uniqueMatches) {
			return false;
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
		sb.append("Solution [id=" + ((id == 0) ? "NOT_SET" : id) + ", cipherId=" + cipherId
				+ ", totalMatches=" + totalMatches + "" + ", unique matches=" + uniqueMatches
				+ ", adjacent matches=" + adjacentMatchCount + "]\n");

		// start at 1 instead of 0 so that the modulus function below isn't
		// messed up
		for (int i = 1; i <= cipher.length(); i++) {

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

		return sb.toString();
	}

}
