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

import com.ciphertool.genetics.util.fitness.FitnessEvaluator;
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

public enum FitnessEvaluatorType {
	CIPHER_SOLUTION(
			"Cipher Solution",
			CipherSolutionFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol"),
	CIPHER_SOLUTION_TRUNCATED(
			"Cipher Solution Truncated",
			CipherSolutionTruncatedFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, skipping the last row of the Cipher"),
	CIPHER_SOLUTION_MULTIPLE(
			"Cipher Solution Multiple",
			CipherSolutionMultipleFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, skipping the last row of the Cipher, and applying a unique match factor."),
	CIPHER_SOLUTION_KNOWN_SOLUTION(
			"Cipher Solution Known Solution",
			CipherSolutionKnownSolutionFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match a known solution, skipping the last row of the Cipher"),
	CIPHER_SOLUTION_KNOWN_SOLUTION_EXPONENTIAL(
			"Cipher Solution Known Solution Exponential",
			CipherSolutionKnownSolutionExponentialFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match a known solution, skipping the last row of the Cipher, and giving exponentially higher fitness for each ciphertext match."),
	CIPHER_SOLUTION_MAGIC_WORD(
			"Cipher Solution Magic Word",
			CipherSolutionMagicWordFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, skipping the last row of the Cipher.  It also produces a higher fitness if certain especially likely words are found."),
	CIPHER_SOLUTION_FREQUENCY(
			"Cipher Solution Frequency",
			CipherSolutionFrequencyFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, including a reduction factor based on letter frequency"),
	CIPHER_SOLUTION_FREQUENCY_TRUNCATED(
			"Cipher Solution Frequency Truncated",
			CipherSolutionFrequencyTruncatedFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, including a reduction factor based on letter frequency, skipping the last row of the Cipher"),
	CIPHER_SOLUTION_FREQUENCY_LENGTH(
			"Cipher Solution Frequency and Word Length",
			CipherSolutionFrequencyLengthFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, including a reduction factor based on letter frequency and another reduction factor based on average word length"),
	CIPHER_SOLUTION_MATCH_DISTANCE(
			"Cipher Solution Match Distance",
			CipherSolutionMatchDistanceFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, awarding extra points towards fitness due to sufficient gaps among identical words."),
	CIPHER_SOLUTION_UNIQUE_WORD(
			"Cipher Solution Unique Word",
			CipherSolutionUniqueWordFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, awarding extra points towards fitness based on the uniqueness of words."),
	CIPHER_SOLUTION_MATCH_DISTANCE_LENGTH(
			"Cipher Solution Match Distance / Word Length",
			CipherSolutionMatchDistanceLengthFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, awarding extra points towards fitness due to sufficient gaps among identical words.  Includes a factor based on average word length.  Skips the last row of the cipher."),
	CIPHER_SOLUTION_UNIQUE_WORD_LENGTH(
			"Cipher Solution Unique Word / Word Length",
			CipherSolutionUniqueWordLengthFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, awarding extra points towards fitness based on the uniqueness of words.  Includes a factor based on average word length.  Skips the last row of the cipher.");

	private String displayName;
	private Class<? extends FitnessEvaluator> type;
	private String description;

	FitnessEvaluatorType(String displayName, Class<? extends FitnessEvaluator> type,
			String description) {
		this.displayName = displayName;
		this.type = type;
		this.description = description;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the types
	 */
	public Class<? extends FitnessEvaluator> getType() {
		return type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the value returned by name()
	 */
	public String getName() {
		return name();
	}
}
