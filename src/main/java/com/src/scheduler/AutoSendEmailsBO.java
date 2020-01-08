package com.src.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class AutoSendEmailsBO {

	final Logger loggerAbstract = LoggerFactory
			.getLogger(AutoSendEmailsBO.class);

	protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected DateFormat dateFormatPrint = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	RestTemplate restTemplate = new RestTemplate();

	 
	 /*
	  * Sample service all method
	  * 
	  */
	
	public boolean checkIsNewsFeedAlreadyExist(String publishedAt) {
		try {
			ResponseEntity<Boolean> isNewsExisit = restTemplate.exchange(
					ConfigConstant.RESTSERVICE_URL_DEV
							+ "/checkIsNewsFeedAlreadyExist/" + publishedAt
							+ "/", HttpMethod.GET, getHttpEntityWithHeaders(),
					Boolean.class);
			if (isNewsExisit.getBody() == null) {
				return true;
			}
		} catch (HttpServerErrorException httpServerErrorException) {
			loggerAbstract
					.error("News Feed Scheduler : checkIsNewsFeedAlreadyExist method : HttpServerErrorException "
							+ httpServerErrorException.toString());
		} catch (HttpClientErrorException clientErrorException) {
			loggerAbstract
					.error("News Feed Scheduler : checkIsNewsFeedAlreadyExist method : HttpClientErrorException "
							+ clientErrorException.toString());
		} catch (Exception exception) {
			loggerAbstract
					.error("News Feed Scheduler : checkIsNewsFeedAlreadyExist method : Exception "
							+ exception.toString());
		}
		return false;
	}

	protected HttpEntity<String> getHttpEntityWithHeaders() {
		return new HttpEntity<String>(getHeaders());
	}

	protected HttpHeaders getHeaders() {
		String plainCredentials = ConfigConstant.REST_USERNAME + ":"
				+ ConfigConstant.REST_PWD;
		String base64Credentials = new String(
				Base64.encodeBase64(plainCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
}
