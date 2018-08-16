package com.meicai.demo;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;


public class BoneLauncher {

	private static final Logger logger = Logger.getLogger(BoneLauncher.class);


	public static void main(String[] args) {
    	try {
			new ClassPathXmlApplicationContext("spring/spring-main.xml");
			logger.info("BoneLauncher started successfully!");
			CountDownLatch latch = new CountDownLatch(1);
			latch.await();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
    }
}
