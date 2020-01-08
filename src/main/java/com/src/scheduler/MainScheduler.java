package com.src.scheduler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MainScheduler extends AutoSendEmailsBO {
	final Logger logger = LoggerFactory.getLogger(MainScheduler.class);

	public void autoRun() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		try {
			dateFormatPrint.setTimeZone(TimeZone.getTimeZone("EST"));
			logger.debug("=======Inside MainScheduler Class : autoRun Method : Start At : ========"
					+ dateFormatPrint.format(new Date()));

			/*
			 * Write the logic which send email notifications automatically to clients as
			 * per the scheduled job and business funcationilty.
			 */

		} catch (Exception e) {
			logger.error("Inside MainScheduler Class : autoRun Method : Exception Error Occur :" + e.toString());
		}
	}
}
