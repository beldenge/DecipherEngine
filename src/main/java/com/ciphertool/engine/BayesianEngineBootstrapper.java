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

package com.ciphertool.engine;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.engine.bayes.BayesianDecipherManager;

public class BayesianEngineBootstrapper {
	private static Logger				log							= LoggerFactory.getLogger(BayesianEngineBootstrapper.class);

	private static final String			SPRING_PROFILES_ACTIVE_KEY	= "spring.profiles.active";
	private static final String			SPRING_PROFILE				= "bayesian";

	private static ApplicationContext	context;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		initializeContext();
	}

	/**
	 * Spins up the Spring application context
	 * 
	 * @throws IOException
	 *             if the properties file cannot be read
	 * @throws FileNotFoundException
	 *             if the properties file does not exist
	 */
	protected static void initializeContext() throws FileNotFoundException, IOException {
		log.info("Starting Spring application context");

		long start = System.currentTimeMillis();

		System.setProperty(SPRING_PROFILES_ACTIVE_KEY, SPRING_PROFILE);

		context = new ClassPathXmlApplicationContext("bootstrapContext.xml");

		log.info("Spring application context started successfully in " + (System.currentTimeMillis() - start) + "ms.");

		BayesianDecipherManager manager = context.getBean(BayesianDecipherManager.class);
		manager.run();

		// Apparently this is required to cleanup after Ehcache, but I'm not entirely sure.
		((ConfigurableApplicationContext) context).close();
	}
}