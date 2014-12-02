package de.cookieapp.control.model;

import java.util.Date;

public class PostInfo {

	private String user;
	private Date date;
	private String text;
	
	public PostInfo(String user, Date date, String text){
		this.user = user;
		this.date = date;
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}

	public String getText() {
		return text;
	}
	
	
	
}
