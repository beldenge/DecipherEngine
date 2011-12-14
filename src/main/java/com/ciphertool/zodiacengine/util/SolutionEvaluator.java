package com.ciphertool.zodiacengine.util;

import com.ciphertool.zodiacengine.entities.Solution;

public interface SolutionEvaluator {
	
	
	/**
	 * @param solution
	 * @return
	 * 
	 * Calculates the confidence level of a solution and returns as an integer, where the higher the number, the higher the confidence 
	 */
	public int determineConfidenceLevel(Solution solution);

}
