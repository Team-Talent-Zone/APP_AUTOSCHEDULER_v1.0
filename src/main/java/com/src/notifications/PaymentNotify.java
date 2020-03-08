package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.notifications.AbstractManager;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.UserServiceDetails;
import com.src.pojo.Util;

public class PaymentNotify extends AbstractManager {

	public static void TriggerPaymentRelatedAutoGenEmail() throws JSONException {
		WhenCBUUserPaymentIsPending();
	}

	private static void WhenCBUUserPaymentIsPending() throws JSONException {
		ResponseEntity<ArrayList<UserServiceDetails>> usersList = getUserServicePendingPayment(
				Config.APICALL_GETUSERSERVICEPENDINGPAYMENT);
		ResponseEntity<LookUpTemplate> cbuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBU_WHENUSERNOTLOGINYET);

		if (usersList != null) {
			for (UserServiceDetails user : usersList.getBody()) {
				try {
					
					Util util = createNewUtilEntityObj(user.getUserService().getUsername(),
							Config.EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED,
							cbuTemplateObject.getBody().getUrl().toString(), user.getUserService().getPreferlang());
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", user.getUserService().getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("platformURL", Config.UI_URL);
					util.setTemplatedynamicdata(jsonObj.toString());
					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(user.getUserId(), cbuTemplateObject.getBody().getTemplateid());
					}
				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(user, e.toString());
				}
			}
		} 
	}

}
