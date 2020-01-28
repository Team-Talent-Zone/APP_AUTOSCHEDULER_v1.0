package com.src.pojo;

import java.io.Serializable;

import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Util implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String fromUser;

	private String toUser;

	private String subject;

	private String body;

	@JsonIgnore
	private JSONArray jsonArray;

	private String templateURL;

	private int lastReturnCode;

	private String lastServerResponse;

	public String getLastServerResponse() {
		return lastServerResponse;
	}

	public void setLastServerResponse(String lastServerResponse) {
		this.lastServerResponse = lastServerResponse;
	}

	public int getLastReturnCode() {
		return lastReturnCode;
	}

	public void setLastReturnCode(int lastReturnCode) {
		this.lastReturnCode = lastReturnCode;
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

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getTemplateURL() {
		return templateURL;
	}

	public void setTemplateURL(String templateURL) {
		this.templateURL = templateURL;
	}

}