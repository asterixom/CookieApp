package de.cookieapp.control;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.data.model.SecurityClearance;

public interface ControlService {
	
	public Long createSession() throws CookieAppException;
	public boolean login(String userORmail, String password) throws CookieAppException;
	public boolean logout() throws CookieAppException;
	public boolean register(String user, String password, String eMail) throws CookieAppException;
	
	public SecurityClearance getSecurityClearance() throws CookieAppException;
	
}
