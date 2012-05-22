package com.ciphertool.zodiacengine.util;

import com.ciphertool.zodiacengine.entities.Solution;

public interface SolutionMerger {

	/**
	 * @return
	 * 
	 *         Merges all solutions existent in the database and returns a
	 *         "best fit" solution based on plaintext similarities among
	 *         solutions.
	 */
	public Solution mergeSolutions();
}
