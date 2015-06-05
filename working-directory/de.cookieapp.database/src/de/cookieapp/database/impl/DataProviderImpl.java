package de.cookieapp.database.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;
import de.cookieapp.data.service.DataProvider;
import de.cookieapp.database.test.DummyDataCreator;

public class DataProviderImpl implements DataProvider {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();

	public void createDummyData() {

		DummyDataCreator dataCreator = new DummyDataCreator(this);
		dataCreator.createDummyData();

	}

	public List<User> getUsers() {
		entityManager.getTransaction().begin();
		List<?> obj = entityManager.createQuery("from " + UserImpl.class.getName()).getResultList();
		List<User> users = extractUsers(obj);
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
		User user = entityManager.find(UserImpl.class, userID);
		if (user != null) {
			entityManager.remove(user);
		}
		entityManager.getTransaction().commit();
	}

	public long getUserID(String mailOrName) {
		long id = 0;
		String mailOrNameLowerCase = mailOrName.toLowerCase();
		Query query = this.entityManager.createQuery("from " + UserImpl.class.getName() + " s where lower(s.eMail)='" + mailOrNameLowerCase + "' OR lower(s.name)='" + mailOrNameLowerCase + "'");
		List<?> usersFromQuery = query.getResultList();
		if (usersFromQuery.size() == 1	&& usersFromQuery.get(0) instanceof UserImpl) {
			id = ((UserImpl) usersFromQuery.get(0)).getId();
		}
		return id;
	}

	public User getUser(long userID) {
		User user = entityManager.find(UserImpl.class, userID);
		return user;
	}

	public boolean changePassword(long userID, String oldPassword,
			String newPassword) {
		boolean flag = false;
		entityManager.getTransaction().begin();
		UserImpl user = entityManager.find(UserImpl.class, userID);
		if (user.getPassword().equals(oldPassword) && newPassword != null
				&& newPassword.length() > 0) {
			user.setPassword(newPassword);
			entityManager.merge(user);
			System.out.println("Password changed successfully");
			flag = true;
		} else {
			System.err.println("Failed changing password");
		}
		entityManager.getTransaction().commit();
		return flag;
	}

