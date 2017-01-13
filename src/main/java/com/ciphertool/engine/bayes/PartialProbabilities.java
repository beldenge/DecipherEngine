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

public class PartialProbabilities {
	private BigDecimal	productOfProbabilities;
	private BigDecimal	sumOfProbabilities;

	/**
	 * @param productOfProbabilities
	 *            the product of probabilities
	 * @param sumOfProbabilities
	 *            the sum of probabilities
	 */
	public PartialProbabilities(BigDecimal productOfProbabilities, BigDecimal sumOfProbabilities) {
		this.productOfProbabilities = productOfProbabilities;
		this.sumOfProbabilities = sumOfProbabilities;
	}

	/**
	 * @return the productOfProbabilities
	 */
	public BigDecimal getProductOfProbabilities() {
		return productOfProbabilities;
	}

	/**
	 * @return the sumOfProbabilities
	 */
	public BigDecimal getSumOfProbabilities() {
		return sumOfProbabilities;
	}
}
