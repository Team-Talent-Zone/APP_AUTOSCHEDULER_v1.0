package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.NewService;
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

		ResponseEntity<ArrayList<NewService>> newservicelist = restTemplate.exchange(
				Config.REST_URL + "/" + Config.APICALL_GETNEWSERVICEDETAILSCREATED, HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<ArrayList<NewService>>() {
				});
		if (newservicelist != null) {
			ResponseEntity<ArrayList<User>> usersCBUList = restTemplate.exchange(
					Config.REST_URL + "/" + Config.APICALL_GETUSERSBYROLE + "/" + Config.CLIENT_BUSINESS_ADMINISTRATOR,
					HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<ArrayList<User>>() {
					});

			ResponseEntity<LookUpTemplate> templatedetails = getTemplateDetailsByShortKey(
					Config.EMAIL_SHORTKEY_CBU_WHENNEWSERVICE_CREATED);

			if (usersCBUList != null) {
				for (User user : usersCBUList.getBody()) {
					try {
						Util util = createNewUtilEntityObj(user.getUsername(),
								Config.EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED,
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
}
