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

/*
 * this class will have methods which will send emails to the Users related to Payments.
 */
public class PaymentNotify extends AbstractManager {

	public static void TriggerPaymentRelatedAutoGenEmail() throws JSONException {
		WhenCBUUserPaymentIsPending();
	}

	/*
	 * this method is for getting User Details from RestAPI by particular UserID
	 */
	private static ResponseEntity<User> getUserDetailsByUserId(int userId, String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/" + userId + "/", HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<User>() {
				});
	}

	/*
	 * this method sends email to CBU users who have not completed payment for the
	 * services.
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
