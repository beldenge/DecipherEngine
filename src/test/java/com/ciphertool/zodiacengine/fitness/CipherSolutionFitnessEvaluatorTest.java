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

package com.ciphertool.zodiacengine.fitness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.zodiacengine.algorithms.GeneticAlgorithmTestBase;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFrequencyFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFrequencyLengthFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFrequencyTruncatedFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionMatchDistanceFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionMultipleFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionTruncatedFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionUniqueWordFitnessEvaluator;

public class CipherSolutionFitnessEvaluatorTest extends GeneticAlgorithmTestBase {
	private static Logger log = Logger.getLogger(CipherSolutionFitnessEvaluatorTest.class);

	private static Map<Character, Double> expectedLetterFrequencies = new HashMap<Character, Double>();
	private static Double averageWordLength = 5.1;
	private static Map<Character, Integer> knownSolutionLetterTotals = new HashMap<Character, Integer>();
	private static Map<Character, Integer> expectedLetterTotals = new HashMap<Character, Integer>();

	@BeforeClass
	public static void setUpFrequencies() {
		expectedLetterFrequencies.put('a', 0.0812);
		expectedLetterFrequencies.put('b', 0.0149);
		expectedLetterFrequencies.put('c', 0.0271);
		expectedLetterFrequencies.put('d', 0.0432);
		expectedLetterFrequencies.put('e', 0.1202);
		expectedLetterFrequencies.put('f', 0.0230);
		expectedLetterFrequencies.put('g', 0.0203);
		expectedLetterFrequencies.put('h', 0.0592);
		expectedLetterFrequencies.put('i', 0.0731);
		expectedLetterFrequencies.put('j', 0.0010);
		expectedLetterFrequencies.put('k', 0.0069);
		expectedLetterFrequencies.put('l', 0.0398);
		expectedLetterFrequencies.put('m', 0.0261);
		expectedLetterFrequencies.put('n', 0.0695);
		expectedLetterFrequencies.put('o', 0.0768);
		expectedLetterFrequencies.put('p', 0.0182);
		expectedLetterFrequencies.put('q', 0.0011);
		expectedLetterFrequencies.put('r', 0.0602);
		expectedLetterFrequencies.put('s', 0.0628);
		expectedLetterFrequencies.put('t', 0.0910);
		expectedLetterFrequencies.put('u', 0.0288);
		expectedLetterFrequencies.put('v', 0.0111);
		expectedLetterFrequencies.put('w', 0.0209);
		expectedLetterFrequencies.put('x', 0.0017);
		expectedLetterFrequencies.put('y', 0.0211);
		expectedLetterFrequencies.put('z', 0.0007);

		populateExpectedLetterTotals();
		populateSolutionLetterTotals();
	}

