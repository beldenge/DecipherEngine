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
