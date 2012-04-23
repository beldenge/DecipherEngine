package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;

public abstract class AbstractSolutionEvaluatorBase {
	protected Cipher cipher;
	
	/**
	 * Creates a map with the key as the String value of the Ciphertext character and the value as a List of occurrences within the cipher
	 * 
	 * There's no reason to run this for every single iteration of the validator since the ciphertext is not going to change during a run
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
}
