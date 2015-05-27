package de.cookieapp.database;

import java.util.Date;

public interface Comment {

	public void setContent(String content);

	public void setCreated(Date created);

	public void setCreator(User creator);

	public void setId(Long id);

	public String getContent();

	public Date getCreated();

	public User getCreator();

	public Long getId();

	public User getCommentCreator();

	public void setCommentCreator(User commentCreator);

	public Recipe getRecipeComment();

	public void setRecipeComment(Recipe recipeComment);
	
	public Comment createComment(String content, User creator, Recipe recipe);
	
	public void debugDump();
}
