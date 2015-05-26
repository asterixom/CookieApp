package de.cookieapp.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.annotations.CreationTimestamp;

import de.cookieapp.database.test.DummyDataCreator;

public class DataProviderImpl implements DataProvider {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();

	public void main() {

		DummyDataCreator dataCreator = new DummyDataCreator(this);
		dataCreator.createDummyData();
		
		/*
		 * Test if Recipes are Created and has User
		printRecepiesFrom(mailadress);
		*/
		
		/*
		 * Checks if Recipes can be renamed
		Long recipeID = getRecipeID("Spaghetti");
		changeRecipeName(recipeID, "Spaghetti2");
		printRecepiesFrom(mailadress);
		 */
		
		//printFavoritesFromUser(mailadress);

		// deleteUser(getUserID(mailadress));
		
//		changePassword(getUserID(mailadress), "test1", "test1234");
//		changePassword(getUserID(mailadress), "test1", "test1234");

		List<User> users = getUsers();
		for (int i = 0; i < users.size(); i++) {
			users.get(i).debugDump();
			System.out.println(users.get(i).getPassword());
		}
		//addRecipeToFavorites(getRecipeID("Spaghetti"), getUserID(mailadress));
	}

	private void printFavoritesFromUser(String mailadress) {
		System.out.println("Debug: Favorites from");
		User user = getUser(getUserID(mailadress));
		user.debugDump();
		System.out.println("are:");
		Set<Recipe> recipes = user.getFavorites();
		Iterator<Recipe> iterReci = recipes.iterator();
		while(iterReci.hasNext()) {
			iterReci.next().debugDump();
		}
	}



	private void printRecepiesFrom(String mailadress) {
		User user = getUser(getUserID(mailadress));		
		Set<Recipe> recipes = user.getRecipes();
		Iterator<Recipe> recipeIterator = recipes.iterator();
		while (recipeIterator.hasNext()) {
			recipeIterator.next().debugDump();
		}
	}

	public List<User> getUsers() {
		entityManager.getTransaction().begin();
		List<?> obj = entityManager.createQuery("from User").getResultList();
		List<User> users = new ArrayList<User>();
		for (Object object : obj) {
			if (object instanceof User) {
				users.add((User) object);
			}
		}
		entityManager.getTransaction().commit();
		return users;
	}

	public void saveUser(User user) {
		entityManager.getTransaction().begin();
		if (contains(user)) {
			System.err.println("User gibt es schon");
		} else {
			entityManager.persist(user);
		}
		entityManager.getTransaction().commit();
	}

