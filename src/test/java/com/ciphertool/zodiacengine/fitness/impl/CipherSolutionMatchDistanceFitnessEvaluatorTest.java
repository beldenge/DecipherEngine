package com.ciphertool.zodiacengine.fitness.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ciphertool.zodiacengine.algorithms.GeneticAlgorithmTestBase;

public class CipherSolutionMatchDistanceFitnessEvaluatorTest extends GeneticAlgorithmTestBase {
	@Test
	public void testEvaluate() {
		CipherSolutionMatchDistanceFitnessEvaluator fitnessEvaluator = new CipherSolutionMatchDistanceFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertTrue(knownSolution.isEvaluationNeeded());
		knownSolution.setFitness(fitness);
		assertFalse(knownSolution.isEvaluationNeeded());

		assertEquals(new Double(335.315), fitness);
		assertEquals(337, knownSolution.getTotalMatches());
		assertEquals(53, knownSolution.getUniqueMatches());
		assertEquals(390, knownSolution.getAdjacentMatches());
	}
}
