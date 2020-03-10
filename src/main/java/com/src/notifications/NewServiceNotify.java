package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.NewService;
import com.src.pojo.User;
import com.src.pojo.Util;

/*
 * this class will have methods which will send emails to the Users related to any New Services.
 */
public class NewServiceNotify extends AbstractManager {

	public static void TriggerNewServiceRelatedAutoGenEmail() throws JSONException {
		// WhenNewServiceIsOnboardedOnPlatform();
		WhenNewServiceIsCreatedForCBUUsers();
	}

	/*
	 * this method is for getting User Details from RestAPI by particular UserID.
	 */
	private static ResponseEntity<User> getUserDetailsByUserId(int userId, String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/" + userId + "/", HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<User>() {
				});
	}

	/*
	 * this method sends email to CBA users when new Service is created.
	 */
	private static void WhenNewServiceIsCreatedForCBUUsers() throws JSONException {

		ResponseEntity<ArrayList<NewService>> usersList = getNewServiceDetailsByAPICall(
				Config.APICALL_GETNEWSERVICEDETAILSCREATED);
		ResponseEntity<LookUpTemplate> cbuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBU_WHENNEWSERVICE_CREATED);

		if (usersList != null) {
			for (NewService user : usersList.getBody()) {
				ResponseEntity<User> usersDetails = getUserDetailsByUserId(user.getUserId(),
						Config.APICALL_GETUSERSBYUSERID);
									
				try {
					Util util = createNewUtilEntityObj(usersDetails.getBody().getUsername(),
							Config.EMAIL_SUBJECT_CBU_WHENSERVICEISGETTINGEXPIRED,
							cbuTemplateObject.getBody().getUrl().toString(), usersDetails.getBody().getPreferlang());

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", usersDetails.getBody().getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("servicepackname", user.getName());
					jsonObj.put("platformURL", Config.UI_URL);

					util.setTemplatedynamicdata(jsonObj.toString());
					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(user.getUserId(), cbuTemplateObject.getBody().getTemplateid());
					}
				} catch (Exception e) {

					NotifyToCSSTPlatFormAdminAboutError(usersDetails.getBody().getUsername(),
							usersDetails.getBody().getFirstname(), e.toString());
				}
			}
		}
	}

	private static void WhenNewServiceIsOnboardedOnPlatform() throws JSONException {

		/*
		 * Replace with the api url path
		 */

		ResponseEntity<ArrayList<NewService>> newservicelist = restTemplate.exchange(
				Config.REST_URL + "/" + Config.APICALL_GETNEWSERVICEDETAILSCREATED, HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<ArrayList<NewService>>() {
				});
		if (newservicelist != null) {
			ResponseEntity<ArrayList<User>> usersCBUList = restTemplate.exchange(
					Config.REST_URL + "/" + Config.APICALL_GETUSERSBYROLE + "/" + Config.CLIENT_BUSINESS_ADMINISTRATOR,
					HttpMethod.GET, getHttpEntityWithHeaders(), new ParameterizedTypeReference<ArrayList<User>>() {
					});

			ResponseEntity<LookUpTemplate> templatedetails = getTemplateDetailsByShortKey(
					Config.EMAIL_SHORTKEY_CBU_WHENNEWSERVICE_CREATED);

			if (usersCBUList != null) {
				for (User user : usersCBUList.getBody()) {
					try {
						Util util = createNewUtilEntityObj(user.getUsername(),
								Config.EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED,
								templatedetails.getBody().getUrl().toString(), user.getPreferlang());
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("firstName", user.getFirstname());
						util.setTemplatedynamicdata(jsonObj.toString());
						sendEmail(util);
					} catch (Exception e) {
						NotifyToCSSTPlatFormAdminAboutError(user.getUsername(), user.getFirstname(), e.toString());
					}
				}
			}
		}
	}
}
