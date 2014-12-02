package de.cookieapp.data.model;

import java.util.ArrayList;
import java.util.Date;

public interface Recipe {

	public long getId();
	
	public String getName();
	public void setName(String name);
	
	public String getDescription();
	public void setDescription(String description);
	
	public Date getCreated();
	public User getCreator();
	
	public ArrayList<Ingredient> getIngredients();
	public void addIngredient(Ingredient ingredient);
	public void removeIngredient(Ingredient ingredient);
	
	public ArrayList<Comment> getComments();
	public void addComment(Comment comment);
	public void removeComment(Comment comment);
	
	public ArrayList<Recommendation> getRecommendations();
	public void addRecommendation(Recommendation recommendation);
	public void removeRecommendation(Recommendation recommendation);
}
