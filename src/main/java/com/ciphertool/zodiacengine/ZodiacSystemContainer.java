package com.ciphertool.zodiacengine;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ZodiacSystemContainer {
	private static Logger log = Logger.getLogger(ZodiacSystemContainer.class);

	@SuppressWarnings("unused")
	private static ApplicationContext context;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		initializeContext();
	}

	/**
	 * Spins up the Spring application context
	 */
	protected static void initializeContext() {
		log.info("Starting Spring application context");

		context = new ClassPathXmlApplicationContext("beans-genetic.xml");
	}
}
