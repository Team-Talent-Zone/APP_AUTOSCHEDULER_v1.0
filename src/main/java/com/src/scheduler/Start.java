package com.src.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {

	final static Logger logger = LoggerFactory.getLogger(Start.class);

	@SuppressWarnings({"unused", "resource"})
	public static void main(String args[]) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"scheduler.xml");
	}
}
