package com.ciphertool.zodiacengine.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.entities.Ciphertext;

public class CiphertextDaoTest {
	
	private static Logger log = Logger.getLogger(CiphertextDaoTest.class);
	private static BeanFactory factory;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-test.xml");
		factory = context;
		log.info("Spring context created successfully!");
	}
	
	@Test
	public void testFindAllByCipherId() {
		CiphertextDao ciphertextDao = (CiphertextDao) factory.getBean("ciphertextDao");
		List<Ciphertext> cipherCharacters = ciphertextDao.findAllByCipherId(1);
		for (Ciphertext ct : cipherCharacters)
			log.info(ct);
	}
}
