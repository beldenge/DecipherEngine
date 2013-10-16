package com.ciphertool.zodiacengine.entities;

import javax.persistence.Transient;


public class IncrementalSolutionChromosome extends SolutionChromosome {

	@Transient
	private transient int committedIndex;

	@Transient
	private transient int uncommittedIndex;

	public IncrementalSolutionChromosome(Cipher cipher, int totalMatches, int uniqueMatches,
			int adjacentMatches) {
		this.cipherId = cipher != null ? cipher.getId() : null;
		this.totalMatches = totalMatches;
		this.uniqueMatches = uniqueMatches;
		this.adjacentMatches = adjacentMatches;
	}

	/**
	 * This is the permanent index used to keep track of how far an incremental
	 * solution has progressed
	 * 
	 * @return the committedIndex
	 */
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
