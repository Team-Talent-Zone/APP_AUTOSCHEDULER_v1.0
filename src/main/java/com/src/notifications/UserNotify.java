package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.User;
import com.src.pojo.UserNotification;
import com.src.pojo.Util;

public class UserNotify extends AbstractManager {

	public static void TriggerUserRelatedAutoGenEmail() throws JSONException {
		WhenFUProfileNotCompleted();
		WhenUserNotLoggedInYet();
	}

	/*
	 * The method will send the email notification to CBU or FU users who has not
	 * logged-in to platform yet after resetting the password and after signup
	 * successfully .
	 */

	private static void WhenUserNotLoggedInYet() throws JSONException {

		String templateURL = null;
		int templateId;

		ResponseEntity<ArrayList<User>> usersList = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getUserByRecoveryPwd/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<User>>() {
				});

		ResponseEntity<LookUpTemplate> cbuTemplateObject = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getLookupTemplateEntityByShortkey/"
						+ Config.EMAIL_SHORTKEY_FU_WHENUSERNOTLOGINYET,
				HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<LookUpTemplate>() {
				});

		ResponseEntity<LookUpTemplate> fuTemplateObject = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getLookupTemplateEntityByShortkey/"
						+ Config.EMAIL_SHORTKEY_CBU_WHENUSERNOTLOGINYET,
				HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<LookUpTemplate>() {
				});

		if (usersList != null) {

			for (User user : usersList.getBody()) {
				try {
					if (user.getUserroles().getRolecode().equals(Config.ROLE_FREELANCER_USER)) {
						templateURL = fuTemplateObject.getBody().getUrl().toString();
						templateId = fuTemplateObject.getBody().getTemplateId();
					} else {
						templateURL = cbuTemplateObject.getBody().getUrl().toString();
						templateId = cbuTemplateObject.getBody().getTemplateId();
					}

					Util util = CreateNewUtilEntity(user.getUsername(), Config.EMAIL_SUBJECT_WHENUSERNOTLOGINYET,
							templateURL, user.getPreferlang());

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstName", user.getFirstname());
					util.setTemplatedynamicdata(jsonObj.toString());
					HttpEntity<Util> requestHeaderWithUtilObject = new HttpEntity<Util>(util, getHeaders());
					ResponseEntity<Util> utilResponse = restTemplate.exchange(
							Config.RESTSERVICE_URL_DEV + "/sendemail/", HttpMethod.POST, requestHeaderWithUtilObject,
							Util.class);

					if (utilResponse.getBody().getLastreturncode() == 250) {
						UserNotification notification = new UserNotification();
						notification.setUserId(user.getUserId());
						notification.setSentby(Config.NOTIFICATION_SENTBY);
						notification.setTemplateId(templateId);
						HttpEntity<UserNotification> requestHeaderWithUserNotificationObject = new HttpEntity<UserNotification>(
								notification, getHeaders());
						restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/saveUserNotification/", HttpMethod.POST,
								requestHeaderWithUserNotificationObject, UserNotification.class);
					}
				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(user, e.toString());
				}
			}
		}

	}

	private static void WhenFUProfileNotCompleted() {
	};

}
