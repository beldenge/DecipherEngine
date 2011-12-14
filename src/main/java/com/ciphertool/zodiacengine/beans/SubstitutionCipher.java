package com.ciphertool.zodiacengine.beans;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.enumerations.SubstitutionType;

public class SubstitutionCipher extends Cipher {
	protected SubstitutionType type;
	
	public SubstitutionCipher () {}
	
	public SubstitutionType getType() {
		return type;
	}
	
	public void setType(SubstitutionType type) {
		this.type = type;
	}

}
