package com.ciphertool.zodiacengine.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ciphertool.zodiacengine.entities.Solution;

@Component
public class SolutionDao implements Dao {
	private SessionFactory sessionFactory;
	private static final String separator = ":";
	private static final String solutionIdParameter = "solutionId";
	private static final String nameParameter = "name";

	public boolean insert(Solution solution) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(solution);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	public Solution findById(Integer id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Solution solution = (Solution) session.createQuery(
				"from Solution where id = " + separator + solutionIdParameter).setParameter(
				solutionIdParameter, id).uniqueResult();
		session.getTransaction().commit();
		session.close();

		return solution;
	}

	public List<Solution> findByCipherName(String cipherName) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Solution> solutions = (List<Solution>) session.createQuery(
				"from Solution where cipherId = (select c.id from Cipher c where c.name = "
						+ separator + nameParameter + ")").setParameter(nameParameter, cipherName)
				.list();
		session.getTransaction().commit();
		session.close();

		return solutions;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
