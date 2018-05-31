package org.bcos.browser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动服务
 *
 */
public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Throwable {
		logger.debug("初始化GovernService");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		GovernService cs = context.getBean(GovernService.class);
//		cs.start();
	}
}
