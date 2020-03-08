package com.src.pojo;

import java.io.Serializable;

public class UserServiceDetails implements Serializable {
	
	private static final long serialVersionUID = 6152024827037015930L;

	private Integer serviceId;

	private Integer ourserviceId;

	private Integer userId;

	private String createdBy;

	private String createdOn;

	private boolean isActive;

	private String reasonOfUnSubscribe;

	private String servicePackName;

	private String status;

	private String serviceStartOn;

	private String serviceEndOn;
	
	private User userService;
	
	public User getUserService() {
		return userService;
	}

	public void setUserService(User userService) {
		this.userService = userService;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getOurserviceId() {
		return ourserviceId;
	}

	public void setOurserviceId(Integer ourserviceId) {
		this.ourserviceId = ourserviceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getReasonOfUnSubscribe() {
		return reasonOfUnSubscribe;
	}

	public void setReasonOfUnSubscribe(String reasonOfUnSubscribe) {
		this.reasonOfUnSubscribe = reasonOfUnSubscribe;
	}

	public String getServicePackName() {
		return servicePackName;
	}

	public void setServicePackName(String servicePackName) {
		this.servicePackName = servicePackName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceStartOn() {
		return serviceStartOn;
	}

	public void setServiceStartOn(String serviceStartOn) {
		this.serviceStartOn = serviceStartOn;
	}

	public String getServiceEndOn() {
		return serviceEndOn;
	}

	public void setServiceEndOn(String serviceEndOn) {
		this.serviceEndOn = serviceEndOn;
	}


}
