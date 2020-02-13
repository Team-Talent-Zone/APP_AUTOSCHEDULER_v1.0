package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.User;
import com.src.pojo.Util;

public class NewServiceNotify extends AbstractManager {

	public static void TriggerNewServiceRelatedAutoGenEmail() throws JSONException {
		WhenNewServiceIsOnboardedOnPlatform();
	}

	private static void WhenNewServiceIsOnboardedOnPlatform() throws JSONException {

		/*
		 * Replace with the api url path
		 */
		ResponseEntity<ArrayList<User>> usersList = getUserDetailsByAPICall(
				Config.APICALL_GETCBUUSERDETAILSFORNEWSERVICECREATED);

		ResponseEntity<LookUpTemplate> templatedetails = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBU_WHENNEWSERVICE_CREATED);

		if (usersList != null) {
			for (User user : usersList.getBody()) {
				try {
					Util util = createNewUtilEntityObj(user.getUsername(), Config.EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED,
							templatedetails.getBody().getUrl().toString(), user.getPreferlang());
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstName", user.getFirstname());
					util.setTemplatedynamicdata(jsonObj.toString());
					sendEmail(util);
				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(user, e.toString());
				}
			}
		}
	}

}
