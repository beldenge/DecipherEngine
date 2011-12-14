package com.ciphertool.zodiacengine.dao;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class PlaintextDaoTest {

	private static Logger log = Logger.getLogger(PlaintextDaoTest.class);
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testFindByPlaintextId() {
		PlaintextDao plaintextDao = (PlaintextDao) factory.getBean("plaintextDao");
		Solution solution = new Solution(1, 0, 0);
		solution.setId(26); //provided a solution with this id exists
		PlaintextId plaintextId = new PlaintextId(solution, 5);
		Plaintext plaintext = plaintextDao.findByPlaintextId(plaintextId);
		log.info(plaintext);
	}
}
