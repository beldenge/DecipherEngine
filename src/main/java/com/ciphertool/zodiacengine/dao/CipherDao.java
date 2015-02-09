/**
 * Copyright 2015 George Belden
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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ciphertool.zodiacengine.entities.Cipher;

@Component
public class CipherDao {
	private static Logger log = Logger.getLogger(CipherDao.class);

	private MongoOperations mongoOperations;

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Cipher findByCipherName(String name) {
		if (name == null || name.isEmpty()) {
			log.warn("Attempted to find cipher with null or empty name.  Returning null.");

			return null;
		}

		Query selectByNameQuery = new Query(Criteria.where("name").is(name));

		Cipher cipher = mongoOperations.findOne(selectByNameQuery, Cipher.class);

		return cipher;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<Cipher> findAll() {
		List<Cipher> ciphers = mongoOperations.findAll(Cipher.class);

		return ciphers;
	}

	@Required
	public void setMongoTemplate(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}
}
