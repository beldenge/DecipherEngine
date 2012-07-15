package com.ciphertool.zodiacengine.singleton;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;

/**
 * This is a faux Singleton class, intended to be instantiated as a Spring
 * Singleton and not a true Java Singleton.
 * 
 * @author george
 */
public class CipherFauxSingleton implements CipherSingleton {
	private static String cipherName;
	private static CipherDao cipherDao;

	public CipherFauxSingleton(String cipherName, CipherDao cipherDao) {
		CipherFauxSingleton.cipherName = cipherName;
		CipherFauxSingleton.cipherDao = cipherDao;
	}

	private static class CipherHolder {
		public static Cipher cipher = cipherDao.findByCipherName(cipherName);
	}

	public Cipher getInstance() {
		return CipherHolder.cipher;
	}
}
