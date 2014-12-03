package de.cookieapp.control;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.control.exceptions.NoSessionException;
import de.cookieapp.control.exceptions.NoSuchRecipeException;
import de.cookieapp.control.exceptions.NotLoggedInException;
import de.cookieapp.control.model.RecipeInfo;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.SecurityClearance;
import de.cookieapp.data.model.User;
import de.cookieapp.data.service.DataService;
import de.cookieapp.data.service.DataServiceImpl;

public class ControlServiceImpl implements ControlService {
	
	private DataService dataService = null;
	private UserService userService = null;
	private Random random = null;
	private HashMap<Long,User> sessionMap = null;
	
	public ControlServiceImpl(){
		random = new Random();
		dataService = new DataServiceImpl();
		userService = new UserService(dataService);
		sessionMap = new HashMap<Long,User>();
	}

	@Override
	public Long createSession() throws CookieAppException {
		Long session;
		do{
		session = random.nextLong();
		}while(!sessionMap.containsKey(session));
		sessionMap.put(session, null);
		return session;
	}

	@Override
	public boolean createSession(Long sessionId) throws CookieAppException {
		if(sessionMap.containsKey(sessionId)){
			return false;
		}
		sessionMap.put(sessionId, null);
		return true;
	}
	
	@Override
	public boolean hasSession(Long sessionId) throws CookieAppException {
		return sessionMap.containsKey(sessionId);
	}
	
	@Override
	public boolean login(Long sessionId, String userORmail, String password)
			throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = userService.login(userORmail, password);
		if(user == null){
			return false;
		}else{
			sessionMap.put(sessionId, user);
			return true;
		}
	}

	@Override
	public void logout(Long sessionId) throws CookieAppException {
		sessionMap.put(sessionId, null);
	}

	@Override
	public boolean register(Long sessionId, String username, String password,
			String eMail) throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = userService.register(username, password, eMail);
		if(user == null){
			return false;
		}
		sessionMap.put(sessionId, user);
		return true;
	}

	@Override
	public SecurityClearance getSecurityClearance(Long sessionId)
			throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = sessionMap.get(sessionId);
		if(user == null){
			return SecurityClearance.GUEST;
		}
		return user.getSecurityClearance();
	}

	@Override
	public String getCurrentUserName(Long sessionId) throws CookieAppException {
		if(sessionMap.get(sessionId) == null){
			throw  new NotLoggedInException();
		}
		return null;
	}

	@Override
	public TreeMap<Long, String> getRecipesOfCurrentUser(Long sessionId)
			throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if(user == null){
			throw  new NotLoggedInException();
		}
		TreeMap<Long, String> list = new TreeMap<Long, String>();
		for(Recipe r : user.getRecipes()){
			list.put(r.getId(), r.getName());
		}
		return list;
	}

	@Override
	public TreeMap<Long, String> getFavorites(Long sessionId)
			throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if(user == null){
			throw  new NotLoggedInException();
		}
		TreeMap<Long, String> list = new TreeMap<Long, String>();
		for(Recipe r : user.getFavorites()){
			list.put(r.getId(), r.getName());
		}
		return list;
	}
	
	@Override
	public RecipeInfo getRecipe(Long sessionId, Long recipeID)
			throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		Recipe recipe = dataService.getRecipe(recipeID);
		if(recipe == null){
			throw new NoSuchRecipeException();
		}
		return new RecipeInfo(recipe);
	}

	@Override
	public boolean changePassword(Long sessionId, String currentPassword,
			String newPassword) throws CookieAppException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCurrentUserMail(Long sessionId) throws CookieAppException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
