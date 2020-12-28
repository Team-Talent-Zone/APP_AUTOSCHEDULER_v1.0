package com.src.scheduler;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.src.notifications.PaymentNotify;
//import com.src.notifications.PaymentNotify;
import com.src.notifications.UserNotify;
import com.src.notifications.UserServiceNotify;

/**
 * The <code> MainScheduler </code> class defines to Run AutoScheduler
 * functionality .
 * 
 * @author Ishaq
 * @version 1.0
 *
 */
public class MainScheduler {
	final Logger logger = LoggerFactory.getLogger(MainScheduler.class);

	/**
	 * This is a method which will be automatically runned according to give time
	 * mentioned in task-Scheduler(from scheduler.xml).
	 */
	public void autoRun() {
		try {
			//UserNotify.TriggerUserRelatedAutoGenEmail();
			PaymentNotify.TriggerPaymentRelatedAutoGenEmail();
			//UserServiceNotify.TriggerUserServiceRelatedAutoGenEmail();
			// NewServiceNotify.TriggerNewServiceRelatedAutoGenEmail();
		} catch (JSONException e) {
			logger.error("Inside MainScheduler Class : autoRun Method : Exception Error Occur :" + e.toString());
		}
	}
}
