/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with DecipherEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.breeder;

import java.math.BigDecimal;

public class LetterProbability implements Probability<Character>, Comparable<LetterProbability> {
	private Character	letter;
	private BigDecimal	probability;

	/**
	 * @param letter
	 *            the letter
	 * @param probability
	 *            the probability
	 */
	public LetterProbability(Character letter, BigDecimal probability) {
		this.letter = letter;
		this.probability = probability;
	}

	@Override
	public Character getValue() {
		return letter;
	}

	@Override
	public BigDecimal getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 *            the probability to set
	 */
	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	@Override
	public BigDecimal getLogProbability() {
		throw new UnsupportedOperationException("Method not implemented!");
	}

	@Override
	public int compareTo(LetterProbability other) {
		return this.probability.compareTo(other.probability);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((letter == null) ? 0 : letter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof LetterProbability)) {
			return false;
		}
		LetterProbability other = (LetterProbability) obj;
		if (letter == null) {
			if (other.letter != null) {
				return false;
			}
		} else if (!letter.equals(other.letter)) {
			return false;
		}
		return true;
	}
}
