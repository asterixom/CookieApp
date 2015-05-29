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

	public void main() {

		DummyDataCreator dataCreator = new DummyDataCreator(this);
		dataCreator.createDummyData();

	}

	public List<User> getUsers() {
		entityManager.getTransaction().begin();
		List<?> obj = entityManager.createQuery(
				"from " + UserImpl.class.getName()).getResultList();
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
		User user = entityManager.find(UserImpl.class, userID);
		if (user != null) {
			entityManager.remove(user);
		}
		entityManager.getTransaction().commit();
	}

	public long getUserID(String eMail) {
		long id = 0;
		Query query = this.entityManager
				.createQuery("from " + UserImpl.class.getName()
						+ " s where s.eMail='" + eMail + "'");
		List<?> usersFromQuery = query.getResultList();
		if (usersFromQuery.size() == 1
				&& usersFromQuery.get(0) instanceof UserImpl) {
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
	 * 
	 * @param recipe
	 *            the recipe to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(Recipe recipe) {
		List<?> recipeList = entityManager.createQuery(
				"from " + RecipeImpl.class.getName() + " s where s.name='"
						+ recipe.getName() + "'").getResultList();
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
		List<?> userList = entityManager.createQuery(
				"from " + UserImpl.class.getName() + " s where s.eMail='"
						+ user.geteMail() + "'").getResultList();
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

	public boolean login(String eMail, String password) {
		entityManager.getTransaction().begin();
		UserImpl user = entityManager.find(UserImpl.class, getUserID(eMail));
		entityManager.getTransaction().commit();
		// TODO NullPointer, if user not found!!!
		return user.getPassword().equals(password);
	}

	public void saveComment(String content, UserImpl user, RecipeImpl recipe) {
		entityManager.getTransaction().begin();
		CommentImpl comment = new CommentImpl();
		comment = comment.createComment(content, user, recipe);
		entityManager.persist(comment);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();

	}

	public void saveComment(String content, Long userID, Long recipeID) {
		entityManager.getTransaction().begin();
		User user = getUser(userID);
		Recipe recipe = getRecipe(recipeID);
		Comment comment = new CommentImpl();
		comment = comment.createComment(content, user, recipe);
		entityManager.persist(comment);
		recipe.addComment(comment);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();
	}

	public long getCommentID(String content) {
		long id = 0;
		Query query = this.entityManager.createQuery("from "
				+ CommentImpl.class.getName() + " s where s.content='"
				+ content + "'");
		List<?> commentFromQuery = query.getResultList();
		if (commentFromQuery.size() == 1
				&& commentFromQuery.get(0) instanceof CommentImpl) {
			id = ((CommentImpl) commentFromQuery.get(0)).getId();
		}
		return id;
	}

	public void deleteComment(Long commentID) {
		entityManager.getTransaction().begin();
		CommentImpl comment = entityManager.find(CommentImpl.class, commentID);
		RecipeImpl recipe = entityManager.find(RecipeImpl.class, comment
				.getRecipeComment().getId());
		recipe.removeComment(comment);
		if (comment != null) {
			entityManager.remove(comment);
			entityManager.merge(recipe);
			System.out.println("Kommentar erfolgreich gelöscht");
		}
		entityManager.getTransaction().commit();
	}

	public void saveFavorite(Long recipeID, Long userID) {
		entityManager.getTransaction().begin();
		UserImpl user = entityManager.find(UserImpl.class, userID);
		;
		Recipe recipe = getRecipe(recipeID);
		user.addFavoriteRecipe(recipe);
		entityManager.merge(user);
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
		List<?> recipeList = entityManager.createQuery(
				"from " + RecipeImpl.class.getName() + " s where s.name='"
						+ string + "'").getResultList();
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		for (Object object : recipeList) {
			if (object instanceof Recipe) {
				recipes.add((Recipe) object);
			}
		}
		return recipes;
	}

	public void saveIngredient(Ingredient ingredient, Long recipeID) {
		entityManager.getTransaction().begin();
		Recipe recipe = getRecipe(recipeID);
		entityManager.persist(ingredient);
		recipe.addIngredient(ingredient);
		entityManager.merge(recipe);
		entityManager.getTransaction().commit();
	}

	// TODO Rezepte Strings speichern(Zutaten)
}
