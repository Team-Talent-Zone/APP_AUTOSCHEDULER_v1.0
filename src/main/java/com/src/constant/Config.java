package com.src.constant;

public class Config {

	public final static String RESTSERVICE_URL_DEV = "http://localhost:8080/RestAPI";

	public final static String RESTSERVICE_URL_QA = "http://ec2-34-235-195-154.compute-1.amazonaws.com:8080/technelinkrest";

	public final static String RESTSERVICE_URL_PRD = "http://ec2-34-225-73-253.compute-1.amazonaws.com:8080/technelinkrest";

	public final static String REST_USERNAME_DEV = "restservicebasicauthuser";

	public final static String REST_PASSWORD_DEV = "TL#2017@REST*832463$#";

	public final static String EMAIL_SENT_FROMUSER_DEV = "team.spprt2019@gmail.com";

	/*
	 * The below are email subject and short key
	 */

	public final static String EMAIL_SUBJECT_WHENUSERNOTLOGINYET = "Your Account is Ready . Get Started Now With Login";

	public final static String EMAIL_SHORTKEY_WHENUSERNOTLOGINYET = "autogen3";

	public final static String EMAIL_SUBJECT_SOMETHINGWENTWRONG = "Something Went Wrong. Urgent Attention Needed";

	public final static String EMAIL_SHORTKEY_SOMETHINGWENTWRONG = "autogen4";

}
