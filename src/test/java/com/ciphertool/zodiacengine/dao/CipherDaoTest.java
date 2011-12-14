package com.ciphertool.zodiacengine.dao;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Cipher;

public class CipherDaoTest {
	
	private static Logger log = Logger.getLogger(CipherDaoTest.class);
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testFindByCipherName() {
		CipherDao cipherDao = (CipherDao) factory.getBean("cipherDao");
		Cipher cipher = cipherDao.findByCipherName("zodiac340");
		log.info(cipher);
	}
}
