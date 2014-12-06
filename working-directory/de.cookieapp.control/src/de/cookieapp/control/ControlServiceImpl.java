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
import de.cookieapp.repository.Repository;

public class ControlServiceImpl implements ControlService {
	
	private DataService dataService = null;
	private Random random = null;
	private HashMap<Long,User> sessionMap = null;
	private Repository repository;
	
	public ControlServiceImpl() {
		random = new Random();
		dataService = new DataService(repository);
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
			throw new NoSessionException();
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
		return user.getSecurityClearance();
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
		for (Recipe r : user.getRecipes()) {
			list.put(r.getId(), r.getName());
		}
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
		for (Recipe r : user.getFavorites()) {
			list.put(r.getId(), r.getName());
		}
		return list;
	}
	
	@Override
	public RecipeInfo getRecipe(Long sessionId, Long recipeID)
			throws CookieAppException {
		if (!sessionMap.containsKey(sessionId)) {
			throw new NoSessionException();
		}
		Recipe recipe = dataService.getRecipe(recipeID);
		if (recipe == null) {
			throw new NoSuchRecipeException();
		}
		return new RecipeInfo(recipe);
	}

	@Override
	public boolean changePassword(Long sessionId, String currentPassword,
			String newPassword) throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if(user==null){
			throw new NoSessionException();
		}
		if(user.checkPassword(dataService.makeHash(currentPassword))){
			user.setPassword(dataService.makeHash(newPassword));
			return true;
		}
		return false;
	}

	@Override
	public String getCurrentUserMail(Long sessionId) throws CookieAppException {
		User user = sessionMap.get(sessionId);
		if(user==null){
			throw new NoSessionException();
		}
		return user.getMail();
	}
	
	public void setRepository(Repository repository) {
		if (repository != null) {
			this.repository = repository;
			dataService.setRepository(repository);
		}
	}

	public void unsetRepsoitory(Repository repository) {
		if (repository != null && this.repository.equals(repository)) {
			this.repository = null;
		}
	}
}
