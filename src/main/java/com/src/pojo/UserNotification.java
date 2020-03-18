package com.src.pojo;

import java.io.Serializable;

/**
 * The <code> UserNotification </code> class defines Pojo beans which is used
 * for <code>User Notification</code>.
 * 
 * @author Ishaq
 * @version 1.0
 *
 */
public class UserNotification implements Serializable {

	private static final long serialVersionUID = 601202099687938282L;
	private int id;
	private int userid;
	private String senton;
	private String sentby;
	private int templateid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}
}
