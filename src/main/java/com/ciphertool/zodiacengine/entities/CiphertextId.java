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

package com.ciphertool.zodiacengine.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CiphertextId implements Serializable {
	private static final long serialVersionUID = -7924706093006822875L;
	private int ciphertextId;
	private Cipher cipher;

	public CiphertextId() {
	}

	@Column(name = "ciphertext_id")
	public int getCiphertextId() {
		return ciphertextId;
	}

	public void setCiphertextId(int ciphertextId) {
		this.ciphertextId = ciphertextId;
	}

	@ManyToOne
	@JoinColumn(name = "cipher_id")
	public Cipher getCipher() {
		return cipher;
	}

	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * We need to use the cipherId only instead of the whole Cipher object.
	 * Otherwise we will run into a stack overflow.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cipher.getId();
		result = prime * result + ciphertextId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * We need to check the cipherId only instead of the whole Cipher object.
	 * Otherwise we will run into a stack overflow.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CiphertextId other = (CiphertextId) obj;
		if (cipher.getId() != other.getCipher().getId())
			return false;
		if (ciphertextId != other.ciphertextId)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * We need to use the cipherId only instead of the whole Cipher object.
	 * Otherwise we will run into a stack overflow.
	 */
	@Override
	public String toString() {
		return "CiphertextId [ciphertextId=" + ciphertextId + ", cipherId=" + cipher.getId() + "]";
	}
}
