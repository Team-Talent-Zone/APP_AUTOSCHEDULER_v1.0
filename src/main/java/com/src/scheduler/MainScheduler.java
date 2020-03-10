package com.src.scheduler;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.src.notifications.NewServiceNotify;
import com.src.notifications.PaymentNotify;
//import com.src.notifications.PaymentNotify;
import com.src.notifications.UserNotify;
import com.src.notifications.UserServiceNotify;

public class MainScheduler {

	final Logger logger = LoggerFactory.getLogger(MainScheduler.class);

	/*
	 * this is a method which will be automatically runned according to give time mentioned in task-Scheduler(from scheduler.xml).	
	 */
	public void autoRun() {
		try {

			UserNotify.TriggerUserRelatedAutoGenEmail();
		    NewServiceNotify.TriggerNewServiceRelatedAutoGenEmail();
			PaymentNotify.TriggerPaymentRelatedAutoGenEmail();
			UserServiceNotify.TriggerUserServiceRelatedAutoGenEmail();

		} catch (JSONException e) {
			logger.error("Inside MainScheduler Class : autoRun Method : Exception Error Occur :" + e.toString());
		}
	}
}
