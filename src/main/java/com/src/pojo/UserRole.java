package com.src.pojo;

import java.io.Serializable;

public class UserRole implements Serializable  {
 
	private static final long serialVersionUID = 1L;

	private Integer roleId;

	private int userId;

	private String rolecode;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

}
