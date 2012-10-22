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

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.entities.SolutionId;

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

	public Solution findBySolutionId(SolutionId solutionId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Solution solution = (Solution) session.createQuery(
				"from Solution where id = " + separator + solutionIdParameter).setParameter(
				solutionIdParameter, solutionId).uniqueResult();
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

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
