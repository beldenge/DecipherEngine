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

import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;

public abstract class SolutionEvaluatorBase extends AbstractSolutionEvaluatorBase {
	protected HashMap<String, List<Ciphertext>> ciphertextKey;

	@Override
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

	@Override
	protected void clearHasMatchValues(SolutionChromosome solutionChromosome) {
		for (PlaintextSequence plaintext : solutionChromosome.getPlaintextCharacters()) {
			plaintext.setHasMatch(false);
		}
	}
}
