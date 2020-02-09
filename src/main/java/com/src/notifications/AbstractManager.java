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
import com.src.pojo.User;
import com.src.pojo.Util;

public class AbstractManager {

	protected static Logger loggerAbstract = LoggerFactory.getLogger(UserNotify.class);
	static RestTemplate restTemplate = new RestTemplate();

	protected static HttpEntity<String> getHttpEntityWithHeaders() {
		return new HttpEntity<String>(getHeaders());
	}

	protected static HttpHeaders getHeaders() {
		String plainCredentials = Config.REST_USERNAME_DEV + ":" + Config.REST_PASSWORD_DEV;
		String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	protected static Util CreateNewUtilEntity(String emailToUser, String emailSubject, String templateURL,
			String preferlang) {
		Util utilEntity = new Util();
		utilEntity.setFromuser(Config.EMAIL_SENT_FROMUSER_DEV);
		utilEntity.setTouser(emailToUser);
		utilEntity.setSubject(emailSubject);
		utilEntity.setTemplateurl(templateURL);
		utilEntity.setPreferlang(preferlang);
		return utilEntity;
	}

	protected static void NotifyToCSSTAdmin(User userEntity, ResponseEntity<Util> responseEntity) throws JSONException {

		Util newUtilEntity = CreateNewUtilEntity(Config.EMAIL_SENT_FROMUSER_DEV,
				Config.EMAIL_SUBJECT_SOMETHINGWENTWRONG, Config.EMAIL_SHORTKEY_SOMETHINGWENTWRONG,
				Config.DEFAULT_PREFEREDLANG);

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("firstName", userEntity.getFirstname() + " " + userEntity.getLastname());
		jsonArray.put(jsonObj);
		newUtilEntity.setJsonarray(jsonArray);

		HttpEntity<Util> emailResponseEntity = new HttpEntity<Util>(newUtilEntity, getHeaders());
		restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/sendEmail/", HttpMethod.PUT, emailResponseEntity,
				Util.class);

	}
}
