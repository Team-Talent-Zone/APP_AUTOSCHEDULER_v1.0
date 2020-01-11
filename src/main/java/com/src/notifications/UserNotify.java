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
import com.src.entity.UserEntity;
import com.src.entity.UtilEntity;

public class UserNotify extends AbstractManager {

	public static void TriggerUserRelatedAutoGenEmail() {
		try {
			WhenFUProfileNotCompleted();
			WhenUserNotLoginYet();
		} catch (JSONException jsonException) {
			loggerAbstract.error(
					"UserNotify : triggerUserRelatedAutoGenEmail method : JSONException " + jsonException.toString());
		}
	}

	/*
	 * Method send email notification to the user who has not login yet after reset
	 * password.
	 */

	private static void WhenUserNotLoginYet() throws JSONException {

		ResponseEntity<ArrayList<UserEntity>> usResponseEntities = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getAllUsers/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<UserEntity>>() {
				});

		for (UserEntity userEntity : usResponseEntities.getBody()) {
			UtilEntity utilEntity = CreateNewUtilEntity(userEntity.getUsername(),
					Config.EMAIL_SUBJECT_WHENUSERNOTLOGINYET, Config.EMAIL_KEY_WHENUSERNOTLOGINYET);

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("fullName", userEntity.getFirstname() + userEntity.getLastname());
			jsonArray.put(jsonObj);
			utilEntity.setJsonArray(jsonArray);
			HttpEntity<UtilEntity> requestHeaderWithObject = new HttpEntity<UtilEntity>(utilEntity, getHeaders());

			ResponseEntity<UtilEntity> responseEntity = restTemplate.exchange(
					Config.RESTSERVICE_URL_DEV + "/sendEmail/", HttpMethod.GET, requestHeaderWithObject,
					UtilEntity.class);

			if (responseEntity.getBody().isStatus() == false) {
				NotifyToAdministrator(userEntity, responseEntity);
				break;
			}
		}
	}

	private static void WhenFUProfileNotCompleted() {
	};

}
