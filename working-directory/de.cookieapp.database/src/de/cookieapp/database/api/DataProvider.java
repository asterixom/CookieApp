package de.cookieapp.database.api;

import java.util.List;

import de.cookieapp.database.Recipe;
import de.cookieapp.database.User;

public interface DataProvider {

	public boolean login(String eMail, String password);

	public List<User> listAllUsers();

	public boolean isUserAlreadySaved(User user);

	public void saveUser(User user);

	public void deleteUser(Long userID);

	public long getUserID(String eMail);

	public User getUser(long userID);

	public void aenderePasswort(long userID, String password);

	public List<Recipe> listAllRecipe();

	public boolean isRecipeAlreadySaved(Recipe recipe);

	public void saveRecipe(Recipe recipe);

	public long getRecipeID(String rezeptName);

	public Recipe getRecipe(long recipeID);

}
