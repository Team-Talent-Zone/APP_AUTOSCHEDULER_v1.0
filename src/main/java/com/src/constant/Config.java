package com.src.constant;

public class Config {

	public final static String RESTSERVICE_URL_DEV = "http://localhost:8080/RestAPI";

	public final static String RESTSERVICE_URL_QA = "http://ec2-34-235-195-154.compute-1.amazonaws.com:8080/technelinkrest";

	public final static String RESTSERVICE_URL_PRD = "http://ec2-34-225-73-253.compute-1.amazonaws.com:8080/technelinkrest";

	public final static String REST_USERNAME_DEV = "restservicebasicauthuser";

	public final static String REST_PASSWORD_DEV = "TL#2017@REST*832463$#";

	public final static String EMAIL_SENT_FROMUSER_DEV = "team.spprt2019@gmail.com";

	public final static String NOTIFICATION_SENTBY = "System";

	public final static String DEFAULT_PREFEREDLANG = "en";

	public final static String ROLE_FREELANCER_USER = "FREELANCER_USER";

	/*
	 * The below are template short keys
	 */

	public final static String EMAIL_SHORTKEY_CBU_WHENNEWSERVICE_CREATED = "autogen2";

	public final static String EMAIL_SHORTKEY_FU_WHENUSERNOTLOGINYET = "autogen3";

	public final static String EMAIL_SHORTKEY_CBU_WHENUSERNOTLOGINYET = "autogen4";

	public final static String EMAIL_SHORTKEY_SOMETHINGWENTWRONG = "autogen5";

	public final static String EMAIL_SHORTKEY_FU_PROFILENOTCOMPLETED = "autogen6";

	/*
	 * The below are template email subjects
	 */

	public final static String EMAIL_SUBJECT_WHENUSERNOTLOGINYET = "Your account is ready to login";

	public final static String EMAIL_SUBJECT_FU_PROFILENOTCOMPLETED  = "Your Profile is Incompleted .";

	public final static String EMAIL_SUBJECT_SOMETHINGWENTWRONG = "Something went wrong while sending auto email to user";
	
	public final static String EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED = "New Service is on platform . Check out now";

	/*
	 * The below are all the service api call path
	 */

	public final static String APICALL_GETUSERDETAILSBYRECOVERYPASSWORD = "getUserByRecoveryPwd";

	public final static String APICALL_GETFUUSERDETAILSWHENINCOMPLETEDPROFILE = "getFUUserDetailsWhenInCompleteProfile";

	public final static String APICALL_GETUSERSBYROLE = "getUsersByRole";
	
	public final static String APICALL_GETNEWSERVICEDETAILSCREATED = "getNewServiceDetailsCreated";

	public final static String APICALL_SENDEMAIL = "sendemail";
	
	/*
	 * 
	 */
	public final static String  CLIENT_BUSINESS_ADMINISTRATOR = "CLIENT_BUSINESS_ADMINISTRATOR";


}
