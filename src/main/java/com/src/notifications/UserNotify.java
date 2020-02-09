package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONArray;
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
	 * Below method will send the email notification to the users who has not
	 * logged-in yet after reset the password.
	 */

	private static void WhenUserNotLoggedInYet() throws JSONException {

		ResponseEntity<ArrayList<User>> listofUsersNotLoggedIn = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getUserByRecoveryPwd/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<User>>() {
				});

		if (listofUsersNotLoggedIn != null) {

			ResponseEntity<LookUpTemplate> lookUpTemplate = restTemplate.exchange(
					Config.RESTSERVICE_URL_DEV + "/getLookupTemplateEntityByShortkey/"
							+ Config.EMAIL_SHORTKEY_WHENUSERNOTLOGINYET,
					HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<LookUpTemplate>() {
					});

			for (User userEntity : listofUsersNotLoggedIn.getBody()) {
				Util utilEntity = CreateNewUtilEntity(userEntity.getUsername(),
						Config.EMAIL_SUBJECT_WHENUSERNOTLOGINYET, lookUpTemplate.getBody().getUrl().toString(),
						userEntity.getPreferlang());

				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("fullName", userEntity.getFirstname() + userEntity.getLastname());
				jsonArray.put(jsonObj);
				utilEntity.setJsonarray(jsonArray);

				HttpEntity<Util> requestHeaderWithUtilObject = new HttpEntity<Util>(utilEntity, getHeaders());
				ResponseEntity<Util> utilResponseEntity = restTemplate.exchange(
						Config.RESTSERVICE_URL_DEV + "/sendemail/", HttpMethod.POST, requestHeaderWithUtilObject,
						Util.class);
				if (utilResponseEntity.getBody().getLastreturncode() == 250) {
					UserNotification notification = new UserNotification();
					notification.setUserId(userEntity.getUserId());
					notification.setSentby(Config.NOTIFICATION_SENTBY);
					notification.setTemplateId(lookUpTemplate.getBody().getTemplateId());
					HttpEntity<UserNotification> requestHeaderWithUserNotificationObject = new HttpEntity<UserNotification>(
							notification, getHeaders());
					restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/saveUserNotification/", HttpMethod.POST,
							requestHeaderWithUserNotificationObject, UserNotification.class);
				} else {
					NotifyToCSSTAdmin(userEntity, utilResponseEntity);
				}
			}
		}
	}

	private static void WhenFUProfileNotCompleted() {
	};

}
