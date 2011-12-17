package com.ciphertool.zodiacengine.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ciphertool.zodiacengine.entities.Solution;

@Component
public class SolutionDao implements Dao {
	private SessionFactory sessionFactory;
	
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
		Solution solution = (Solution) session.createQuery( "from Solution where id = ?" ).setInteger(0, id).uniqueResult();
	    session.getTransaction().commit();
	    session.close();
	    
		return solution;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
