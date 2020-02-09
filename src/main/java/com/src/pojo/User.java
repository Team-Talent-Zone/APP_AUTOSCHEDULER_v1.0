package com.src.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String username;

	private String password;

	private boolean isactive;

	private String firstname;

	private String lastname;

	private boolean isrecoverypwd;

	private String reasonofdeactivation;

	private String createdon;

	private String createdby;

	private String updateby;

	private String updatedon;
	
	private String preferlang;

	private ArrayList<UserRole> userroles;

	private ArrayList<UserBiz> userbizdetails;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
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

	public boolean isIsrecoverypwd() {
		return isrecoverypwd;
	}

	public void setIsrecoverypwd(boolean isrecoverypwd) {
		this.isrecoverypwd = isrecoverypwd;
	}

	public String getReasonofdeactivation() {
		return reasonofdeactivation;
	}

	public void setReasonofdeactivation(String reasonofdeactivation) {
		this.reasonofdeactivation = reasonofdeactivation;
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

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public String getUpdatedon() {
		return updatedon;
	}

	public void setUpdatedon(String updatedon) {
		this.updatedon = updatedon;
	}

	public ArrayList<UserRole> getUserroles() {
		return userroles;
	}

	public void setUserroles(ArrayList<UserRole> userroles) {
		this.userroles = userroles;
	}

	public ArrayList<UserBiz> getUserbizdetails() {
		return userbizdetails;
	}

	public void setUserbizdetails(ArrayList<UserBiz> userbizdetails) {
		this.userbizdetails = userbizdetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPreferlang() {
		return preferlang;
	}

	public void setPreferlang(String preferlang) {
		this.preferlang = preferlang;
	}

}