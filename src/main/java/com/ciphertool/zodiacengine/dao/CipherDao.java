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

package com.ciphertool.zodiacengine.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.ciphertool.zodiacengine.entities.Cipher;

@Component
public class CipherDao implements Dao {
	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String nameParameter = "name";

	public Cipher findByCipherName(String name) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Cipher cipher = (Cipher) session.createQuery(
				"from Cipher where name = " + separator + nameParameter).setParameter(
				nameParameter, name).uniqueResult();
		session.getTransaction().commit();
		session.close();

		return cipher;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
