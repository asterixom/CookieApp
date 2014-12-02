package de.cookieapp.control.model;

import java.util.ArrayList;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Recipe;

public class RecipeInfo {
	
	private Long id;
	private String name;
	private String description;
	private ArrayList<PostInfo> comments;
	
	public RecipeInfo(Recipe recipe){
		comments = new ArrayList<>();
		for(Comment c : recipe.getComments()){
			comments.add(new PostInfo(c.getCreator().getName(), c.getCreated(), c.getText()));
		}
		id = recipe.getId();
		name = recipe.getName();
		description = recipe.getDescription();
	}
	
}
