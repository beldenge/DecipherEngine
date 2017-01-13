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
import java.util.HashMap;
import java.util.Map;

public class PartialDerivation {
	BigDecimal							productOfProbabilities	= BigDecimal.ONE;
	BigDecimal							sumOfProbabilities		= BigDecimal.ZERO;
	Map<String, BigDecimal>				unigramCounts			= new HashMap<>();
	Map<String, BigDecimal>				bigramCounts			= new HashMap<>();
	Map<CiphertextMapping, BigDecimal>	ciphertextMappingCounts	= new HashMap<>();

	/**
	 * @param productOfProbabilities
	 *            the product of probabilities
	 * @param sumOfProbabilities
	 *            the sum of probabilities
	 */
	public PartialDerivation(BigDecimal productOfProbabilities, BigDecimal sumOfProbabilities) {
		super();
		this.productOfProbabilities = productOfProbabilities;
		this.sumOfProbabilities = sumOfProbabilities;
	}

	/**
	 * @param productOfProbabilities
	 *            the product of probabilities
	 * @param sumOfProbabilities
	 *            the sum of probabilities
	 * @param unigramCounts
	 *            the unigram counts
	 * @param bigramCounts
	 *            the bigram counts
	 * @param ciphertextMappingCounts
	 *            the ciphertext mapping counts
	 */
	public PartialDerivation(BigDecimal productOfProbabilities, BigDecimal sumOfProbabilities,
			Map<String, BigDecimal> unigramCounts, Map<String, BigDecimal> bigramCounts,
			Map<CiphertextMapping, BigDecimal> ciphertextMappingCounts) {
		this.productOfProbabilities = productOfProbabilities;
		this.sumOfProbabilities = sumOfProbabilities;
		this.unigramCounts = unigramCounts;
		this.bigramCounts = bigramCounts;
		this.ciphertextMappingCounts = ciphertextMappingCounts;
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

	/**
	 * @return the unigramCounts
	 */
	public Map<String, BigDecimal> getUnigramCounts() {
		return unigramCounts;
	}

	/**
	 * @return the bigramCounts
	 */
	public Map<String, BigDecimal> getBigramCounts() {
		return bigramCounts;
	}

	/**
	 * @return the ciphertextMappingCounts
	 */
	public Map<CiphertextMapping, BigDecimal> getCiphertextMappingCounts() {
		return ciphertextMappingCounts;
	}
}
