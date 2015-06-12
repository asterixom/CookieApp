package de.cookieapp.data.service;

import java.util.List;

import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;

public interface DataProvider {
	
	public boolean login(String eMail, String password);
	
	public void saveUser(User user);
	
	public long getUserID(String eMail);
	
	public User getUser(long userID);
	
	public boolean saveRecipe(Recipe recipe, User user);
	
	public long getRecipeID(String rezeptName);
	
	public Recipe getRecipe(long recipeID);
	
	public void saveFavorite(Long recipeID, Long userID);
	
	public boolean saveComment(String content, Long userID, Long recipeID);
	
	public boolean changePassword(long userID, String oldPassword, String newPassword);
	
	public boolean saveIngredient(Ingredient ingredient, Long recipeID);
	
	public void deleteIngredient(Long ingredientID);
	
	public Long getIngredientID(String name, Recipe recipe);
	
	public List<Recipe> compareToRecipeName(String string);
	
	public boolean isFavorite(Long recipeID, Long userID);
	
	public void deleteFavorite(Long recipeID, Long userID);

}
