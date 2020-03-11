package com.src.pojo;

import java.io.Serializable;

public class UserBiz implements Serializable  {

	private static final long serialVersionUID = 1L;

	private Integer Id;

	private int userId;
	
	private String bizname;

	private String biztype;

	private String bizwebsite;

	private String abtbiz;

	private String purposeofsignup;

	private String designation;
	
	private String streetAddress;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipcode;

	private String fulladdress;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBizname() {
		return bizname;
	}

	public void setBizname(String bizname) {
		this.bizname = bizname;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getBizwebsite() {
		return bizwebsite;
	}

	public void setBizwebsite(String bizwebsite) {
		this.bizwebsite = bizwebsite;
	}

	public String getAbtbiz() {
		return abtbiz;
	}

	public void setAbtbiz(String abtbiz) {
		this.abtbiz = abtbiz;
	}

	public String getPurposeofsignup() {
		return purposeofsignup;
	}

	public void setPurposeofsignup(String purposeofsignup) {
		this.purposeofsignup = purposeofsignup;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getFulladdress() {
		return fulladdress;
	}

	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	 

	
}