	public void deleteUser(Long userID) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		if (user != null) {
			entityManager.remove(user);
		} 
		entityManager.getTransaction().commit();
	}

	public long getUserID(String eMail) {
		long id = 0;
		Query query = this.entityManager.createQuery("from User s where s.eMail='" + eMail + "'");
		List<?> usersFromQuery = query.getResultList();
		if (usersFromQuery.size() == 1 && usersFromQuery.get(0) instanceof User) {
			id = ((User) usersFromQuery.get(0)).getId();
		}
		return id;
	}

	public User getUser(long userID) {
		User user = entityManager.find(User.class, userID);
		return user;
	}

	public void changePassword(long userID, String oldPassword, String newPassword) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		if (user.getPassword().equals(oldPassword) && newPassword != null && newPassword.length() > 0) {
			user.setPassword(newPassword);
			entityManager.merge(user);
			System.out.println("Password changed successfully");
		} else {
			System.err.println("Failed changing password");
		}
		entityManager.getTransaction().commit();
		// TODO add return value, Maybe just Boolean?
	}
	
	public void changeRecipeName(long recipeID, String newRecipeName) {
		entityManager.getTransaction().begin();
		Recipe recipe = entityManager.find(Recipe.class, recipeID);
		if (recipe != null && newRecipeName != null && newRecipeName.length() > 0) {
			recipe.setName(newRecipeName);
			entityManager.merge(recipe);
			System.out.println("Recipename changed successfully");
		} else {
			System.err.println("Failed changing Recipename");
		}
		entityManager.getTransaction().commit();
		// TODO add return value, Maybe just Boolean?
	}


	public List<Recipe> getRecipes() {
		entityManager.getTransaction().begin();
		List<?> obj = entityManager.createQuery("from Recipe").getResultList();
		List<Recipe> recipes = new ArrayList<Recipe>();
		for (Object object : obj) {
			if (object instanceof Recipe) {
				recipes.add((Recipe) object);
			}
		}
		entityManager.getTransaction().commit();
		return recipes;
	}

	/**
	 * Returns true, if the Database contains the Recipe
	 * @param recipe the recipe to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(Recipe recipe) {
		List<?> recipeList = entityManager.createQuery("from Recipe s where s.name='" + recipe.getName() + "'").getResultList();
		return !recipeList.isEmpty();
	}

	/**
	 * Returns true, if the Database contains the User
	 * @param user the user to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(User user) {
		List<?> userList = entityManager.createQuery("from User s where s.eMail='" + user.geteMail() + "'").getResultList();
		return !userList.isEmpty();
	}

	public void saveRecipe(Recipe recipe, User user) {
		entityManager.getTransaction().begin();
		if (contains(recipe)) {
			System.out.println("Rezept gibt es schon");
		} else {
			entityManager.persist(recipe);
			user.addRecipe(recipe);
			entityManager.merge(user);
		}
		entityManager.getTransaction().commit();
	}

	public long getRecipeID(String rezeptName) {
		long id = 0;
		List<?> recipes = entityManager.createQuery("from Recipe s where s.name='" + rezeptName + "'").getResultList();
		if (recipes.size() == 1 && recipes.get(0) instanceof Recipe) {
			id = ((Recipe)recipes.get(0)).getId();
		}
		return id;
	}

	public Recipe getRecipe(long recipeID) {
		Recipe temp = new Recipe();
		temp = entityManager.find(Recipe.class, recipeID);
		return temp;

	}

	
	public void addRecipeToFavorites(long recipeID, long userID){
		entityManager.getTransaction().begin();
		Recipe recipeTemp = getRecipe(recipeID);
		User userTemp = getUser(userID);
		userTemp.addFavoriteRecipe(recipeTemp);
		entityManager.merge(userTemp);

		entityManager.getTransaction().commit();
	}
	

	public boolean login(String eMail, String password) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, getUserID(eMail));
		entityManager.getTransaction().commit();
		return user.getPassword().equals(password);
	}
	
	public void saveComment(String content, User user, Recipe recipe){
		entityManager.getTransaction().begin();
		Comment comment = new Comment();
				comment = comment.createComment(content, user, recipe);
		entityManager.persist(comment);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();
		
	}
	
	public void saveComment(String content, Long userID, Long recipeID){
		entityManager.getTransaction().begin();
		User user = getUser(userID);
		Recipe recipe = getRecipe(recipeID);
		Comment comment = new Comment();
		comment = comment.createComment(content, user, recipe);
		entityManager.persist(comment);
		recipe.addComment(comment);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();
	}
	
	public long getCommentID(String content){
		long id = 0;
		Query query = this.entityManager.createQuery("from Comment s where s.content='" + content + "'");
		List<?> commentFromQuery = query.getResultList();
		if (commentFromQuery.size() == 1 && commentFromQuery.get(0) instanceof Comment) {
			id = ((Comment) commentFromQuery.get(0)).getId();
		}
		return id;
	}
	
	public void saveFavorite(Long recipeID, Long userID) {
		entityManager.getTransaction().begin();
		User user = getUser(userID);
		Recipe recipe = getRecipe(recipeID);
		user.addFavoriteRecipe(recipe);
		entityManager.merge(user);
		entityManager.getTransaction().commit();
	}

}
