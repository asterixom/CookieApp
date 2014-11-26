package de.cookieapp.data.model;

import java.util.ArrayList;
import java.util.Date;

public interface User {

	public ArrayList<Recipe> getRecipes();
	public ArrayList<Recipe> getFavorites();
	
	public void addRecipe(Recipe recipe);
	public void addFavorite(Recipe recipe);
	
	public boolean checkPassword(String password);
	public void setPassword(String password);
	
	public String getMail();
	public void setMail(String mail);
	
	public String getName(String name);
	public void setName(String name); 
	
	public Date getCreated();
}
