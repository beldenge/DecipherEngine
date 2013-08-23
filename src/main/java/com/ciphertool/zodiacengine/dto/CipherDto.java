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

package com.ciphertool.zodiacengine.dto;

import java.math.BigInteger;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class CipherDto {
	private long totalMatchSum;
	private long numSolutions;
	private long uniqueMatchSum;
	private long adjacentMatchSum;
	private SolutionChromosome solutionMostMatches;
	private SolutionChromosome solutionMostUnique;
	private SolutionChromosome solutionMostAdjacent;
	private String threadName;

	/**
	 * No-args constructor
	 */
	public CipherDto(String threadName, Cipher cipher) {
		this.threadName = threadName;

		this.totalMatchSum = 0;
		this.numSolutions = 0;
		this.uniqueMatchSum = 0;
		this.adjacentMatchSum = 0;

		/*
		 * We set "dummy" Solutions with the lowest possible confidence values
		 */
		BigInteger cipherId = cipher.getId();
		int rows = cipher.getRows();
		int columns = cipher.getColumns();
		this.solutionMostMatches = new SolutionChromosome(cipherId, 0, 0, 0, rows, columns);
		this.solutionMostUnique = new SolutionChromosome(cipherId, 0, 0, 0, rows, columns);
		this.solutionMostAdjacent = new SolutionChromosome(cipherId, 0, 0, 0, rows, columns);
	}

	/**
	 * Computes the average total matches of all solutions tried up to this
	 * point
	 * 
	 * @return
	 */
	public long getAverage() {
		return (this.totalMatchSum / this.numSolutions);
	}

	/**
	 * @param totalMatches
	 */
	public void addToSum(long totalMatches) {
		this.totalMatchSum += totalMatches;
	}

	/**
	 * @param uniqueMatches
	 */
	public void addToUniqueSum(long uniqueMatches) {
		this.uniqueMatchSum += uniqueMatches;
	}

	/**
	 * @param adjacentMatches
	 */
	public void addToAdjacentSum(long adjacentMatches) {
		this.adjacentMatchSum += adjacentMatches;
	}

	/**
	 * Increments by 1 the number of solutions tried up to this point
	 */
	public void incrementSolutions() {
		this.numSolutions++;
	}

	/**
	 * @return the totalMatchSum
	 */
	public long getTotalMatchSum() {
		return totalMatchSum;
	}

	/**
	 * @return the numSolutions
	 */
	public long getNumSolutions() {
		return numSolutions;
	}

	/**
	 * @return the uniqueMatchSum
	 */
	public long getUniqueMatchSum() {
		return uniqueMatchSum;
	}

	/**
	 * @return the adjacentMatchSum
	 */
	public long getAdjacentMatchSum() {
		return adjacentMatchSum;
	}

	/**
	 * @return the solutionMostMatches
	 */
	public SolutionChromosome getSolutionMostMatches() {
		return solutionMostMatches;
	}

	/**
	 * @param solutionMostMatches
	 *            the solutionMostMatches to set
	 */
	public void setSolutionMostMatches(SolutionChromosome solutionMostMatches) {
		this.solutionMostMatches = solutionMostMatches;
	}

	/**
	 * @return the solutionMostUnique
	 */
	public SolutionChromosome getSolutionMostUnique() {
		return solutionMostUnique;
	}

	/**
	 * @param solutionMostUnique
	 *            the solutionMostUnique to set
	 */
	public void setSolutionMostUnique(SolutionChromosome solutionMostUnique) {
		this.solutionMostUnique = solutionMostUnique;
	}

	/**
	 * @return the solutionMostAdjacent
	 */
	public SolutionChromosome getSolutionMostAdjacent() {
		return solutionMostAdjacent;
	}

	/**
	 * @param solutionMostAdjacent
	 *            the solutionMostAdjacent to set
	 */
	public void setSolutionMostAdjacent(SolutionChromosome solutionMostAdjacent) {
		this.solutionMostAdjacent = solutionMostAdjacent;
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}
}
