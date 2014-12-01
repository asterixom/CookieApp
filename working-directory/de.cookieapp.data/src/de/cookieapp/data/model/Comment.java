package de.cookieapp.data.model;

import java.util.Date;

public interface Comment {

	public String getText();
	public void setText(String text);
	
	public User getCreator();
	public void setCreator(User user);
	
	public Date getCreated();
}
