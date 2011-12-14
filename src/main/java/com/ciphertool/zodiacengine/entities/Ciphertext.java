package com.ciphertool.zodiacengine.entities;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ciphertext")
@AssociationOverrides(
		@AssociationOverride(name = "ciphertextId.cipher", joinColumns = @JoinColumn(name = "cipher_id"))
)
public class Ciphertext {
	private String value;
	private CiphertextId ciphertextId;
	
	public Ciphertext() {}

	@EmbeddedId
	public CiphertextId getCiphertextId() {
		return ciphertextId;
	}

	public void setCiphertextId(CiphertextId ciphertextId) {
		this.ciphertextId = ciphertextId;
	}

	@Column(name="value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ciphertextId == null) ? 0 : ciphertextId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ciphertext other = (Ciphertext) obj;
		if (ciphertextId == null) {
			if (other.ciphertextId != null)
				return false;
		} else if (!ciphertextId.equals(other.ciphertextId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ciphertext [value=" + value + ", ciphertextId=" + ciphertextId
				+ "]";
	}
	
}
