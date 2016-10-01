package com.glingo.marvin.bundles.console.ressources.model;

import java.util.Date;

public class Message {

	private int id;
	private String corps;
	private Date date;
	
	public Message() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorps() {
		return corps;
	}

	public void setCorps(String corps) {
		this.corps = corps;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
