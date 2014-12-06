package de.cookieapp.repository;

import java.util.ArrayList;
import java.util.Date;

import de.cookieapp.dataimpl.Recipe;
import de.cookieapp.dataimpl.User;

public interface Repository {

	public User getUser(String name, String mail);
	public User addUser(String name, String mail, String password);
	public Recipe addRecipe(String name, String description, Date created, User creator);
	public Recipe getRecipe(Long recipeId);
	public ArrayList<Recipe> getRecipesWithName(String name);
}
