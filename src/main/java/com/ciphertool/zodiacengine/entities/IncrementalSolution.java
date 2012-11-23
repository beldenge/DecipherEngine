package com.ciphertool.zodiacengine.entities;

import javax.persistence.Transient;

public class IncrementalSolution extends Solution {
	private transient int committedIndex;
	private transient int uncommittedIndex;

	public IncrementalSolution(Cipher cipher, int totalMatches, int uniqueMatches,
			int adjacentMatches) {
		super(cipher, totalMatches, uniqueMatches, adjacentMatches);
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
}
