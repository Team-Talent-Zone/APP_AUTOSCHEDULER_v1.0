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
import com.src.pojo.Util;

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

	public static void main(String arg[]) throws JSONException {
		WhenUserNotLoginYet();
	}

	/*
	 * Method send email notification to the user who has not login yet after reset
	 * password.
	 */

	private static void WhenUserNotLoginYet() throws JSONException {

		ResponseEntity<ArrayList<User>> usResponseEntities = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getAllUsers/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<User>>() {
				});

		ResponseEntity<LookUpTemplate> rltemplateObject = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getLookupTemplateEntityByShortkey/"
						+ Config.EMAIL_SHORTKEY_WHENUSERNOTLOGINYET,
				HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<LookUpTemplate>() {
				});

		for (User userEntity : usResponseEntities.getBody()) {
			Util utilEntity = CreateNewUtilEntity(userEntity.getUsername(), Config.EMAIL_SUBJECT_WHENUSERNOTLOGINYET,
					rltemplateObject.getBody().getUrl().toString());

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("fullName", userEntity.getFirstname() + userEntity.getLastname());
			jsonArray.put(jsonObj);
			utilEntity.setJsonArray(jsonArray);

			HttpEntity<Util> requestHeaderWithObject = new HttpEntity<Util>(utilEntity, getHeaders());
			ResponseEntity<Util> responseEntity = restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/sendEmail/",
					HttpMethod.POST, requestHeaderWithObject, Util.class);

			if (responseEntity.getBody().isStatus() == false) {
				NotifyToAdministrator(userEntity, responseEntity);
				break;
			}
		}
	}

	private static void WhenFUProfileNotCompleted() {
	};

}
