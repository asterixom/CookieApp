package de.cookieapp.data.model;

import java.util.Date;
import java.util.Set;

public interface User {
	
	public void setRecipes(Set<Recipe> recipes);
	public Set<Recipe> getRecipes();
	public void addRecipe(Recipe recipe);
	public void deleteRecipe(Recipe recipe);

	public void setFavorites(Set<Recipe> favorite);
	public Set<Recipe> getFavorites();
	public void addFavoriteRecipe(Recipe recipe);
	public void deleteFavoriteRecipe(Recipe recipe);
	
	public String getName();
	public void setName(String name);
	
	public String getPassword();
	public void setPassword(String password);
	public boolean checkPassword(String password);
	
	public String geteMail();
	public void seteMail(String eMail);
	
	public Date getCreated();
	public void setCreated(Date created);

	public Long getId();
	public void setId(Long id);
	
	public SecurityClearance getSecurityClearance();
	public void setSecurityClearance(SecurityClearance i);

	public void debugDump();
	public void debugDumpExtended();
	
	public boolean equals(Object object);
}
