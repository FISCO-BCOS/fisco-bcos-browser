package org.bcos.browser.bcos.browser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * start the service
 *
 */
public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Throwable {
		logger.debug("initialize GovernService");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	}
}
