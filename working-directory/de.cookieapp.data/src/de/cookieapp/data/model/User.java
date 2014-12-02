package de.cookieapp.data.model;

import java.util.ArrayList;
import java.util.Date;

public interface User {
	
	public SecurityClearance getSecurityClearance();
	public void setSecurityClearance(SecurityClearance i);

	public ArrayList<Recipe> getRecipes();
	public void addRecipe(Recipe recipe);	
	
	public ArrayList<Recipe> getFavorites();
	public void addFavorite(Recipe recipe);
	
	public boolean checkPassword(String password);
	public void setPassword(String password);
	
	public String getMail();
	public void setMail(String mail);
	
	public String getName();
	public void setName(String name); 
	
	public Date getCreated();
}
