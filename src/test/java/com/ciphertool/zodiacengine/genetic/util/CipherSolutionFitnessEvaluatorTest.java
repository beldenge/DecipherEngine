package com.ciphertool.zodiacengine.genetic.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.zodiacengine.genetic.GeneticAlgorithmTestBase;

public class CipherSolutionFitnessEvaluatorTest extends GeneticAlgorithmTestBase {
	private static Logger log = Logger.getLogger(CipherSolutionFitnessEvaluatorTest.class);

	private static Map<Character, Double> expectedLetterFrequencies = new HashMap<Character, Double>();
	private static Double averageWordLength = 5.1;

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
	}

	@Test
	public void testCipherSolutionFitnessEvaluator() {
		CipherSolutionFitnessEvaluator fitnessEvaluator = new CipherSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		log.info(knownSolution);
		log.info("Fitness: " + fitness);

		assertEquals(fitness, new Double(354.0));
	}

	@Test
	public void testCipherSolutionTruncatedFitnessEvaluator() {
		CipherSolutionTruncatedFitnessEvaluator fitnessEvaluator = new CipherSolutionTruncatedFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		log.info(knownSolution);
		log.info("Fitness: " + fitness);

		assertEquals(fitness, new Double(337.0));
	}

	@Test
	public void testCipherSolutionFrequencyFitnessEvaluator() {
		CipherSolutionFrequencyFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);

		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		log.info(knownSolution);
		log.info("Fitness: " + fitness);
	}

	@Test
	public void testCipherSolutionFrequencyTruncatedFitnessEvaluator() {
		CipherSolutionFrequencyTruncatedFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyTruncatedFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);

		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		log.info(knownSolution);
		log.info("Fitness: " + fitness);
	}

	@Test
	public void testCipherSolutionFrequencyLengthFitnessEvaluator() {
		CipherSolutionFrequencyLengthFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyLengthFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);
		fitnessEvaluator.setAverageWordLength(averageWordLength);

		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		log.info(knownSolution);
		log.info("Fitness: " + fitness);
	}
}
