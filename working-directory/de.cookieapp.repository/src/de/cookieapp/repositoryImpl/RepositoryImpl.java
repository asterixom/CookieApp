package de.cookieapp.repositoryImpl;

import java.util.ArrayList;
import java.util.Date;

import de.cookieapp.dataimpl.User;
import de.cookieapp.dataimpl.Recipe;
import de.cookieapp.repository.Repository;


public class RepositoryImpl implements Repository {

	ArrayList<User> userRepository;
	ArrayList<Recipe> recipeRepository;
	
	public RepositoryImpl() {
		userRepository = new ArrayList<User>();
		recipeRepository = new ArrayList<Recipe>();	
	}
	
	/**
	 * Returns the User from Repository
	 * @param name the Name of the User, that is searched for. may be null
	 * @param mail the mail of the User, that is searched for. may be null
	 * @return the User object from name or mail. may be null
	 */
	public User getUser(String name, String mail) {
		User user = null;
		if(name != null) {
			for (User userFromRepo : userRepository) {
				if (userFromRepo.getName().equals(name)) {
					user = userFromRepo;
				}
			}
		} else if (mail != null) {
			for (User userFromRepo : userRepository) {
				if (userFromRepo.getMail().equals(mail)) {
					user = userFromRepo;
				}
			}
		}
		return user;
	}
	
	
	public User addUser(String name, String mail, String password) {
		User user = null;
		if (name != null && mail != null && password != null) {
			Date date = new Date();
			date.setTime(System.currentTimeMillis());
			long id = userRepository.size() + 1;
			user = new User(id, name, password, mail, date, null, null);
			userRepository.add(user);
		} 
		return user;
	}
	
	public Recipe addRecipe(String name, String description, Date created, User creator) {
		Recipe recipe = null;
		if (name != null && description != null && created != null && creator != null) {
			Date date = new Date();
			date.setTime(System.currentTimeMillis());
			Long id = (long) (recipeRepository.size() + 1);
			recipe = new Recipe(id, name, description, date, null, null, null);
			recipeRepository.add(recipe);
		}
		return recipe;
	}
	
	public Recipe getRecipe(Long recipeId) {
		Recipe recipe = null;
		for (Recipe recipeFromRepo : recipeRepository) {
			if (recipeFromRepo.getId().equals(recipeId)) {
				recipe = recipeFromRepo;
				break;
			}
		}
		return recipe;
	}
	
	public ArrayList<Recipe> getAllRecipe() {
		return recipeRepository;
	}
	
	
	
}
