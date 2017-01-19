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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.Ciphertext;

public class CipherSolution {
	private static Logger			log								= LoggerFactory.getLogger(CipherSolution.class);

	protected Cipher				cipher;

	private BigDecimal				probability						= BigDecimal.ONE;
	private BigDecimal				logProbability					= BigDecimal.ZERO;
	private BigDecimal				generativeModelProbability		= BigDecimal.ONE;
	private BigDecimal				generativeModelLogProbability	= BigDecimal.ZERO;
	private BigDecimal				languageModelProbability		= BigDecimal.ONE;
	private BigDecimal				languageModelLogProbability		= BigDecimal.ZERO;
	private BigDecimal				knownSolutionProximity			= null;

	private Map<String, Plaintext>	mappings;
	private Set<Integer>			wordBoundaries;

	public CipherSolution() {
		mappings = new HashMap<>();
		wordBoundaries = new HashSet<>();
	}

	public CipherSolution(Cipher cipher, int numCiphertext) {
		if (cipher == null) {
			throw new IllegalArgumentException("Cannot construct CipherSolution with null cipher.");
		}

		this.cipher = cipher;

		mappings = new HashMap<>(numCiphertext);
		wordBoundaries = new HashSet<>();
	}

	/**
	 * @return the cipher
	 */
	public Cipher getCipher() {
		return this.cipher;
	}

	/**
	 * @param cipher
	 *            the cipher to set
	 */
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	/**
	 * @return the probability
	 */
	public BigDecimal getProbability() {
		return probability;
	}

	public void setProbability(BigDecimal score) {
		this.probability = score;
	}

	/**
	 * @return the logProbability
	 */
	public BigDecimal getLogProbability() {
		return logProbability;
	}

	/**
	 * @param logProbability
	 *            the logProbability to set
	 */
	public void setLogProbability(BigDecimal logProbability) {
		this.logProbability = logProbability;
	}

	/**
	 * @return the generativeModelProbability
	 */
	public BigDecimal getGenerativeModelProbability() {
		return generativeModelProbability;
	}

	/**
	 * @param generativeModelProbability
	 *            the generativeModelProbability to set
	 */
	public void setGenerativeModelProbability(BigDecimal generativeModelProbability) {
		this.generativeModelProbability = generativeModelProbability;
	}

	/**
	 * @return the generativeModelLogProbability
	 */
	public BigDecimal getGenerativeModelLogProbability() {
		return generativeModelLogProbability;
	}

	/**
	 * @param generativeModelLogProbability
	 *            the generativeModelLogProbability to set
	 */
	public void setGenerativeModelLogProbability(BigDecimal generativeModelLogProbability) {
		this.generativeModelLogProbability = generativeModelLogProbability;
	}

	/**
	 * @return the languageModelProbability
	 */
	public BigDecimal getLanguageModelProbability() {
		return languageModelProbability;
	}

	/**
	 * @param languageModelProbability
	 *            the languageModelProbability to set
	 */
	public void setLanguageModelProbability(BigDecimal languageModelProbability) {
		this.languageModelProbability = languageModelProbability;
	}

	/**
	 * @return the languageModelLogProbability
	 */
	public BigDecimal getLanguageModelLogProbability() {
		return languageModelLogProbability;
	}

	/**
	 * @param languageModelLogProbability
	 *            the languageModelLogProbability to set
	 */
	public void setLanguageModelLogProbability(BigDecimal languageModelLogProbability) {
		this.languageModelLogProbability = languageModelLogProbability;
	}

	/**
	 * @return the knownSolutionProximity
	 */
	public BigDecimal getKnownSolutionProximity() {
		return knownSolutionProximity;
	}

	/**
	 * @param knownSolutionProximity
	 *            the knownSolutionProximity to set
	 */
	public void setKnownSolutionProximity(BigDecimal knownSolutionProximity) {
		this.knownSolutionProximity = knownSolutionProximity;
	}

	public Map<String, Plaintext> getMappings() {
		return Collections.unmodifiableMap(mappings);
	}

