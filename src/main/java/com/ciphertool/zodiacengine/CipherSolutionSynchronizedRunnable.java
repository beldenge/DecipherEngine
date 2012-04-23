package com.ciphertool.zodiacengine;

import com.ciphertool.zodiacengine.dto.CipherDto;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.util.SolutionEvaluator;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class CipherSolutionSynchronizedRunnable implements Runnable {
	private SolutionGenerator solutionGenerator;
	private SolutionEvaluator solutionEvaluator;
	private CipherDto cipherDto;
	
	/**
	 * @param solutionGenerator
	 * @param solutionEvaluator
	 * @param cipherDto
	 */
	public CipherSolutionSynchronizedRunnable(SolutionGenerator solutionGenerator,
			SolutionEvaluator solutionEvaluator, CipherDto cipherDto) {
		this.solutionGenerator = solutionGenerator;
		this.solutionEvaluator = solutionEvaluator;
		this.cipherDto = cipherDto;
	}

	public void run() {
		Solution solution = null;
		int totalMatches = 0;
		int uniqueMatches = 0;
		int adjacentMatches = 0;
		
		// generate a solution
		solution = solutionGenerator.generateSolution();
		
		// find the confidence level for the solution
		solutionEvaluator.determineConfidenceLevel(solution);
				
		// we need the confidence level several times, so just call the getter once and store in a temp variable
		totalMatches = solution.getTotalMatches();
		uniqueMatches = solution.getUniqueMatches();
		adjacentMatches = solution.getAdjacentMatchCount();
		
		synchronized (cipherDto) {
			// check if there's a new solution with more total matches
			if(totalMatches > cipherDto.getSolutionMostMatches().getTotalMatches()) {
				cipherDto.setSolutionMostMatches(solution);
			}
	
			// check if there's a new greater uniquely matched solution
			if(uniqueMatches > cipherDto.getSolutionMostUnique().getUniqueMatches()) {
				cipherDto.setSolutionMostUnique(solution);
			}
			
			// check if there's a new greater adjacently matched solution
			if(adjacentMatches > cipherDto.getSolutionMostAdjacent().getAdjacentMatchCount()) {
				cipherDto.setSolutionMostAdjacent(solution);
			}
			
			cipherDto.incrementSolutions();
			
			// add to the running totals for computing the average later
			cipherDto.addToSum(totalMatches);
			cipherDto.addToUniqueSum(uniqueMatches);
			cipherDto.addToAdjacentSum(adjacentMatches);
		}
	}
}
