package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.notifications.AbstractManager;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.User;
import com.src.pojo.UserServiceDetails;
import com.src.pojo.Util;

/**
 * This <code> PaymentNotify</code> class will have methods which will send
 * emails to the Users related to Payments.
 * 
 * @author Ishaq
 * @version 1.0
 *
 */
public class PaymentNotify extends AbstractManager {

	/**
	 * This is static method for Generating the Email Auto Scheduler Service.
	 * 
	 * @throws JSONException
	 */
	public static void TriggerPaymentRelatedAutoGenEmail() throws JSONException {
		WhenCBUUserPaymentIsPending();
	}

	/**
	 * This method is for getting User Details from RestAPI by particular UserID
	 * 
	 * @param userId
	 * @param apipath
	 */
	private static ResponseEntity<User> getUserDetailsByUserId(int userId, String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/" + userId + "/", HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<User>() {
				});
	}

	/**
	 * This method sends email to CBU users who have not completed payment for the
	 * services.
	 * 
	 * @throws JSONException
	 */
	private static void WhenCBUUserPaymentIsPending() throws JSONException {
		ResponseEntity<ArrayList<UserServiceDetails>> usersList = getUserServicePendingPayment(
				Config.APICALL_GETUSERSERVICEPENDINGPAYMENT);
		ResponseEntity<LookUpTemplate> cbuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBA_WHENPAYMENTISPENDING);

		if (usersList.getBody() != null) {
			for (UserServiceDetails userServiceDetails : usersList.getBody()) {
				ResponseEntity<User> usersDetails = getUserDetailsByUserId(userServiceDetails.getUserId(),
						Config.APICALL_GETUSERSBYUSERID);
				try {
					Util util = createNewUtilEntityObj(usersDetails.getBody().getUsername(),
							Config.EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED,
							cbuTemplateObject.getBody().getUrl().toString(), usersDetails.getBody().getPreferlang());
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", usersDetails.getBody().getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("platformURL", Config.UI_URL);
					util.setTemplatedynamicdata(jsonObj.toString());
					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(usersDetails.getBody().getUserId(),
								cbuTemplateObject.getBody().getTemplateid());
					}

				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(usersDetails.getBody().getUsername(),
							usersDetails.getBody().getFirstname(), e.toString());
				}
			}
		}
	}
}
