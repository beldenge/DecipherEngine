package com.ciphertool.zodiacengine.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.entities.SolutionSet;

public class SolutionSetDao implements Dao {
	private SessionFactory sessionFactory;

	public boolean insert(SolutionSet solutionSet) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(solutionSet);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
