package de.cookieapp.database;

import java.util.Date;
import java.util.Set;

public interface Recipe {
	
	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public Date getCreated();

	public void setCreated();

	public User getCreator();

	public void setCreator(User creator);

//	public Set<Recipe> getUserFavorites();
//	public void setUserFavorites(Set<Recipe> userFavorites);
	
	public void addComment(Comment comment);
	
	public void removeComment(Comment comment);
	
	public void addIngredient(Ingredient ingredient);
	
	public void removeIngredient(Ingredient ingredient);
	
	public void setComments(Set<Comment> recipeComments);
	
	public Set<Comment> getComments();

	public void setCreated(Date created);

	public Long getId();

	public void setId(Long id);

//	public void addRecipeToFavorites(Recipe favo);
//
//	public void deleteRecipeFromFavorites(Recipe favo);

	public Recipe createRecipe(String name, String description, User creator);

	public void debugDump();
	
	public void debugDumpExtended();

	public boolean equals(Object object);
}
