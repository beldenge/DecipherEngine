package com.ciphertool.zodiacengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Solution;

public class SolutionDaoTest {
	private static Logger log = Logger.getLogger(PlaintextDaoTest.class);
	private static ApplicationContext context;
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	/**
	 * This assumes that the id used below actually exists in the database
	 */
	@Test
	public void testFindBySolutionId() {
		SolutionDao solutionDao = (SolutionDao) factory.getBean("solutionDao");
		Solution solution = solutionDao.findById(26);
		assertNotNull(solution);
		assertEquals(solution.getId(), 26);
		assertEquals(solution.getCipherId(), 1);
	}
	
	/**
	 * Without setting these to null, the humongous wordMap will not be garbage collected and subsequent unit tests may encounter an out of memory exception
	 */
	@AfterClass
	public static void cleanUp() {
		context = null;
		factory = null;
	}
}
