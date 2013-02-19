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

package com.ciphertool.zodiacengine.genetic;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.algorithms.GeneticAlgorithm;

public class GeneticCipherSolutionEngine {
	private static Logger log = Logger.getLogger(GeneticCipherSolutionEngine.class);
	@SuppressWarnings("unused")
	private static ApplicationContext context;
	private static GeneticAlgorithm geneticAlgorithm;
	private static String[] commandsBefore;
	private static String[] commandsAfter;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Spin up the Spring application context
		setUp();

		long start = System.currentTimeMillis();

		geneticAlgorithm.evolve();

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to finish.");

		geneticAlgorithm.getPopulation().printAscending();

		tearDown();
	}

	/**
	 * Spins up the Spring application context
	 */
	private static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");

		log.info("Spring context created successfully!");

		/*
		 * currentCommand is really just used for exception catching.
		 */
		String currentCommand = "";

		try {
			for (String commandBefore : commandsBefore) {
				log.info("Executing shell command on start up: " + commandBefore);
				currentCommand = commandBefore;

				Runtime.getRuntime().exec(commandBefore);
			}
		} catch (IOException e) {
			log.warn("Unable to execute commmand before app begin: " + currentCommand
					+ ".  Continuing.");
		}
	}

	private static void tearDown() {
		/*
		 * currentCommand is really just used for exception catching.
		 */
		String currentCommand = "";

		try {
			for (String commandAfter : commandsAfter) {
				log.info("Executing shell command on termination: " + commandAfter);
				currentCommand = commandAfter;

				Runtime.getRuntime().exec(commandAfter);
			}
		} catch (IOException e) {
			log.warn("Unable to execute commmand after app end: " + currentCommand
					+ ".  Continuing.");
		}
	}

	/**
	 * @param geneticAlgorithm
	 *            the geneticAlgorithm to set
	 */
	@Required
	public void setGeneticAlgorithm(GeneticAlgorithm geneticAlgorithm) {
		GeneticCipherSolutionEngine.geneticAlgorithm = geneticAlgorithm;
	}

	/**
	 * @param commandsBefore
	 *            the commandsBefore to set
	 */
	@Required
	public static void setCommandsBefore(String[] commandsBefore) {
		GeneticCipherSolutionEngine.commandsBefore = commandsBefore;
	}

	/**
	 * @param commandsAfter
	 *            the commandsAfter to set
	 */
	@Required
	public static void setCommandsAfter(String[] commandsAfter) {
		GeneticCipherSolutionEngine.commandsAfter = commandsAfter;
	}
}
