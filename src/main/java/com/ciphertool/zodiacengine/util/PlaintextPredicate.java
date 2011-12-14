package com.ciphertool.zodiacengine.util;

import org.apache.commons.collections.Predicate;

import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;

public class PlaintextPredicate implements Predicate {

	private Ciphertext ciphertext;
	
	public PlaintextPredicate (Ciphertext ciphertext) {
		this.ciphertext = ciphertext;
	}

	@Override
	public boolean evaluate(Object p) {
		Plaintext plaintext = (Plaintext) p;
		return (plaintext.getPlaintextId().getCiphertextId() == this.ciphertext.getCiphertextId().getId());
	}
}
