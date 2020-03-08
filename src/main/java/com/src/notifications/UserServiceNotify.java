package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.UserServiceExpirationDetails;
import com.src.pojo.Util;

public class UserServiceNotify extends AbstractManager{
	
	public static void TriggerUserServiceRelatedAutoGenEmail() throws JSONException {
		// Rest API URL -> getUserServiceExpirationDetails & getFUOnServiceExpirationDetails
		WhenCBAUserServiceGettingExpired();
		WhenFUUserServiceGettingExpired();
	}
	
	/**
	 * This method sends the mail for CBA users whose services is getting expired.
	 * @throws JSONException
	 */
	private static void WhenCBAUserServiceGettingExpired() throws JSONException {
		ResponseEntity<ArrayList<UserServiceExpirationDetails>> usersList = getServiceDetailsByAPICall(
				Config.APICALL_GETUSERSERVICEEXPIRATIONDETAILS);
		ResponseEntity<LookUpTemplate> cbuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBA_WHENPAYMENTISPENDING);

		if (usersList != null) {
			for (UserServiceExpirationDetails user : usersList.getBody()) {
				try {
					Util util = createNewUtilEntityObj(user.getUsername(),
							Config.EMAIL_SUBJECT_CBU_WHENSERVICEISGETTINGEXPIRED,
							cbuTemplateObject.getBody().getUrl().toString(), user.getPreferlang());

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", user.getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("servicepackname",user.getName());
					jsonObj.put("platformURL",Config.UI_URL);

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
	
	private static void WhenFUUserServiceGettingExpired() throws JSONException {
		ResponseEntity<ArrayList<UserServiceExpirationDetails>> usersList = getServiceDetailsByAPICall(
				Config.APICALL_GETFUUSERDETAILSWHENINCOMPLETEPROFILE);
		ResponseEntity<LookUpTemplate> fuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_FU_WHENUSERJOBISGETTINGEXPIRED);

		if (usersList != null) {
			for (UserServiceExpirationDetails user : usersList.getBody()) {
				try {
					Util util = createNewUtilEntityObj(user.getUsername(),
							Config.EMAIL_SUBJECT_FU_WHENJOBISGETTINGEXPIRED,
							fuTemplateObject.getBody().getUrl().toString(), user.getPreferlang());

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", user.getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("servicepackname",user.getName());
					jsonObj.put("platformURL",Config.UI_URL);

					util.setTemplatedynamicdata(jsonObj.toString());
					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(user.getUserId(), fuTemplateObject.getBody().getTemplateid());
					}
				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(user, e.toString());
				}
			}
		}
	}

}
