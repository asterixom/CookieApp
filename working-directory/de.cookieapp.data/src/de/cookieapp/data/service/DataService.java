package de.cookieapp.data.service;

import java.util.ArrayList;

import de.cookieapp.data.model.*;

public interface DataService {
	
	
	
	public User getUser(String username);
	public User getUserByMail(String mail);
	
	public Recipe getRecipe(String name);
	public Recipe getRecipe(Long id);
	public ArrayList<Recipe> getRecipes();
	
	public Ingredient getIngredient(String name);
	public ArrayList<Ingredient> getIngredients();
	
	public Recipe createRecipe(String name);
	public User createUser(String name);
	public Comment createComment();
	public Ingredient createIngredient(String name); //with Nutrient!!!
	
}
