package com.ciphertool.zodiacengine.dto;

import com.ciphertool.zodiacengine.entities.Solution;

public class CipherDto {
	private long confidenceSum;
	private long numSolutions;
	private long uniqueMatchSum;
	private long adjacentMatchSum;
	private Solution solutionMostMatches;
	private Solution solutionMostUnique;
	private Solution solutionMostAdjacent;
	private String threadName;
	
	/**
	 * No-args constructor
	 */
	public CipherDto(String threadName, int cipherId) {
		this.threadName = threadName;
		
		this.confidenceSum = 0;
		this.numSolutions = 0;
		this.uniqueMatchSum  = 0;
		this.adjacentMatchSum = 0;
		
		/*
		 * We set "dummy" Solutions with the lowest possible confidence values
		 */
		this.solutionMostMatches = new Solution(cipherId, 0, 0);
		this.solutionMostUnique = new Solution(cipherId, 0, 0);
		this.solutionMostAdjacent = new Solution(cipherId, 0, 0);
	}
	
	/**
	 * Computes the average confidence level of all solutions tried up to this point
	 * 
	 * @return
	 */
	public long getAverage() {
		return (this.confidenceSum / this.numSolutions);
	}
	
	
	/**
	 * @param confidence
	 */
	public void addToSum(long confidence) {
		this.confidenceSum += confidence;
	}

	/**
	 * @param confidence
	 */
	public void addToUniqueSum(long uniqueMatches) {
		this.uniqueMatchSum += uniqueMatches;
	}
	
	/**
	 * @param confidence
	 */
	public void addToAdjacentSum(long adjacentMatches) {
		this.adjacentMatchSum += adjacentMatches;
	}
	
	/**
	 * Increments by 1 the number of solutions tried up to this point
	 */
	public void incrementSolutions() {
		this.numSolutions ++;
	}

	/**
	 * @return the confidenceSum
	 */
	public long getConfidenceSum() {
		return confidenceSum;
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
	public Solution getSolutionMostMatches() {
		return solutionMostMatches;
	}

	/**
	 * @param solutionMostMatches the solutionMostMatches to set
	 */
	public void setSolutionMostMatches(Solution solutionMostMatches) {
		this.solutionMostMatches = solutionMostMatches;
	}

	/**
	 * @return the solutionMostUnique
	 */
	public Solution getSolutionMostUnique() {
		return solutionMostUnique;
	}

	/**
	 * @param solutionMostUnique the solutionMostUnique to set
	 */
	public void setSolutionMostUnique(Solution solutionMostUnique) {
		this.solutionMostUnique = solutionMostUnique;
	}

	/**
	 * @return the solutionMostAdjacent
	 */
	public Solution getSolutionMostAdjacent() {
		return solutionMostAdjacent;
	}

	/**
	 * @param solutionMostAdjacent the solutionMostAdjacent to set
	 */
	public void setSolutionMostAdjacent(Solution solutionMostAdjacent) {
		this.solutionMostAdjacent = solutionMostAdjacent;
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}
}
