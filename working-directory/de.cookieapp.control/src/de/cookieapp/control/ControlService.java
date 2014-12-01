package de.cookieapp.control;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.data.model.SecurityClearance;

public interface ControlService {
	
	public Long createSession() throws CookieAppException;
	public boolean createSession(Long sessionId) throws CookieAppException;
	public boolean hasSession(Long sessionId) throws CookieAppException;
	public boolean login(Long sessionId, String userORmail, String password) throws CookieAppException;
	public void logout(Long sessionId) throws CookieAppException;
	public boolean register(Long sessionId, String user, String password, String eMail) throws CookieAppException;
	
	public SecurityClearance getSecurityClearance(Long sessionId) throws CookieAppException;
	
	public boolean test();
	
}
