package de.cookieapp.control;

import java.util.TreeMap;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.control.model.RecipeInfo;
import de.cookieapp.data.model.SecurityClearance;

public interface ControlService {
	
	public Long createSession() throws CookieAppException;
	public boolean createSession(Long sessionId) throws CookieAppException;
	public boolean hasSession(Long sessionId) throws CookieAppException;
	public boolean login(Long sessionId, String userORmail, String password) throws CookieAppException;
	public void logout(Long sessionId) throws CookieAppException;
	public boolean register(Long sessionId, String user, String password, String eMail) throws CookieAppException;
	
	public SecurityClearance getSecurityClearance(Long sessionId) throws CookieAppException;
	
	public String getCurrentUserName(Long sessionId) throws CookieAppException;
	public TreeMap<Long, String> getRecipesOfCurrentUser(Long sessionId) throws CookieAppException;
	public TreeMap<Long, String> getFavorites(Long sessionId) throws CookieAppException;
	
	public RecipeInfo getRecipe(Long sessionId, Long recipeID) throws CookieAppException;
}
