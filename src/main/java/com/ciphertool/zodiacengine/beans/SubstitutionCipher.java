package com.ciphertool.zodiacengine.beans;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.enumerations.SubstitutionType;

public class SubstitutionCipher extends Cipher {
	private static final long serialVersionUID = 5227121564551865387L;

	protected SubstitutionType type;

	public SubstitutionCipher() {
	}

	public SubstitutionType getType() {
		return type;
	}

	public void setType(SubstitutionType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubstitutionCipher other = (SubstitutionCipher) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
