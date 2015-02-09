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

package com.ciphertool.zodiacengine.fitness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFrequencyFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFrequencyLengthFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionFrequencyTruncatedFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionKnownSolutionExponentialFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionKnownSolutionFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionMagicWordFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionMatchDistanceFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionMatchDistanceLengthFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionMultipleFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionTruncatedFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionUniqueWordFitnessEvaluator;
import com.ciphertool.zodiacengine.fitness.impl.CipherSolutionUniqueWordLengthFitnessEvaluator;

public class FitnessEvaluatorTypeTest {
	@Test
	public void testTypes() {
		assertEquals(13, FitnessEvaluatorType.values().length);

		assertSame(CipherSolutionFitnessEvaluator.class, FitnessEvaluatorType.CIPHER_SOLUTION
				.getType());
		assertSame(CipherSolutionTruncatedFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_TRUNCATED.getType());
		assertSame(CipherSolutionMultipleFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_MULTIPLE.getType());
		assertSame(CipherSolutionKnownSolutionFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_KNOWN_SOLUTION.getType());
		assertSame(CipherSolutionKnownSolutionExponentialFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_KNOWN_SOLUTION_EXPONENTIAL.getType());
		assertSame(CipherSolutionMagicWordFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_MAGIC_WORD.getType());
		assertSame(CipherSolutionFrequencyFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_FREQUENCY.getType());
		assertSame(CipherSolutionFrequencyTruncatedFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_FREQUENCY_TRUNCATED.getType());
		assertSame(CipherSolutionFrequencyLengthFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_FREQUENCY_LENGTH.getType());
		assertSame(CipherSolutionMatchDistanceFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_MATCH_DISTANCE.getType());
		assertSame(CipherSolutionUniqueWordFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_UNIQUE_WORD.getType());
		assertSame(CipherSolutionMatchDistanceLengthFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_MATCH_DISTANCE_LENGTH.getType());
		assertSame(CipherSolutionUniqueWordLengthFitnessEvaluator.class,
				FitnessEvaluatorType.CIPHER_SOLUTION_UNIQUE_WORD_LENGTH.getType());
	}
}
