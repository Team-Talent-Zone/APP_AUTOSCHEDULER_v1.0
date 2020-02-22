package com.src.notifications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.User;
import com.src.pojo.UserNotification;
import com.src.pojo.UserServiceExpirationDetails;
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

	protected static Util createNewUtilEntityObj(String emailToUser, String emailSubject, String templateURL,
			String preferlang) {
		Util utilEntity = new Util();
		utilEntity.setFromuser(Config.EMAIL_SENT_FROMUSER_DEV);
		utilEntity.setTouser(emailToUser);
		utilEntity.setSubject(emailSubject);
		utilEntity.setTemplateurl(templateURL);
		utilEntity.setPreferlang(preferlang);
		return utilEntity;
	}

	protected static ResponseEntity<LookUpTemplate> getTemplateDetailsByShortKey(String shortKey) {
		return restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/getLookupTemplateEntityByShortkey/" + shortKey,
				HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<LookUpTemplate>() {
				});
	}

	protected static ResponseEntity<ArrayList<User>> getUserDetailsByAPICall(String apipath) {
		return restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/" + apipath + "/", HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<ArrayList<User>>() {
				});
	}
	
	protected static ResponseEntity<ArrayList<UserServiceExpirationDetails>> getServiceDetailsByAPICall(String apipath) {
		return restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/" + apipath + "/", HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<ArrayList<UserServiceExpirationDetails>>() {
				});
	}
	

	protected static ResponseEntity<Util> sendEmail(Util util) {
		HttpEntity<Util> emailResponseEntity = new HttpEntity<Util>(util, getHeaders());
		return restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/sendemail/", HttpMethod.POST, emailResponseEntity,
				Util.class);
	}

	protected static void saveNotificationDetails(int userId, int templateId) {
		UserNotification notification = new UserNotification();
		notification.setUserid(userId);
		notification.setSentby(Config.NOTIFICATION_SENTBY);
		notification.setSenton(getCurrentDateInNewFormat());
		notification.setTemplateid(templateId);
		HttpEntity<UserNotification> requestHeaderWithUserNotificationObject = new HttpEntity<UserNotification>(
				notification, getHeaders());
		restTemplate.exchange(Config.RESTSERVICE_URL_DEV + "/saveUserNotification/", HttpMethod.POST,
				requestHeaderWithUserNotificationObject, UserNotification.class);
	};

	private static String getCurrentDateInNewFormat() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	protected static void NotifyToCSSTPlatFormAdminAboutError(User user, String error) throws JSONException {

		ResponseEntity<LookUpTemplate> errorTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_SOMETHINGWENTWRONG);
		Util util = createNewUtilEntityObj(Config.EMAIL_SENT_FROMUSER_DEV,
				Config.EMAIL_SUBJECT_SOMETHINGWENTWRONG + " :" + user.getUsername(),
				errorTemplateObject.getBody().getUrl(), Config.DEFAULT_PREFEREDLANG);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("firstName", user.getFirstname());
		jsonObj.put("error", error);
		util.setTemplatedynamicdata(jsonObj.toString());
		sendEmail(util);
	}

	protected static void NotifyToCSSTPlatFormAdminAboutError(UserServiceExpirationDetails user, String error) throws JSONException {

		ResponseEntity<LookUpTemplate> errorTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_SOMETHINGWENTWRONG);
		Util util = createNewUtilEntityObj(Config.EMAIL_SENT_FROMUSER_DEV,
				Config.EMAIL_SUBJECT_SOMETHINGWENTWRONG + " :" + user.getUsername(),
				errorTemplateObject.getBody().getUrl(), Config.DEFAULT_PREFEREDLANG);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("firstName", user.getFirstname());
		jsonObj.put("error", error);
		util.setTemplatedynamicdata(jsonObj.toString());
		sendEmail(util);
	}

}
