package de.cookieapp.data.model;

import java.util.Date;
import java.util.Set;

import de.cookieapp.data.model.Ingredient;

public interface Recipe {
	
	public Long getId();
	public void setId(Long id);
	
	public String getName();
	public void setName(String name);

	public String getDescription();
	public void setDescription(String description);

	public void setCreated(Date created);
	public Date getCreated();

	public User getCreator();
	public void setCreator(User creator);
	
	/*
	public Set<Recipe> getUserFavorites();
	public void setUserFavorites(Set<Recipe> userFavorites);
	public void addRecipeToFavorites(Recipe favo);
	public void deleteRecipeFromFavorites(Recipe favo);
	*/
	
	public void addComment(Comment comment);
	public void removeComment(Comment comment);
	
	public void setComments(Set<Comment> recipeComments);
	public Set<Comment> getComments();
	
	public Set<Ingredient> getIngredients();
	public void addIngredient(Ingredient ingredient);
	public void removeIngredient(Ingredient ingredient);
	
	/*
	public Set<String> getRecommendations();
	public void addRecommendation(String recommendation);
	public void removeRecommendation(String recommendation);
	 */
	
	public void debugDump();	
	public void debugDumpExtended();

	public boolean equals(Object object);
}
