package com.ciphertool.zodiacengine.dao;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Cipher;

public class CipherDaoTest {
	
	private static Logger log = Logger.getLogger(CipherDaoTest.class);
	private static ApplicationContext context;
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testFindByCipherName() {
		CipherDao cipherDao = (CipherDao) factory.getBean("cipherDao");
		Cipher cipher = cipherDao.findByCipherName("zodiac340");
		log.info(cipher);
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
