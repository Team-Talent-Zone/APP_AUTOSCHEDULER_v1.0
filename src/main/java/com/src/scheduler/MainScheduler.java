package com.src.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.src.notifications.NewServiceNotify;
import com.src.notifications.PaymentNotify;
import com.src.notifications.UserNotify;
import com.src.notifications.UserServiceNotify;

public class MainScheduler {

	final Logger logger = LoggerFactory.getLogger(MainScheduler.class);

	public void autoRun() {
		try {

			UserNotify.TriggerUserRelatedAutoGenEmail();
			NewServiceNotify.TriggerNewServiceRelatedAutoGenEmail();
			PaymentNotify.TriggerPaymentRelatedAutoGenEmail();
			UserServiceNotify.TriggerUserServiceRelatedAutoGenEmail();

		} catch (Exception e) {
			logger.error("Inside MainScheduler Class : autoRun Method : Exception Error Occur :" + e.toString());
		}
	}
}
