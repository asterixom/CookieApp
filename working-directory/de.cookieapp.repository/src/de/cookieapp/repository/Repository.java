package de.cookieapp.repository;

import java.util.ArrayList;
import java.util.Date;

import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;

public interface Repository {

	public User getUser(String userOrMail);
	public User addUser(String name, String mail, String password);
	public Recipe addRecipe(String name, String description, Date created, User creator);
	public Recipe getRecipe(Long recipeId);
	public ArrayList<Recipe> getRecipesWithName(String name);
}
