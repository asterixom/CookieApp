package de.cookieapp.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.control.exceptions.NoSessionException;
import de.cookieapp.control.exceptions.NoSuchRecipeException;
import de.cookieapp.control.exceptions.NotLoggedInException;
import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.Recommendation;
import de.cookieapp.data.model.SecurityClearance;
import de.cookieapp.data.model.User;
import de.cookieapp.dataimpl.UserImpl;
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
		if(name.toLowerCase().contains("lasagne")){
			recipe.add(new TempRecipe(new Long(1001001), "Lasagne"));
		}
		if(name.toLowerCase().contains("burger")){
			recipe.add(new TempRecipe(new Long(2002002), "Burger"));
		}
		
		if (recipe == null||recipe.size()<=0) {
			throw new NoSuchRecipeException();
		}
		return recipe;
	}

	class TempRecipe implements Recipe{

		private Long id;
		private String name;
		private String description = "";
		private Date created;
		
		public TempRecipe(Long id, String name){
			this.id = id;
			this.name = name;
			created = new Date();
		}
		
		@Override
		public Long getId() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public Date getCreated() {
			return created;
		}

		@Override
		public User getCreator() {
			return new UserImpl(123456L, "Moritz", "Test1234", "moe@web.de", new Date(System.currentTimeMillis()), new ArrayList<Recipe>(), new ArrayList<Recipe>());
		}

		@Override
		public ArrayList<Ingredient> getIngredients() {
			return new ArrayList<>();
		}

		@Override
		public void addIngredient(Ingredient ingredient) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeIngredient(Ingredient ingredient) {
			// TODO Auto-generated method stub
		}

		@Override
		public ArrayList<Comment> getComments() {
			return new ArrayList<>();
		}

		@Override
		public void addComment(Comment comment) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeComment(Comment comment) {
			// TODO Auto-generated method stub
		}

		@Override
		public ArrayList<Recommendation> getRecommendations() {
			return new ArrayList<>();
		}

		@Override
		public void addRecommendation(Recommendation recommendation) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeRecommendation(Recommendation recommendation) {
			// TODO Auto-generated method stub
		}
		
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
