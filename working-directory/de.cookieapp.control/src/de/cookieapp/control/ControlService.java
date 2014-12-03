package de.cookieapp.control;

import java.util.TreeMap;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.control.model.RecipeInfo;
import de.cookieapp.data.model.SecurityClearance;

public interface ControlService {
	
	public Long createSession();
	public boolean createSession(Long sessionId);
	public boolean hasSession(Long sessionId);
	public boolean login(Long sessionId, String userORmail, String password) throws CookieAppException;
	public void logout(Long sessionId) throws CookieAppException;
	public boolean register(Long sessionId, String user, String password, String eMail) throws CookieAppException;
	public boolean changePassword(Long sessionId, String currentPassword, String newPassword) throws CookieAppException;
	
	public SecurityClearance getSecurityClearance(Long sessionId) throws CookieAppException;
	
	public String getCurrentUserName(Long sessionId) throws CookieAppException;
	public String getCurrentUserMail(Long sessionId) throws CookieAppException;
	public TreeMap<Long, String> getRecipesOfCurrentUser(Long sessionId) throws CookieAppException;
	public TreeMap<Long, String> getFavorites(Long sessionId) throws CookieAppException;
	
	public RecipeInfo getRecipe(Long sessionId, Long recipeID) throws CookieAppException;
}
