package com.src.notifications;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.src.constant.Config;
import com.src.entity.UserEntity;
import com.src.entity.UtilEntity;

public class AbstractManager {

	protected static Logger loggerAbstract = LoggerFactory.getLogger(UserNotify.class);
	static RestTemplate restTemplate = new RestTemplate();

	public static HttpEntity<String> getHttpEntityWithHeaders() {
		return new HttpEntity<String>(getHeaders());
	}

	public static HttpHeaders getHeaders() {
		String plainCredentials = Config.REST_USERNAME + ":" + Config.REST_PWD;
		String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	public static UtilEntity CreateNewUtilEntity(String emailToUser, String emailSubject, String shortkey) {
		UtilEntity utilEntity = new UtilEntity();
		utilEntity.setFromUser(Config.EMAIL_SENT_FROMUSER);
		utilEntity.setToUser(emailToUser);
		utilEntity.setSubject(emailSubject);
		utilEntity.setShortkey(shortkey);
		return utilEntity;
	}

	public static void NotifyToAdministrator(UserEntity userEntity, ResponseEntity<UtilEntity> responseEntity)
			throws JSONException {

		UtilEntity newUtilEntity = CreateNewUtilEntity(Config.EMAIL_SENT_FROMUSER,
				Config.EMAIL_SUBJECT_SOMETHINGWENTWRONG, Config.EMAIL_KEY_SOMETHINGWENTWRONG);

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("fullName", userEntity.getFirstname() + " " + userEntity.getLastname());
		jsonObj.put("lastServerResponse", responseEntity.getBody().getLastServerResponse());

		jsonArray.put(jsonObj);
		newUtilEntity.setJsonArray(jsonArray);

		HttpEntity<UtilEntity> requestHeaderWithObject = new HttpEntity<UtilEntity>(newUtilEntity, getHeaders());
		restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/sendEmail/", HttpMethod.POST,
				requestHeaderWithObject, UtilEntity.class);

	}
}
