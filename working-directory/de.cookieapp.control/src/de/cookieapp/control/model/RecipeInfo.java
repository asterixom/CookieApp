package de.cookieapp.control.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;

public class RecipeInfo {
	
	private Long id;
	private String name;
	private String description;
	private ArrayList<PostInfo> comments;
	//private HashSet<Set<Double, String>> ingredients;
	
	public RecipeInfo(Recipe recipe, double menge){
		comments = new ArrayList<>();
		for(Comment c : recipe.getComments()){
			comments.add(new PostInfo(c.getCreator().getName(), c.getCreated(), c.getText()));
		}
		id = recipe.getId();
		name = recipe.getName();
		description = recipe.getDescription();
		for(Ingredient i : recipe.getIngredients()){
			
		}
	}
	
	public RecipeInfo(Recipe recipe){
		this(recipe,1.0);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<PostInfo> getComments() {
		return comments;
	}
	
	
	
}
