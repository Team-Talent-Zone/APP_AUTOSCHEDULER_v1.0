package com.src.pojo;

import java.io.Serializable;

public class LookUpTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String url;

	private String description;

	private String shortkey;

	public String getShortkey() {
		return shortkey;
	}

	public void setShortkey(String shortkey) {
		this.shortkey = shortkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}