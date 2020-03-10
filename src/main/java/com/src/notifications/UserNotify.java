package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.User;
import com.src.pojo.Util;

/*
 * this class will have methods which will send emails to the Users related to any User Notifications
 */
public class UserNotify extends AbstractManager {

	public static void TriggerUserRelatedAutoGenEmail() throws JSONException {
		WhenFUProfileNotCompleted();
		WhenPwdRecoveryIsNeeded();
	}

	/*
	 * The method will send the email notification to CBU or FU users who has not
	 * logged-in to platform yet after resetting the password and after signup
	 * successfully .
	 */
	private static void WhenPwdRecoveryIsNeeded() throws JSONException {

		String templateURL = null;
		int templateId;

		ResponseEntity<ArrayList<User>> usersList = getUserDetailsByAPICall(
				Config.APICALL_GETUSERDETAILSBYRECOVERYPASSWORD);
		ResponseEntity<LookUpTemplate> cbuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_FU_WHENUSERNOTLOGINYET);
		ResponseEntity<LookUpTemplate> fuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBU_WHENUSERNOTLOGINYET);

		if (usersList.getBody() != null) {
			
			ResponseEntity<ArrayList<User>> listofusers= getUserDetailsByAPICall(
					Config.APICALL_GETUSERDETAILS);
		 
			for (User user : usersList.getBody()) {
				try {
					if (user.getUserroles().getRolecode().equals(Config.ROLE_FREELANCER_USER)) {
						templateURL = fuTemplateObject.getBody().getUrl().toString();
						templateId = fuTemplateObject.getBody().getTemplateid();
					} else {
						templateURL = cbuTemplateObject.getBody().getUrl().toString();
						templateId = cbuTemplateObject.getBody().getTemplateid();
					}
					Util util = createNewUtilEntityObj(user.getUsername(), Config.EMAIL_SUBJECT_WHENUSERNOTLOGINYET,
							templateURL, user.getPreferlang());

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", user.getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("platformURL",Config.UI_URL);
					jsonObj.put("roleId",listofusers.getBody().size());
					
					util.setTemplatedynamicdata(jsonObj.toString());

					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(user.getUserId(), templateId);
					}
				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(user.getUsername(),user.getFirstname(), e.toString());
				}
			}
		}
	}

	/*
	 * sends email to FU users who have not filled the details in their profile i.e. who have incomplete profiles.
	 * @throws JSONException
	 */
	private static void WhenFUProfileNotCompleted() throws JSONException {

		ResponseEntity<ArrayList<User>> usersList = getUserDetailsByAPICall(
				Config.APICALL_GETFUUSERDETAILSWHENINCOMPLETEDPROFILE);
		ResponseEntity<LookUpTemplate> templatedetails = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_FU_PROFILENOTCOMPLETED);

		if (usersList != null) {
			for (User user : usersList.getBody()) {
				try {
					Util util = createNewUtilEntityObj(user.getUsername(), Config.EMAIL_SUBJECT_FU_PROFILENOTCOMPLETED,
							templatedetails.getBody().getUrl().toString(), user.getPreferlang());
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", user.getFirstname());		
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("platformURL",Config.UI_URL);
					util.setTemplatedynamicdata(jsonObj.toString());
					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(user.getUserId(), templatedetails.getBody().getTemplateid());
					}
				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(user.getUsername(),user.getFirstname(), e.toString());
				}
			}
		}

	}

}
