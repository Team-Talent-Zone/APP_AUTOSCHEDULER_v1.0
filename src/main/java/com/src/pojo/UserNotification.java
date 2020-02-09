package com.src.pojo;

import java.io.Serializable;

public class UserNotification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int userId;
	
	private String senton;
	
	private String sentby;
	
	private int templateId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSenton() {
		return senton;
	}

	public void setSenton(String senton) {
		this.senton = senton;
	}

	public String getSentby() {
		return sentby;
	}

	public void setSentby(String sentby) {
		this.sentby = sentby;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

}
