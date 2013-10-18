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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;

public abstract class AbstractSolutionEvaluatorBase {
	protected Cipher cipher;
	protected HashMap<String, List<Ciphertext>> ciphertextKey;

	/**
	 * Creates a map with the key as the String value of the Ciphertext
	 * character and the value as a List of occurrences within the cipher
	 * 
	 * There's no reason to run this for every single iteration of the validator
	 * since the ciphertext is not going to change during a run
	 * 
	 * @return
	 */
	protected HashMap<String, List<Ciphertext>> createKeyFromCiphertext() {
		HashMap<String, List<Ciphertext>> ciphertextKey = new HashMap<String, List<Ciphertext>>();
		for (Ciphertext ct : cipher.getCiphertextCharacters()) {
			if (!ciphertextKey.containsKey(ct.getValue())) {
				ciphertextKey.put(ct.getValue(), new ArrayList<Ciphertext>());
			}
			ciphertextKey.get(ct.getValue()).add(ct);
		}
		return ciphertextKey;
	}

	protected void clearHasMatchValues(SolutionChromosome solutionChromosome) {
		for (PlaintextSequence plaintext : solutionChromosome.getPlaintextCharacters()) {
			plaintext.setHasMatch(false);
		}
	}
}
