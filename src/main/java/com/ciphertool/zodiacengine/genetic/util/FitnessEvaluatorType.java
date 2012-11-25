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

package com.ciphertool.zodiacengine.genetic.util;

import com.ciphertool.genetics.util.FitnessEvaluator;

public enum FitnessEvaluatorType {
	CIPHER_SOLUTION(
			"Cipher Solution",
			CipherSolutionFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol"),
	CIPHER_SOLUTION_FREQUENCY(
			"Cipher Solution Frequency",
			CipherSolutionFrequencyFitnessEvaluator.class,
			"This will evaluate cipher solutions based on the number of plaintext characters that match for the same ciphertext symbol, including a reduction factor based on letter frequency");

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
