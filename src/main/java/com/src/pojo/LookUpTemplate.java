package com.src.pojo;

import java.io.Serializable;

/**
 * The <code> LookUpTemplate </code> class defines pojo class which provides
 * email service details.
 * 
 * @author Ishaq
 * @version 1.0
 */
public class LookUpTemplate implements Serializable {
	private static final long serialVersionUID = -2914313078869115793L;
	private int templateid;
	private String name;
	private String url;
	private String description;
	private String shortkey;

	public int getTemplateid() {
		return templateid;
	}

	public void setTemplateid(int templateid) {
		this.templateid = templateid;
	}

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