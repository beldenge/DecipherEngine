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

package com.ciphertool.zodiacengine.util;

import org.apache.commons.collections.Predicate;

import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;

public class PlaintextPredicate implements Predicate {

	private Ciphertext ciphertext;

	public PlaintextPredicate(Ciphertext ciphertext) {
		this.ciphertext = ciphertext;
	}

	@Override
	public boolean evaluate(Object p) {
		Plaintext plaintext = (Plaintext) p;
		return (plaintext.getPlaintextId().getCiphertextId() == this.ciphertext.getCiphertextId()
				.getId());
	}
}
