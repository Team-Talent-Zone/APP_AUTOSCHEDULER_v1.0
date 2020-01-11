package com.src.scheduler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.src.notifications.NewServiceNotify;
import com.src.notifications.PaymentNotify;
import com.src.notifications.UserNotify;
import com.src.notifications.UserServiceNotify;

public class MainScheduler {
	final Logger logger = LoggerFactory.getLogger(MainScheduler.class);
	protected DateFormat dateFormatPrint = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void autoRun() throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		try {
			dateFormatPrint.setTimeZone(TimeZone.getTimeZone("IST"));
			logger.debug("=======Inside MainScheduler Class : autoRun Method : Start At : ========"
					+ dateFormatPrint.format(new Date()));

			/*
			 * Scheduling send email notifications automatically on multiple business
			 * functionalities.
			 */

			NewServiceNotify.triggerNewServiceRelatedAutoGenEmail();
			PaymentNotify.triggerPaymentRelatedAutoGenEmail();
			UserNotify.TriggerUserRelatedAutoGenEmail();
			UserServiceNotify.triggerUserServiceRelatedAutoGenEmail();

		} catch (Exception e) {
			logger.error("Inside MainScheduler Class : autoRun Method : Exception Error Occur :" + e.toString());
		}
	}
}