	public void putMapping(String key, Plaintext plaintext) {
		if (null == plaintext) {
			log.warn("Attempted to insert a null mapping to CipherSolution.  Returning. " + this);

			return;
		}

		if (this.mappings.get(key) != null) {
			log.warn("Attempted to insert a mapping to CipherSolution with key " + key
					+ ", but the key already exists.  If this was intentional, please use replaceMapping() instead.  Returning. "
					+ this);

			return;
		}

		this.mappings.put(key, plaintext);
	}

	public Plaintext removeMapping(Ciphertext key) {
		if (null == this.mappings || null == this.mappings.get(key)) {
			log.warn("Attempted to remove a mapping from CipherSolution with key " + key
					+ ", but this key does not exist.  Returning null.");

			return null;
		}

		return this.mappings.remove(key);
	}

	/*
	 * This does the same thing as putMapping(), and exists solely for semantic consistency.
	 */
	public void replaceMapping(String key, Plaintext newPlaintext) {
		if (null == newPlaintext) {
			log.warn("Attempted to replace a mapping from CipherSolution, but the supplied mapping was null.  Cannot continue. "
					+ this);

			return;
		}

		if (null == this.mappings || null == this.mappings.get(key)) {
			log.warn("Attempted to replace a mapping from CipherSolution with key " + key
					+ ", but this key does not exist.  Cannot continue.");

			return;
		}

		this.mappings.put(key, newPlaintext);
	}

	public Set<Integer> getWordBoundaries() {
		return Collections.unmodifiableSet(this.wordBoundaries);
	}

	public void addWordBoundary(Integer wordBoundary) {
		if (null == wordBoundary) {
			log.warn("Attempted to insert a null WordBoundary CipherSolution.  Returning. ");

			return;
		}

		this.wordBoundaries.add(wordBoundary);
	}

	public boolean removeWordBoundary(Integer wordBoundary) {
		return this.wordBoundaries.remove(wordBoundary);
	}

	public CipherSolution clone() {
		CipherSolution copySolution = new CipherSolution(this.cipher, this.mappings.size());

		for (Map.Entry<String, Plaintext> entry : this.mappings.entrySet()) {
			copySolution.putMapping(entry.getKey(), entry.getValue().clone());
		}

		for (Integer boundary : this.wordBoundaries) {
			copySolution.addWordBoundary(boundary.intValue());
		}

		// We need to set these values last to maintain whether evaluation is needed on the clone
		copySolution.setProbability(this.probability);
		copySolution.setLogProbability(this.logProbability);
		copySolution.setGenerativeModelProbability(this.generativeModelProbability);
		copySolution.setGenerativeModelLogProbability(this.generativeModelLogProbability);
		copySolution.setLanguageModelProbability(this.languageModelProbability);
		copySolution.setLanguageModelLogProbability(this.languageModelLogProbability);

		return copySolution;
	}

	/*
	 * Prints the properties of the solution and then outputs the entire plaintext list in block format.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Solution [probability=" + probability.toString() + ", logProbability=" + logProbability.toString()
				+ (knownSolutionProximity != null ? ", proximity="
						+ String.format("%1$,.2f", knownSolutionProximity.doubleValue() * 100.0) + "%" : "")
				+ ", generativeModelProbability=" + generativeModelProbability.toString()
				+ ", generativeModelLogProbability=" + generativeModelLogProbability.toString()
				+ ", languageModelProbability=" + languageModelProbability.toString() + ", languageModelLogProbability="
				+ languageModelLogProbability.toString() + "]\n");

		if (this.cipher != null) {
			String nextPlaintext = null;
			int actualSize = this.cipher.getCiphertextCharacters().size();
			for (int i = 0; i < actualSize; i++) {

				nextPlaintext = this.mappings.get(this.cipher.getCiphertextCharacters().get(i).getValue()).getValue();

				sb.append(" ");
				sb.append(nextPlaintext);
				sb.append(" ");

				/*
				 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function doesn't
				 * break.
				 */
				if (((i + 1) % this.cipher.getColumns()) == 0) {
					if (wordBoundaries.contains(i)) {
						sb.append("|");
					}

					sb.append("\n");
				} else {
					if (wordBoundaries.contains(i)) {
						sb.append("|");
					} else {
						sb.append(" ");
					}
				}
			}
		}

		return sb.toString();
	}
}
