package de.cookieapp.control;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;
import de.cookieapp.data.service.DataProvider;
import de.cookieapp.database.impl.CommentImpl;
import de.cookieapp.database.impl.IngredientImpl;
import de.cookieapp.database.impl.RecipeImpl;
import de.cookieapp.database.impl.UserImpl;

public class DataService {

	private MessageDigest md = null;
	private DataProvider dataProvider;

	public DataService(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
		/*
		try {
			md = MessageDigest.getInstance("SHA512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		 */
	}

	public void setRepository(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public User login(String userOrMail, String password) {
		User user = null;
		/*
		 * Could be implemented differently, if different in Repository
		 */
		if (dataProvider.login(userOrMail, password)) {
			user = dataProvider.getUser(dataProvider.getUserID(userOrMail));
		}
		return user;
	}

	public User register(String username, String password, String mail) {
		User user = null;
		if (dataProvider != null) {
			user = UserImpl.createUser(username, password, mail, null, null, null);
			dataProvider.saveUser(user);
		}
		return user;
	}

	public String makeHash(String text) {
		byte[] hash;
		try {
			hash = md.digest(text.getBytes("UTF-16"));
			return DatatypeConverter.printHexBinary(hash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Recipe getRecipe(Long recipeId) {
		return dataProvider.getRecipe(recipeId);
	}

	public List<Recipe> getRecipesWithName(String name) {
		return dataProvider.compareToRecipeName(name);
	}

	public boolean saveRecipe(String recipeName, String recipeDescription,	User user, ArrayList<String> ingredientNames,
			ArrayList<String> ingredientUnits, ArrayList<String> ingredientQuantity) {
		boolean flag = false;
		Recipe recipe = RecipeImpl.createRecipe(recipeName, recipeDescription, user, null, null);
		boolean saved = dataProvider.saveRecipe(recipe, user);
		if (saved) {
			// otherwise the ingredients will ne added again, if the recipe is already in database
			Long recipeID = dataProvider.getRecipeID(recipeName);
			for (int i = 0; i < ingredientNames.size(); i = i + 1) {
				double quantity = 0.0;
				String unit = ingredientUnits.get(i);
				String name = ingredientNames.get(i);
				try {
					quantity = Double.valueOf(ingredientQuantity.get(i));
				} catch (NumberFormatException e) {
					System.err.println("NumberFormat Exeption. Could not Format the Number for " + name);
					e.printStackTrace();
				}
				Ingredient ingredient = IngredientImpl.createIngredient(quantity, unit, name);
				flag = dataProvider.saveIngredient(ingredient, recipeID);
			}
		}
		return flag;
	}
	
	public boolean saveComment(String commentContent, User user, Recipe recipe) {
		return dataProvider.saveComment(commentContent, dataProvider.getUserID(user.geteMail()), dataProvider.getRecipeID(recipe.getName()));
	}
	
	
}
