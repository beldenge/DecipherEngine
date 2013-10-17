/**
 * Copyright 2013 George Belden
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