	/*
	 * This is just used to help validate frequency evaluators.
	 */
	private static void populateExpectedLetterTotals() {
		int numCharsToEvaluate = zodiac408.getColumns() * (zodiac408.getRows() - 1);

		expectedLetterTotals.put('a', ((Long) Math.round(expectedLetterFrequencies.get('a')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('b', ((Long) Math.round(expectedLetterFrequencies.get('b')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('c', ((Long) Math.round(expectedLetterFrequencies.get('c')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('d', ((Long) Math.round(expectedLetterFrequencies.get('d')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('e', ((Long) Math.round(expectedLetterFrequencies.get('e')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('f', ((Long) Math.round(expectedLetterFrequencies.get('f')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('g', ((Long) Math.round(expectedLetterFrequencies.get('g')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('h', ((Long) Math.round(expectedLetterFrequencies.get('h')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('i', ((Long) Math.round(expectedLetterFrequencies.get('i')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('j', ((Long) Math.round(expectedLetterFrequencies.get('j')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('k', ((Long) Math.round(expectedLetterFrequencies.get('k')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('l', ((Long) Math.round(expectedLetterFrequencies.get('l')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('m', ((Long) Math.round(expectedLetterFrequencies.get('m')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('n', ((Long) Math.round(expectedLetterFrequencies.get('n')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('o', ((Long) Math.round(expectedLetterFrequencies.get('o')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('p', ((Long) Math.round(expectedLetterFrequencies.get('p')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('q', ((Long) Math.round(expectedLetterFrequencies.get('q')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('r', ((Long) Math.round(expectedLetterFrequencies.get('r')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('s', ((Long) Math.round(expectedLetterFrequencies.get('s')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('t', ((Long) Math.round(expectedLetterFrequencies.get('t')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('u', ((Long) Math.round(expectedLetterFrequencies.get('u')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('v', ((Long) Math.round(expectedLetterFrequencies.get('v')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('w', ((Long) Math.round(expectedLetterFrequencies.get('w')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('x', ((Long) Math.round(expectedLetterFrequencies.get('x')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('y', ((Long) Math.round(expectedLetterFrequencies.get('y')
				* numCharsToEvaluate)).intValue());
		expectedLetterTotals.put('z', ((Long) Math.round(expectedLetterFrequencies.get('z')
				* numCharsToEvaluate)).intValue());

		log.info("ExpectedLetterTotals:      " + expectedLetterTotals);
		int letterSum = 0;
		for (Integer letterCount : expectedLetterTotals.values()) {
			letterSum += letterCount;
		}
		log.info("Total letters expected: " + letterSum);
	}

	/*
	 * This is just used to help validate frequency evaluators.
	 */
	private static void populateSolutionLetterTotals() {
		knownSolutionLetterTotals.put('a', 0);
		knownSolutionLetterTotals.put('b', 0);
		knownSolutionLetterTotals.put('c', 0);
		knownSolutionLetterTotals.put('d', 0);
		knownSolutionLetterTotals.put('e', 0);
		knownSolutionLetterTotals.put('f', 0);
		knownSolutionLetterTotals.put('g', 0);
		knownSolutionLetterTotals.put('h', 0);
		knownSolutionLetterTotals.put('i', 0);
		knownSolutionLetterTotals.put('j', 0);
		knownSolutionLetterTotals.put('k', 0);
		knownSolutionLetterTotals.put('l', 0);
		knownSolutionLetterTotals.put('m', 0);
		knownSolutionLetterTotals.put('n', 0);
		knownSolutionLetterTotals.put('o', 0);
		knownSolutionLetterTotals.put('p', 0);
		knownSolutionLetterTotals.put('q', 0);
		knownSolutionLetterTotals.put('r', 0);
		knownSolutionLetterTotals.put('s', 0);
		knownSolutionLetterTotals.put('t', 0);
		knownSolutionLetterTotals.put('u', 0);
		knownSolutionLetterTotals.put('v', 0);
		knownSolutionLetterTotals.put('w', 0);
		knownSolutionLetterTotals.put('x', 0);
		knownSolutionLetterTotals.put('y', 0);
		knownSolutionLetterTotals.put('z', 0);

		int numCharsToEvaluate = zodiac408.getColumns() * (zodiac408.getRows() - 1);

		for (PlaintextSequence plaintext : knownSolution.getPlaintextCharacters()) {
			if (plaintext.getSequenceId() < numCharsToEvaluate) {
				char plaintextChar = plaintext.getValue().charAt(0);
				knownSolutionLetterTotals.put(plaintextChar, knownSolutionLetterTotals
						.get(plaintextChar) + 1);
			}
		}

		log.info("KnownSolutionLetterTotals: " + knownSolutionLetterTotals);

		int letterSum = 0;
		for (Integer letterCount : knownSolutionLetterTotals.values()) {
			letterSum += letterCount;
		}
		log.info("Total letters known: " + letterSum);

		int validWordCount = 0;
		for (Gene gene : knownSolution.getGenes()) {
			if (gene.getSequences().get(0).getSequenceId() < numCharsToEvaluate) {
				validWordCount++;
			}
		}
		log.info("Average word length known: "
				+ ((double) numCharsToEvaluate / (double) (validWordCount)));
	}

	@Before
	public void resetDirtiness() {
		knownSolution.setEvaluationNeeded(true);
		assertTrue(knownSolution.isEvaluationNeeded());
	}

	@Test
	public void testCipherSolutionFitnessEvaluator() {
		CipherSolutionFitnessEvaluator fitnessEvaluator = new CipherSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionFitnessEvaluator Fitness: " + fitness);

		assertEquals(fitness, new Double(354.0));
	}

	@Test
	public void testCipherSolutionMultipleFitnessEvaluator() {
		CipherSolutionMultipleFitnessEvaluator fitnessEvaluator = new CipherSolutionMultipleFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionMultipleFitnessEvaluator Fitness: " + fitness);

		assertEquals(fitness, new Double(602.0));
	}

	@Test
	public void testCipherSolutionTruncatedFitnessEvaluator() {
		CipherSolutionTruncatedFitnessEvaluator fitnessEvaluator = new CipherSolutionTruncatedFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionTruncatedFitnessEvaluator Fitness: " + fitness);

		assertEquals(fitness, new Double(337.0));
	}

	@Test
	public void testCipherSolutionKnownSolutionFitnessEvaluator() {
		CipherSolutionKnownSolutionFitnessEvaluator fitnessEvaluator = new CipherSolutionKnownSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionKnownSolutionFitnessEvaluator Fitness: " + fitness);

		/*
		 * This should return 100% since we know the solution.
		 */
		assertEquals(fitness, new Double(100.0));
	}

	@Test
	public void testCipherSolutionFrequencyFitnessEvaluator() {
		CipherSolutionFrequencyFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyFitnessEvaluator();
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionFrequencyFitnessEvaluator Fitness: " + fitness);
	}

	@Test
	public void testCipherSolutionFrequencyTruncatedFitnessEvaluator() {
		CipherSolutionFrequencyTruncatedFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyTruncatedFitnessEvaluator();
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionFrequencyTruncatedFitnessEvaluator Fitness: " + fitness);
	}

	@Test
	public void testCipherSolutionFrequencyLengthFitnessEvaluator() {
		CipherSolutionFrequencyLengthFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyLengthFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);
		fitnessEvaluator.setAverageWordLength(averageWordLength);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionFrequencyLengthFitnessEvaluator Fitness: " + fitness);
	}

	@Test
	public void testCipherSolutionMatchDistanceFitnessEvaluator() {
		CipherSolutionMatchDistanceFitnessEvaluator fitnessEvaluator = new CipherSolutionMatchDistanceFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionMatchDistanceFitnessEvaluator Fitness: " + fitness);
	}

	@Test
	public void testCipherSolutionUniqueWordFitnessEvaluator() {
		CipherSolutionUniqueWordFitnessEvaluator fitnessEvaluator = new CipherSolutionUniqueWordFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		assertTrue(knownSolution.isEvaluationNeeded());
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		assertFalse(knownSolution.isEvaluationNeeded());

		log.info(knownSolution);
		log.info("CipherSolutionUniqueWordFitnessEvaluator Fitness: " + fitness);
	}
}
