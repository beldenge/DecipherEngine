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
import org.springframework.stereotype.Component;

import com.ciphertool.engine.entities.Cipher;

@Component
public class CipherDao {
	private static Logger log = LoggerFactory.getLogger(CipherDao.class);

	private MongoOperations mongoOperations;

	public Cipher findByCipherName(String name) {
		if (name == null || name.isEmpty()) {
			log.warn("Attempted to find cipher with null or empty name.  Returning null.");

			return null;
		}

		Query selectByNameQuery = new Query(Criteria.where("name").is(name));

		Cipher cipher = mongoOperations.findOne(selectByNameQuery, Cipher.class);

		return cipher;
	}

	public List<Cipher> findAll() {
		List<Cipher> ciphers = mongoOperations.findAll(Cipher.class);

		return ciphers;
	}

	@Required
	public void setMongoTemplate(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
