package de.cookieapp.data.model;

import java.util.Date;

public interface Comment {
	
	public Long getId();
	public void setId(Long id);
	
	public String getContent();
	public void setContent(String content);
	
	public Date getCreated();
	public void setCreated(Date created);
	
	public void setCreator(User creator);
	public User getCreator();

	public User getCommentCreator();
	public void setCommentCreator(User commentCreator);
	
	public Recipe getRecipeComment();
	public void setRecipeComment(Recipe recipeComment);
		
	public void debugDump();
}
