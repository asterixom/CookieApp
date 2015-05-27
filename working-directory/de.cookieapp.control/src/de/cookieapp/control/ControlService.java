package de.cookieapp.control;

import java.util.ArrayList;
import java.util.TreeMap;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.database.Recipe;
import de.cookieapp.data.model.SecurityClearance;
import de.cookieapp.database.User;

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
	
	public Recipe getRecipe(Long sessionId, Long recipeID) throws CookieAppException;
	User getCurrentUser(Long sessionId) throws CookieAppException;
	ArrayList<Recipe> getRecipeByName(Long sessionId, String name)	throws CookieAppException;
}
