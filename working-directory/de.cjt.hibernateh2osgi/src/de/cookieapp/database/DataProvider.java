package de.cookieapp.database;

public interface DataProvider {
	
	public boolean login(String eMail, String password);
	
	public void saveUser(User user);
	
	public long getUserID(String eMail);
	
	public User getUser(long userID);
	
	public void saveRecipe(Recipe recipe, User user);
	
	public long getRecipeID(String rezeptName);
	
	public Recipe getRecipe(long recipeID);
	
	public void addRecipeToFavorites(long recipeID, long userID);
	
	public void saveFavorite(Long recipeID, Long userID);
	
	public void saveComment(String content, Long userID, Long recipeID);
	
	public boolean changePassword(long userID, String oldPassword, String newPassword);

}
