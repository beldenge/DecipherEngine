package com.ciphertool.zodiacengine.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import com.ciphertool.zodiacengine.entities.Cipher;

@Component
public class CipherDao implements Dao {
	private SessionFactory sessionFactory;
	
	public Cipher findByCipherName(String name) {
		Session session = sessionFactory.openSession();
	    session.beginTransaction();
		Cipher cipher = (Cipher) session.createQuery( "from Cipher where name = ?" ).setString(0, name).uniqueResult();
	    session.getTransaction().commit();
	    session.close();
	    
		return cipher;
	}
	
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
