package com.src.pojo;

import java.io.Serializable;

public class UserServiceExpirationDetails implements Serializable {

	private static final long serialVersionUID = -4023319338334069924L;

	private int userId;

	private String username;

	private String firstname;

	private String lastname;

	private String createdon;

	private String createdby;

	private String servicestarton;

	private String serviceendon;

	private String name;

	private String preferlang;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCreatedon() {
		return createdon;
	}

	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getServicestarton() {
		return servicestarton;
	}

	public void setServicestarton(String servicestarton) {
		this.servicestarton = servicestarton;
	}

	public String getServiceendon() {
		return serviceendon;
	}

	public void setServiceendon(String serviceendon) {
		this.serviceendon = serviceendon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreferlang() {
		return preferlang;
	}

	public void setPreferlang(String preferlang) {
		this.preferlang = preferlang;
	}

}
