package com.src.entity;

import java.io.Serializable;

import org.json.JSONArray;

public class UtilEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fromUser;

	private String toUser;

	private String subject;

	private String body;

	private JSONArray jsonArray;

	private String shortkey;
	
	private boolean isStatus;
	
	private String lastServerResponse;
	
	public String getLastServerResponse() {
		return lastServerResponse;
	}

	public void setLastServerResponse(String lastServerResponse) {
		this.lastServerResponse = lastServerResponse;
	}

	public boolean isStatus() {
		return isStatus;
	}

	public void setStatus(boolean isStatus) {
		this.isStatus = isStatus;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getShortkey() {
		return shortkey;
	}

	public void setShortkey(String shortkey) {
		this.shortkey = shortkey;
	}

}