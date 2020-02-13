package com.src.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Util implements Serializable {

	private String fromuser;

	private String touser;

	private String subject;

	private String body;

	private String templatedynamicdata;

	private String templateurl;
	
	private int lastreturncode;
	
	private String lastserverresponse;
	
	private String preferlang;

	public String getFromuser() {
		return fromuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
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

	public String getTemplateurl() {
		return templateurl;
	}

	public void setTemplateurl(String templateurl) {
		this.templateurl = templateurl;
	}

	public int getLastreturncode() {
		return lastreturncode;
	}

	public void setLastreturncode(int lastreturncode) {
		this.lastreturncode = lastreturncode;
	}

	public String getLastserverresponse() {
		return lastserverresponse;
	}

	public void setLastserverresponse(String lastserverresponse) {
		this.lastserverresponse = lastserverresponse;
	}

	public String getPreferlang() {
		return preferlang;
	}

	public void setPreferlang(String preferlang) {
		this.preferlang = preferlang;
	}

	public String getTemplatedynamicdata() {
		return templatedynamicdata;
	}

	public void setTemplatedynamicdata(String templatedynamicdata) {
		this.templatedynamicdata = templatedynamicdata;
	}

}