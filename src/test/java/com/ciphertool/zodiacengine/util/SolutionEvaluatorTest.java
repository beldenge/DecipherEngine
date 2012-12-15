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

package com.ciphertool.zodiacengine.util;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ciphertool.zodiacengine.entities.IncrementalSolution;
import com.ciphertool.zodiacengine.entities.Plaintext;

public class SolutionEvaluatorTest extends ZodiacTestBase {

	private static Logger log = Logger.getLogger(SolutionEvaluatorTest.class);
	private static final int ZODIAC_SOLUTION_MAX_FITNESS = 354;

	@Test
	public void testZodiacSolutionEvaluator() {
		ZodiacSolutionEvaluator fitnessEvaluator = new ZodiacSolutionEvaluator();
		fitnessEvaluator.setCipher(zodiac408);
		fitnessEvaluator.setAdjacencyThreshold(Integer.MAX_VALUE);
		fitnessEvaluator.setTotalMatchThreshold(Integer.MAX_VALUE);
		fitnessEvaluator.setUniqueMatchThreshold(Integer.MAX_VALUE);

		int fitness = fitnessEvaluator.determineConfidenceLevel(knownSolution);

		log.info(knownSolution);
		log.info("ZodiacSolutionEvaluator Fitness: " + fitness);

		assertEquals(fitness, ZODIAC_SOLUTION_MAX_FITNESS);
	}

	@Test
	public void testIncrementalSolutionEvaluator() {
		IncrementalSolutionEvaluator fitnessEvaluator = new IncrementalSolutionEvaluator();
		fitnessEvaluator.setCipher(zodiac408);

		IncrementalSolution incrementalSolution = new IncrementalSolution(zodiac408, 0, 0, 0);
		for (Plaintext plaintext : knownSolution.getPlaintextCharacters()) {
			incrementalSolution.addPlaintext(plaintext);
		}
		incrementalSolution.setUncommittedIndex(zodiac408.getCiphertextCharacters().size() + 1);

		int fitness = fitnessEvaluator.determineConfidenceLevel(incrementalSolution);

		log.info(incrementalSolution);
		log.info("IncrementalSolutionEvaluator Fitness: " + fitness);

		assertEquals(fitness, ZODIAC_SOLUTION_MAX_FITNESS);
	}

	@Test
	public void testZodiacSolutionPredicateEvaluator() {
		ZodiacSolutionPredicateEvaluator fitnessEvaluator = new ZodiacSolutionPredicateEvaluator();
		fitnessEvaluator.setCipher(zodiac408);

		int fitness = fitnessEvaluator.determineConfidenceLevel(knownSolution);

		log.info(knownSolution);
		log.info("ZodiacSolutionPredicateEvaluator Fitness: " + fitness);

		assertEquals(fitness, ZODIAC_SOLUTION_MAX_FITNESS);
	}
}
