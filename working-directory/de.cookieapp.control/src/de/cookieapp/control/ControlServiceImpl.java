package de.cookieapp.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.control.exceptions.NoSessionException;
import de.cookieapp.control.exceptions.NoSuchRecipeException;
import de.cookieapp.control.exceptions.NotLoggedInException;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.SecurityClearance;
import de.cookieapp.data.model.User;
import de.cookieapp.data.service.DataProvider;

public class ControlServiceImpl implements ControlService {
	
	private DataService dataService = null;
	private Random random = null;
	private HashMap<Long,User> sessionMap = null;
	private DataProvider dataProvider;
	
	public ControlServiceImpl() {
		random = new Random();
		dataService = new DataService(dataProvider);
		sessionMap = new HashMap<Long,User>();
	}
	
	@Override
	public Long createSession() {
		Long session;
		do {
			session = random.nextLong();
		} while(sessionMap.containsKey(session));
		sessionMap.put(session, null);
		return session;
	}

	@Override
	public boolean createSession(Long sessionId) {
		if (sessionMap.containsKey(sessionId)) {
			return false;
		}
		sessionMap.put(sessionId, null);
		return true;
	}
	
	@Override
	public boolean hasSession(Long sessionId) {
		return sessionMap.containsKey(sessionId);
	}
	
	@Override
	public boolean login(Long sessionId, String userORmail, String password)
			throws CookieAppException {
		if (!sessionMap.containsKey(sessionId)) {
			//throw new NoSessionException();
			System.err.println("No Such Session available: [" + sessionId + "]");
		}
		User user = dataService.login(userORmail, password);
		if (user == null) {
			return false;
		} else {
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
		if (!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = dataService.register(username, password, eMail);
		if (user == null) {
			return false;
		}
		sessionMap.put(sessionId, user);
		return true;
	}

	@Override
	public SecurityClearance getSecurityClearance(Long sessionId)
			throws CookieAppException {
		if (!sessionMap.containsKey(sessionId)) {
			throw new NoSessionException();
		}
		User user = sessionMap.get(sessionId);
		if (user == null) {
			return SecurityClearance.GUEST;
		}
		return SecurityClearance.USER;
		//return user.getSecurityClearance();
	}

	@Override
	public String getCurrentUserName(Long sessionId) throws CookieAppException {
		if (sessionMap.get(sessionId) == null) {
			throw  new NotLoggedInException();
		}
		return sessionMap.get(sessionId).getName();
	}
	
	@Override
	public User getCurrentUser(Long sessionId) throws CookieAppException {
		if (sessionMap.get(sessionId) == null) {
			throw  new NotLoggedInException();
		}
		return sessionMap.get(sessionId);
	}

	@Override
	public TreeMap<Long, String> getRecipesOfCurrentUser(Long sessionId)
			throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if (user == null) {
			throw  new NotLoggedInException();
		}
		TreeMap<Long, String> list = new TreeMap<Long, String>();
		// TODO finish implementing
		/*
		for (Recipe r : user.getRecipes()) {
			list.put(r.getId(), r.getName());
		}
		*/
		return list;
	}

	@Override
	public TreeMap<Long, String> getFavorites(Long sessionId)
			throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if (user == null) {
			throw  new NotLoggedInException();
		}
		TreeMap<Long, String> list = new TreeMap<Long, String>();
		// TODO Implement new
		/*
		for (Recipe r : user.getFavorites()) {
			list.put(r.getId(), r.getName());
		}
		*/
		return list;
	}
	
	@Override
	public Recipe getRecipe(Long sessionId, Long recipeID)
			throws CookieAppException {
		if (!sessionMap.containsKey(sessionId)) {
			throw new NoSessionException();
		}
		Recipe recipe = dataService.getRecipe(recipeID);
		if (recipe == null) {
			throw new NoSuchRecipeException();
		}
		return recipe;
	}
	
	@Override
	public ArrayList<Recipe> getRecipeByName(Long sessionId, String name)
			throws CookieAppException {
		
		if (!sessionMap.containsKey(sessionId)) {
			throw new NoSessionException();
		}
//		name = name.toLowerCase();
		//ArrayList<Recipe> recipe = dataService.getRecipesWithName(name);
		ArrayList<Recipe> recipe = new ArrayList<>();		
		recipe.add(dataProvider.getRecipe(dataProvider.getRecipeID(name)));
		if (recipe == null || recipe.size() < 1) {
			throw new NoSuchRecipeException();
		}
		return recipe;
	}
	
	@Override
	public boolean changePassword(Long sessionId, String currentPassword,
			String newPassword) throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if(user==null){
			throw new NoSessionException();
		}
		// TODO instead of sessionID it has to be the USERID!!!
		return dataProvider.changePassword(sessionId, currentPassword, newPassword);
	}

	@Override
	public String getCurrentUserMail(Long sessionId) throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if(user==null){
			throw new NoSessionException();
		}
		return user.geteMail();
	}
	
	public void setDataProvider(DataProvider dataProvider) {
		if (dataProvider != null) {
			this.dataProvider = dataProvider;
			dataService.setRepository(dataProvider);
			System.out.println("Debug: DataProvider is set!");
		}
	}

	public void unsetDataProvider(DataProvider dataProvider) {
		if (dataProvider != null && this.dataProvider.equals(dataProvider)) {
			this.dataProvider = null;
		}
	}
}