	public void changeRecipeName(long recipeID, String newRecipeName) {
		entityManager.getTransaction().begin();
		Recipe recipe = entityManager.find(RecipeImpl.class, recipeID);
		if (recipe != null && newRecipeName != null
				&& newRecipeName.length() > 0) {
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
		List<?> obj = entityManager.createQuery(
				"from " + RecipeImpl.class.getName()).getResultList();
		List<Recipe> recipes = extractRecipes(obj);
		entityManager.getTransaction().commit();
		return recipes;
	}

	/**
	 * Returns true, if the Database contains the Recipe
	 * 
	 * @param recipe
	 *            the recipe to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(Recipe recipe) {
		List<?> recipeList = entityManager.createQuery("from " + RecipeImpl.class.getName() + " s where s.name='" + recipe.getName() + "'").getResultList();
		return !recipeList.isEmpty();
	}

	/**
	 * Returns true, if the Database contains the User
	 * 
	 * @param user
	 *            the user to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(User user) {
		List<?> userList = entityManager.createQuery("from " + UserImpl.class.getName() + " s where s.eMail='" + user.geteMail() + "'").getResultList();
		List<?> userList2 = entityManager.createQuery( "from " + UserImpl.class.getName() + " s where s.name='"	+ user.getName() + "'").getResultList();
		return !(userList.isEmpty() && userList2.isEmpty());
	}
	
	/**
	 * Returns true, if the Database contains the Comment
	 * @param commentContent the Commentary as String
	 * @param user the user to look for
	 * @param recipe the Recipe, that contains the recipe
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(String commentContent, Long userID, Long recipeID) {
		List<?> commentList = entityManager.createQuery("from " + CommentImpl.class.getName() + " s where s.content='" + commentContent + "' AND s.recipeid='" + recipeID + "' AND s.userid='" + userID + "'").getResultList();
		return !commentList.isEmpty();
	}

	public boolean saveRecipe(Recipe recipe, User user) {
		entityManager.getTransaction().begin();
		boolean flag = false;
		if (contains(recipe)) {
			System.out.println("Rezept gibt es schon");
		} else {
			entityManager.persist(recipe);
			user.addRecipe(recipe);
			entityManager.merge(user);
			flag = true;
		}
		entityManager.getTransaction().commit();
		return flag;
	}

	public long getRecipeID(String rezeptName) {
		long id = 0;
		List<?> recipes = entityManager.createQuery(
				"from " + RecipeImpl.class.getName() + " s where s.name='"
						+ rezeptName + "'").getResultList();
		if (recipes.size() == 1 && recipes.get(0) instanceof RecipeImpl) {
			id = ((RecipeImpl) recipes.get(0)).getId();
		}
		return id;
	}

	public Recipe getRecipe(long recipeID) {
		Recipe temp = new RecipeImpl();
		temp = entityManager.find(RecipeImpl.class, recipeID);
		return temp;

	}

	public boolean login(String eMailorName, String password) {
		entityManager.getTransaction().begin();
		UserImpl user = entityManager.find(UserImpl.class, getUserID(eMailorName));
		entityManager.getTransaction().commit();
		if (user != null && user.getPassword() != null) {
			return user.getPassword().equals(password);
		} else {
			return false;
		}
	}

	public void saveComment(String content, UserImpl user, RecipeImpl recipe) {
		entityManager.getTransaction().begin();
		Comment comment;
		comment = CommentImpl.createComment(content, user, recipe);
		entityManager.persist(comment);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();

	}

	public boolean saveComment(String content, Long userID, Long recipeID) {
		boolean flag = false;
		if (!contains(content, userID, recipeID)) {
			entityManager.getTransaction().begin();
			User user = getUser(userID);
			Recipe recipe = getRecipe(recipeID);
			Comment comment;
			comment = CommentImpl.createComment(content, user, recipe);
			entityManager.persist(comment);
			recipe.addComment(comment);
			entityManager.merge(recipe);
			entityManager.getTransaction().commit();
			flag = true;
		}
		return flag;
	}

	public long getCommentID(String content) {
		long id = 0;
		Query query = this.entityManager.createQuery("from " + CommentImpl.class.getName() + " s where s.content='"	+ content + "'");
		List<?> commentFromQuery = query.getResultList();
		if (commentFromQuery.size() == 1 && commentFromQuery.get(0) instanceof CommentImpl) {
			id = ((CommentImpl) commentFromQuery.get(0)).getId();
		}
		return id;
	}

	public void deleteComment(Long commentID) {
		entityManager.getTransaction().begin();
		Comment comment = entityManager.find(CommentImpl.class, commentID);
		Recipe recipe = entityManager.find(RecipeImpl.class, comment.getRecipeComment().getId());
		recipe.removeComment(comment);
		if (comment != null) {
			entityManager.remove(comment);
			entityManager.merge(recipe);
		}
		entityManager.getTransaction().commit();
	}

	public void saveFavorite(Long recipeID, Long userID) {
		entityManager.getTransaction().begin();
		UserImpl user = entityManager.find(UserImpl.class, userID);
		Recipe recipe = getRecipe(recipeID);
		if (!user.getFavorites().contains(recipe)) {
			user.addFavoriteRecipe(recipe);
			entityManager.merge(user);
		}
		entityManager.getTransaction().commit();
	}

	public void deleteFavorite(Long recipeID, Long userID) {
		entityManager.getTransaction().begin();
		UserImpl user = entityManager.find(UserImpl.class, userID);
		Recipe recipe = getRecipe(recipeID);
		user.deleteFavoriteRecipe(recipe);
		entityManager.merge(user);
		entityManager.getTransaction().commit();
	}

	public void update(User user) {
		entityManager.getTransaction().begin();
		entityManager.merge(user);
		entityManager.getTransaction().commit();
	}

	public void update(Recipe recipe) {
		entityManager.getTransaction().begin();
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();
	}

	public void update(Comment comment) {
		entityManager.getTransaction().begin();
		entityManager.merge(comment);
		entityManager.getTransaction().commit();
	}

	public List<Recipe> compareToRecipeName(String string) {
		entityManager.getTransaction().begin();
		// replace whitespace with wildcard
		string = string.replace(" ", "%");
		List<?> recipeList = entityManager.createQuery("from " + RecipeImpl.class.getName() + " s where lower(s.name) like '%" + string + "%'").getResultList();
		List<Recipe> recipes = extractRecipes(recipeList);
		entityManager.getTransaction().commit();
		return recipes;
	}

	public boolean saveIngredient(Ingredient ingredient, Long recipeID) {
		boolean flag = false;
		if (0 != getIngredientID(ingredient.getName(), getRecipe(recipeID))) {
			entityManager.getTransaction().begin();
			Recipe recipe = getRecipe(recipeID);
			ingredient.setRecipe(recipe);
			entityManager.persist(ingredient);
			recipe.addIngredient(ingredient);
			entityManager.merge(recipe);
			entityManager.getTransaction().commit();
			flag = true;
		}
		return flag;
	}

	public Long getIngredientID(String name, Recipe recipe) {
		Long id = 0L;
		Query query = this.entityManager.createQuery("from "
				+ IngredientImpl.class.getName() + " s where s.name='"
				+ name + "' AND s.recipe='" + recipe.getId() + "'");
		List<?> ingredientFromQuery = query.getResultList();
		if (ingredientFromQuery.size() == 1	&& ingredientFromQuery.get(0) instanceof IngredientImpl) {
			id = ((IngredientImpl) ingredientFromQuery.get(0)).getId();
		}
		return id;

	}

	public void deleteIngredient(Long ingredientID) {
		entityManager.getTransaction().begin();
		Ingredient ing = entityManager.find(IngredientImpl.class, ingredientID);
		Recipe recipe = entityManager.find(RecipeImpl.class, ing.getRecipe().getId());
		entityManager.remove(ing);
		recipe.removeIngredient(ing);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();
	}

	private List<User> extractUsers(List<?> obj) {
		List<User> users = new ArrayList<User>();
		for (Object object : obj) {
			if (object instanceof User) {
				users.add((User) object);
			}
		}
		return users;
	}

	private List<Recipe> extractRecipes(List<?> obj) {
		List<Recipe> recipes = new ArrayList<Recipe>();
		for (Object object : obj) {
			if (object instanceof Recipe) {
				recipes.add((Recipe) object);
			}
		}
		return recipes;
	}

	// TODO Rezepte Strings speichern(Zutaten)
}
