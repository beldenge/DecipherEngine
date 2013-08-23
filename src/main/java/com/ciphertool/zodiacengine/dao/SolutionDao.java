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

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class SolutionDao {
	private MongoOperations mongoOperations;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean insert(SolutionChromosome solutionChromosome) {
		mongoOperations.insert(solutionChromosome);

		return true;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public SolutionChromosome findBySolutionId(Integer solutionId) {
		Query selectByIdQuery = new Query(Criteria.where("id").is(solutionId));

		SolutionChromosome solutionChromosome = mongoOperations.findOne(selectByIdQuery,
				SolutionChromosome.class);

		return solutionChromosome;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<SolutionChromosome> findByCipherName(String cipherName) {
		// First find the Cipher by name
		Query selectByCipherNameQuery = new Query(Criteria.where("name").is(cipherName));

		Cipher cipher = mongoOperations.findOne(selectByCipherNameQuery, Cipher.class);

		// Then find the Solutions that correspond to this Cipher
		Query selectByCipherIdQuery = new Query(Criteria.where("cipherId").is(cipher.getId()));

		List<SolutionChromosome> solutions = mongoOperations.find(selectByCipherIdQuery,
				SolutionChromosome.class);

		return solutions;
	}

	@Required
	public void setMongoTemplate(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
