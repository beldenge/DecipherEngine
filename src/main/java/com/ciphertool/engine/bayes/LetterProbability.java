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

package com.ciphertool.engine.bayes;

import java.math.BigDecimal;

public class LetterProbability implements Probability<Character> {
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
}
