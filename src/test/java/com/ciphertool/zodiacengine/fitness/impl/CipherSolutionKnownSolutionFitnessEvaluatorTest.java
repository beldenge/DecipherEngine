/**
 * Copyright 2015 George Belden
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

package com.ciphertool.zodiacengine.fitness.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ciphertool.zodiacengine.fitness.FitnessEvaluatorTestBase;

public class CipherSolutionKnownSolutionFitnessEvaluatorTest extends FitnessEvaluatorTestBase {
	@Test
	public void testEvaluate() {
		CipherSolutionKnownSolutionFitnessEvaluator fitnessEvaluator = new CipherSolutionKnownSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertTrue(knownSolution.isEvaluationNeeded());
		knownSolution.setFitness(fitness);
		assertFalse(knownSolution.isEvaluationNeeded());

		/*
		 * This should return 100% since we know the solution.
		 */
		assertEquals(new Double(100.0), fitness);
		assertEquals(391, knownSolution.getTotalMatches());
		assertEquals(0, knownSolution.getUniqueMatches());
		assertEquals(0, knownSolution.getAdjacentMatches());
	}
}
