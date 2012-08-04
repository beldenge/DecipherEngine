package com.ciphertool.zodiacengine.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;

@Component
public class PlaintextDao implements Dao {
	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String ciphertextIdParameter = "ciphertextId";
	private static final String solutionParameter = "solution";

	public boolean insert(Plaintext plaintext) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(plaintext);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public Plaintext findByPlaintextId(PlaintextId plaintextId) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Plaintext plaintext = (Plaintext) session.createQuery(
				"from Plaintext p where p.plaintextId.ciphertextId = " + separator
						+ ciphertextIdParameter + " and p.plaintextId.solution = " + separator
						+ solutionParameter).setParameter(ciphertextIdParameter,
				plaintextId.getCiphertextId()).setParameter(solutionParameter,
				plaintextId.getSolution()).uniqueResult();
		session.getTransaction().commit();
		session.close();

		return plaintext;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
