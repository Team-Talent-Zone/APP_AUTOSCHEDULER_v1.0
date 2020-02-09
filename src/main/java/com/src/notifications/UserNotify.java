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
			WhenUserNotLoggedInYet();
		} catch (JSONException jsonException) {
			loggerAbstract.error(
					"UserNotify : triggerUserRelatedAutoGenEmail method : JSONException " + jsonException.toString());
		}
	}

	/*
	 * Below method will send the email notification to the users who has not
	 * logged-in yet after reset the password.
	 */

	private static void WhenUserNotLoggedInYet() throws JSONException {

		ResponseEntity<ArrayList<User>> listofUsersNotLoggedIn = restTemplate.exchange(
				Config.RESTSERVICE_URL_DEV + "/getAllUsers/", HttpMethod.GET, getHttpEntityWithHeaders(),
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

				HttpEntity<Util> requestHeaderWithObject = new HttpEntity<Util>(utilEntity, getHeaders());
				restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/sendemail/", HttpMethod.PUT,
						requestHeaderWithObject, Util.class);

				/*
				 * if (emailResponseEntity.getBody().getLastReturnCode() != 250) {
				 * NotifyToCSSTAdmin(userEntity, emailResponseEntity); break; }
				 */

			}
		}
	}

	private static void WhenFUProfileNotCompleted() {
	};

}
