package com.ciphertool.zodiacengine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.entities.Ciphertext;

public class CiphertextDao implements Dao {
	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String cipherIdParameter = "cipherId";

	public List<Ciphertext> findAllByCipherId(int cipherId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Ciphertext> cipherCharacters = session.createQuery(
				"from Ciphertext where cipher_id = " + separator + cipherIdParameter).setParameter(
				cipherIdParameter, cipherId).list();
		session.getTransaction().commit();
		session.close();

		return cipherCharacters;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
