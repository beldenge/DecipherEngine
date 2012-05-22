package com.ciphertool.zodiacengine.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CiphertextId implements Serializable {
	private static final long serialVersionUID = -7924706093006822875L;
	private int id;
	private Cipher cipher;

	public CiphertextId() {
	}

	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		result = prime * result + id;
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
		if (id != other.id)
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
		return "CiphertextId [id=" + id + ", cipherId=" + cipher.getId() + "]";
	}
}
