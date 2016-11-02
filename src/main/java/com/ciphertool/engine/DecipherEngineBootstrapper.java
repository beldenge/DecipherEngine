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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DecipherEngineBootstrapper {
	private static Logger				log							= LoggerFactory.getLogger(DecipherEngineBootstrapper.class);

	private static final String			SPRING_PROFILES_ACTIVE_KEY	= "spring.profiles.active";

	@SuppressWarnings("unused")
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

		Properties props = new Properties();
		props.load(new FileInputStream("src/main/resources/DecipherEngine.properties"));
		System.setProperty(SPRING_PROFILES_ACTIVE_KEY, props.getProperty(SPRING_PROFILES_ACTIVE_KEY));

		context = new ClassPathXmlApplicationContext("bootstrapContext.xml");

		log.info("Spring application context started successfully in " + (System.currentTimeMillis() - start) + "ms.");
	}
}
