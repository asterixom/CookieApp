package de.cookieapp.repositoryImpl;

import java.util.ArrayList;
import java.util.Date;

import de.cookieapp.data.model.User;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.repository.Repository;


public class RepositoryImpl implements Repository {

	ArrayList<User> userRepository;
	ArrayList<Recipe> recipeRepository;
	
	public RepositoryImpl() {
		userRepository = new ArrayList<User>();
		recipeRepository = new ArrayList<Recipe>();
		TestData testDataProvider = new TestData();
		testDataProvider.createTestData(userRepository, recipeRepository);
	}
	
	/**
	 * Returns the User from Repository
	 * @param nameOrMail the Name or the Mail Adress of the User, that is searched for. may be null
	 * @return the User object from name or mail. <Code> Null </Code> if no user is found
	 */
	public User getUser(String userOrMail) {
		User user = null;
		if(userOrMail != null) {
			for (User userFromRepo : userRepository) {
				if (userFromRepo.getName().toLowerCase().equals(userOrMail)) {
					user = userFromRepo;
					break;
				} else if (userFromRepo.getMail().toLowerCase().equals(userOrMail)) {
					user = userFromRepo;
					break;
				}
			}
		}
		return user;
	}
	
	/**
	 * Adds a User with the given name, mailadress and password
	 * @param name the username
	 * @param mail the mailadress
	 * @param password the password
	 */
	public User addUser(String name, String mail, String password) {
		User user = null;
		if (name != null && mail != null && password != null) {
			Date date = new Date();
			date.setTime(System.currentTimeMillis());
			long id = userRepository.size() + 1;
			user = new de.cookieapp.dataimpl.UserImpl(id, name, password, mail, date, null, null);
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
			recipe = new de.cookieapp.dataimpl.RecipeImpl(id, name, description, date, null, null, null);
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

	/**
	 * Returns all Recipes, that contain the given Name
	 */
	@Override
	public ArrayList<Recipe> getRecipesWithName(String name) {
		ArrayList<Recipe> selectedRecipes = new ArrayList<Recipe>();
		for (Recipe recipe : recipeRepository) {
			if (recipe.getName().contains(name)) {
				selectedRecipes.add(recipe);
			}
			
		}
		return selectedRecipes;
	}
	
	
	
}
