/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrequencyFitnessEvaluatorTestBase extends FitnessEvaluatorTestBase {
	private static Logger					log							= LoggerFactory.getLogger(FrequencyFitnessEvaluatorTestBase.class);

	protected static Map<Character, Double>	expectedLetterFrequencies	= new HashMap<Character, Double>();
	private static Map<Character, Integer>	expectedLetterTotals		= new HashMap<Character, Integer>();

	protected static Double					averageWordLength			= 5.1;

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

		/*
		 * This may be slightly different than the actual letter total since we are rounding the frequencies
		 */
		log.info("Total letters expected: " + letterSum);
	}
}
