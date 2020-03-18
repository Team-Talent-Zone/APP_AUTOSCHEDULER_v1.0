package com.src.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The <code> Start </code> class defines to run Spring Bean Context for
 * <code>AutoScheduler</code>.
 * 
 * @author Ishaq
 * @version 1.0
 *
 */
public class Start {
	final static Logger logger = LoggerFactory.getLogger(Start.class);

	/**
	 * This is main method to run Spring Bean Context For AutoScheduler.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String args[]) {
		DateFormat dateFormatPrint = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatPrint.setTimeZone(TimeZone.getTimeZone("IST"));
		logger.debug("=======Inside MainScheduler Class : autoRun Method : Start At : ========"
				+ dateFormatPrint.format(new Date()));

		@SuppressWarnings("unused")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("scheduler.xml");

	}
}
