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
