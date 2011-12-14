package com.ciphertool.zodiacengine.entities;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="plaintext")
@AssociationOverrides(
		@AssociationOverride(name = "plaintextId.solution", joinColumns = @JoinColumn(name = "solution_id"))
)
public class Plaintext {
	private PlaintextId plaintextId;
	private String value;
	private boolean locked;
	
	public Plaintext() {}
	
	public Plaintext(PlaintextId plaintextId, String value) {
		this.plaintextId=plaintextId;
		this.value=value;
	}

	@EmbeddedId
	public PlaintextId getPlaintextId() {
		return plaintextId;
	}

	public void setPlaintextId(PlaintextId plaintextId) {
		this.plaintextId = plaintextId;
	}

	@Column(name="value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name="locked")
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (locked ? 1231 : 1237);
		result = prime * result
				+ ((plaintextId == null) ? 0 : plaintextId.hashCode());
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
		Plaintext other = (Plaintext) obj;
		if (locked != other.locked)
			return false;
		if (plaintextId == null) {
			if (other.plaintextId != null)
				return false;
		} else if (!plaintextId.equals(other.plaintextId))
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
		return "Plaintext [plaintextId=" + plaintextId + ", value=" + value
				+ ", locked=" + locked + "]";
	}	
}