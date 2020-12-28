package com.src.notifications;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.src.constant.Config;
import com.src.pojo.FreelancerPaymentInput;
import com.src.pojo.LookUpTemplate;
import com.src.pojo.PaymentToFreelancers;
import com.src.pojo.PayoutTransferResponse;
import com.src.pojo.User;
import com.src.pojo.UserServiceDetails;
import com.src.pojo.Util;
import com.src.scheduler.Start;

/**
 * This <code> PaymentNotify</code> class will have methods which will send
 * emails to the Users related to Payments.
 * 
 * @author Ishaq
 * @version 1.0
 *
 */
public class PaymentNotify extends AbstractManager {

	final static Logger logger = LoggerFactory.getLogger(PaymentNotify.class);

	/**
	 * This is static method for Generating the Email Auto Scheduler Service.
	 * 
	 * @throws JSONException
	 */
	public static void TriggerPaymentRelatedAutoGenEmail() throws JSONException {
		logger.info("================The Payment Notify Class is triggered =================="
				+ getCurrentDateInNewFormat());

		// WhenCBUUserPaymentIsPending();
		getUserAllPendingPaymentOfFreelancer();
		logger.info("================The Payment Notify Class is Ended On =================="
				+ getCurrentDateInNewFormat());

	}

	/**
	 * This method is for getting User Details from RestAPI by particular UserID
	 * 
	 * @param userId
	 * @param apipath
	 */
	private static ResponseEntity<User> getUserDetailsByUserId(int userId, String apipath) {
		return restTemplate.exchange(Config.REST_URL + "/" + apipath + "/" + userId + "/", HttpMethod.GET,
				getHttpEntityWithHeaders(), new ParameterizedTypeReference<User>() {
				});
	}

	/**
	 * This method sends email to CBU users who have not completed payment for the
	 * services.
	 * 
	 * @throws JSONException
	 */
	private static void WhenCBUUserPaymentIsPending() throws JSONException {
		ResponseEntity<ArrayList<UserServiceDetails>> usersList = getUserServicePendingPayment(
				Config.APICALL_GETUSERSERVICEPENDINGPAYMENT);
		ResponseEntity<LookUpTemplate> cbuTemplateObject = getTemplateDetailsByShortKey(
				Config.EMAIL_SHORTKEY_CBA_WHENPAYMENTISPENDING);

		if (usersList.getBody() != null) {
			for (UserServiceDetails userServiceDetails : usersList.getBody()) {
				ResponseEntity<User> usersDetails = getUserDetailsByUserId(userServiceDetails.getUserid(),
						Config.APICALL_GETUSERSBYUSERID);
				try {
					Util util = createNewUtilEntityObj(usersDetails.getBody().getUsername(),
							Config.EMAIL_SUBJECT_CBU_WHENNEWSERVICE_CREATED,
							cbuTemplateObject.getBody().getUrl().toString(), usersDetails.getBody().getPreferlang());
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("firstname", usersDetails.getBody().getFirstname());
					jsonObj.put("companyname", Config.COMPANY_NAME);
					jsonObj.put("platformURL", Config.UI_URL);
					util.setTemplatedynamicdata(jsonObj.toString());
					ResponseEntity<Util> emailresponse = sendEmail(util);
					if (emailresponse.getBody().getLastreturncode() == 250) {
						saveNotificationDetails(usersDetails.getBody().getUserId(),
								cbuTemplateObject.getBody().getTemplateid());
					}

				} catch (Exception e) {
					NotifyToCSSTPlatFormAdminAboutError(usersDetails.getBody().getUsername(),
							usersDetails.getBody().getFirstname(), e.toString());
				}
			}
		}
	}

	/**
	 * This method get list of all the freelancers to pay for the job invoice and
	 * update the transcation id once paid services.
	 * 
	 * @throws JSONException
	 */
	private static void getUserAllPendingPaymentOfFreelancer() throws JSONException {
		ResponseEntity<ArrayList<PaymentToFreelancers>> freelanceList = getUserAllPendingPaymentOfFreelancer(
				Config.APICALL_GETUSERALLPENIDNGPAYMENTOFFRERELANCER);
		if (freelanceList.getBody() != null) {
			logger.info("======Total Number of Skilled Worker payment initiated today is =========="
					+ freelanceList.getBody().size());
			for (PaymentToFreelancers freelanceDetail : freelanceList.getBody()) {
				ResponseEntity<User> usersDetails = getUserDetailsByUserId(freelanceDetail.getFreelanceuserId(),
						Config.APICALL_GETUSERSBYUSERID);
				if (freelanceDetail.getBeneficiaryId() != null) {
					try {
						FreelancerPaymentInput freelancerPaymentInput = new FreelancerPaymentInput();
						freelancerPaymentInput.setAmount(Double.valueOf(freelanceDetail.getTofreelanceamount()));
						freelancerPaymentInput.setBatchId(genRandomAlphaNumeric());
						freelancerPaymentInput.setBeneficiaryId(freelanceDetail.getBeneficiaryId());
						ResponseEntity<String> merchantRefId = getReferenceDataByShortKey("mkey");
						freelancerPaymentInput.setMerchantRefId(merchantRefId.getBody().toString());
						freelancerPaymentInput.setPurpose("Payment from Company");
						freelancerPaymentInput.setPaymentType("IMPS");
						ResponseEntity<PayoutTransferResponse> resp = payment(freelancerPaymentInput);
						if (resp.getBody().isStatus()) {
							logger.info("======Pending Process to Pay " + usersDetails.getBody().getUsername()
									+ " of amount " + freelanceDetail.getTofreelanceamount() + "======");
						}
					} catch (Exception e) {
						NotifyToCSSTPlatFormAdminAboutError(usersDetails.getBody().getUsername(),
								usersDetails.getBody().getFirstname(), e.toString());
					}
				} else {
					logger.info("====== There is no benefinciary account for the skilled worker: "
							+ usersDetails.getBody().getUsername() + " of amount "
							+ freelanceDetail.getTofreelanceamount() + "======");
					NotifyToCSSTPlatFormAdminAboutError(usersDetails.getBody().getUsername(),
							usersDetails.getBody().getFirstname(),
							"There is no beneficiary Id for this skilled worker");
				}
			}
		}
	}
}
