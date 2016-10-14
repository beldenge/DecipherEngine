/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.genetics.entities.Chromosome;

public class SolutionDao {
	private static Logger log = LoggerFactory.getLogger(SolutionDao.class);

	private MongoOperations mongoOperations;

	public boolean insert(Chromosome chromosome) {
		if (chromosome == null) {
			log.warn("Attempted to insert null solution.  Returning.");

			return false;
		}

		mongoOperations.insert(chromosome);

		return true;
	}

	public Chromosome findBySolutionId(Integer solutionId) {
		if (solutionId == null) {
			log.warn("Attempted to find solution by null ID.  Returning null.");

			return null;
		}

		Query selectByIdQuery = new Query(Criteria.where("id").is(solutionId));

		Chromosome chromosome = mongoOperations.findOne(selectByIdQuery, Chromosome.class);

		return chromosome;
	}

	public List<Chromosome> findByCipherName(String cipherName) {
		if (cipherName == null || cipherName.isEmpty()) {
			log.warn("Attempted to find solution by cipher name with null or empty cipherName.  Returning null.");

			return null;
		}

		// First find the Cipher by name
		Query selectByCipherNameQuery = new Query(Criteria.where("name").is(cipherName));

		Cipher cipher = mongoOperations.findOne(selectByCipherNameQuery, Cipher.class);

		// Then find the Solutions that correspond to this Cipher
		Query selectByCipherIdQuery = new Query(Criteria.where("cipherId").is(cipher.getId()));

		List<Chromosome> solutions = mongoOperations.find(selectByCipherIdQuery, Chromosome.class);

		return solutions;
	}

	@Required
	public void setMongoTemplate(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
