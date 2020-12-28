package com.src.notifications;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

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
import com.src.pojo.FreelancerPaymentInput;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.NewService;
import com.src.pojo.PaymentToFreelancers;
import com.src.pojo.PayoutTransferResponse;
import com.src.pojo.User;
import com.src.pojo.UserNotification;
import com.src.pojo.UserServiceDetails;
import com.src.pojo.UserServiceExpirationDetails;
import com.src.pojo.Util;

/**
 * The <code> AbstractManager </code> class defines AutoScheduler functionality.
 * 
 * @author Ishaq
 * @version 1.0
 *
 */
public class AbstractManager {

	protected static Logger loggerAbstract = LoggerFactory.getLogger(UserNotify.class);
	static RestTemplate restTemplate = new RestTemplate();

	protected static HttpEntity<String> getHttpEntityWithHeaders() {
		return new HttpEntity<String>(getHeaders());
	}

	/**
	 * Get Header Method is to get Http Header details.
	 */
	protected static HttpHeaders getHeaders() {
		String plainCredentials = Config.REST_USERNAME_DEV + ":" + Config.REST_PSWD_DEV;
		String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	/**
	 * createNewUtilEntityObj -This Method is to create New Util Entity Object.
	 * 
	 * @param emailToUser
	 * @param emailSubject
	 * @param templateURL
	 * @param preferlang
	 */
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

	/**
	 * ResponseEntity -This method is written to get Response Entity Details.
	 * 
	 * @param shortKey
	 * @return
	 */
	protected static ResponseEntity<LookUpTemplate> getTemplateDetailsByShortKey(String shortKey) {
		return restTemplate.exchange(Config.REST_URL + "/getLookupTemplateEntityByShortkey/" + shortKey, HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<LookUpTemplate>() {
				});
	}
	
	/**
	 * ResponseEntity -This method is written to get Response Entity Details.
	 * 
	 * @param shortKey
	 * @return
	 */
	protected static ResponseEntity<String> getReferenceDataByShortKey(String shortKey) {
		return restTemplate.exchange(Config.REST_URL + "/getReferenceLookupByShortKey/" + shortKey, HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<String>() {
				});
	}


	/**
	 * ResponseEntity-getUserDetailsByAPICall -This method is written to get
	 * Response Entity Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<ArrayList<User>> getUserDetailsByAPICall(String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<User>>() {
				});
	}

	/**
	 * ResponseEntity-getServiceDetailsByAPICall -This method is written to get
	 * Response Entity Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<ArrayList<UserServiceExpirationDetails>> getServiceDetailsByAPICall(
			String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<UserServiceExpirationDetails>>() {
				});
	}

	/**
	 * ResponseEntity-getNewServiceDetailsByAPICall -This method is written to get
	 * Response Entity Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<ArrayList<NewService>> getNewServiceDetailsByAPICall(String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<NewService>>() {
				});
	}

	/**
	 * ResponseEntity-getUserServicePendingPayment -This method is written to get
	 * Response Entity Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<ArrayList<UserServiceDetails>> getUserServicePendingPayment(String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<UserServiceDetails>>() {
				});
	}
	
	/**
	 * ResponseEntity-getUserAllPendingPaymentOfFreelancer -This method is written to get
	 * Response Entity Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<ArrayList<PaymentToFreelancers>> getUserAllPendingPaymentOfFreelancer(String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/", HttpMethod.GET, getHttpEntityWithHeaders(),
				new ParameterizedTypeReference<ArrayList<PaymentToFreelancers>>() {
				});
	}
	
	/**
	 * ResponseEntity-sendEmail -This method is written to get Response Entity
	 * Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<PayoutTransferResponse> payment(FreelancerPaymentInput freelancerPaymentInput) {
		HttpEntity<FreelancerPaymentInput> emailResponseEntity = new HttpEntity<FreelancerPaymentInput>(freelancerPaymentInput, getHeaders());
		return restTemplate.exchange(Config.REST_URL + "/payment/", HttpMethod.POST, emailResponseEntity, PayoutTransferResponse.class);
	}

	/**
	 * ResponseEntity-sendEmail -This method is written to get Response Entity
	 * Details.
	 * 
	 * @param apipath
	 */
	protected static ResponseEntity<Util> sendEmail(Util util) {
		HttpEntity<Util> emailResponseEntity = new HttpEntity<Util>(util, getHeaders());
		return restTemplate.exchange(Config.REST_URL + "/sendemail/", HttpMethod.POST, emailResponseEntity, Util.class);
	}

	/**
	 * This Method is for Notification Details.
	 * 
	 * @param userId
	 * @param templateId
	 */
	protected static void saveNotificationDetails(int userId, int templateId) {
		UserNotification notification = new UserNotification();
		notification.setUserid(userId);
		notification.setSentby(Config.NOTIFICATION_SENTBY);
		notification.setSenton(getCurrentDateInNewFormat());
		notification.setTemplateid(templateId);
		HttpEntity<UserNotification> requestHeaderWithUserNotificationObject = new HttpEntity<UserNotification>(
				notification, getHeaders());
		restTemplate.exchange(Config.REST_URL + "/saveUserNotification/", HttpMethod.POST,
				requestHeaderWithUserNotificationObject, UserNotification.class);
	};

	/**
	 * This Method is to get Current Date in New Format.
	 * 
	 * @return Date
	 */
	protected static String getCurrentDateInNewFormat() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	/**
	 * This Method is to get Notification to Admin about the Error.
	 * 
	 * @param username
	 * @param firstname
	 * @param error
	 * @throws JSONException
	 */
	protected static void NotifyToCSSTPlatFormAdminAboutError(String username, String firstname, String error)
			throws JSONException {

		ResponseEntity<LookUpTemplate> errorTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_SOMETHINGWENTWRONG);
		Util util = createNewUtilEntityObj(Config.EMAIL_SENT_FROMUSER_DEV,
				Config.EMAIL_SUBJECT_SOMETHINGWENTWRONG + " :" + username, errorTemplateObject.getBody().getUrl(),
				Config.DEFAULT_PREFEREDLANG);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", Config.EMAIL_SENT_FROMUSER_DEV);
		jsonObj.put("firstname", firstname);
		jsonObj.put("username", username);
		jsonObj.put("eventname", error);
		jsonObj.put("senton", getCurrentDateInNewFormat());
		jsonObj.put("sentby", Config.NOTIFICATION_SENTBY);
		util.setTemplatedynamicdata(jsonObj.toString());
		sendEmail(util);
	}
	
	/**
	 * genRandomAlphaNumeric - To get Random Alpha Numeric Data.
	 * 
	 * @return Random Data.
	 */
	protected static String genRandomAlphaNumeric() {
		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int n = alphabet.length();
		String result = "";
		Random r;
		try {
			r = SecureRandom.getInstanceStrong();
			for (int i = 0; i < 10; i++)
				result = result + alphabet.charAt(r.nextInt(n));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Exception in CommonUtilites-genRandomAlphaNumeric():" + e);
		}
		return result;
	}


}
