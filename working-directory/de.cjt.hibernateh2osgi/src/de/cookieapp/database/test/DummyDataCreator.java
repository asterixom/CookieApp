package de.cookieapp.database.test;

import java.util.ArrayList;

import de.cookieapp.database.impl.CommentImpl;
import de.cookieapp.database.impl.RecipeImpl;
import de.cookieapp.database.impl.UserImpl;
import de.cookieapp.database.DataProvider;
import de.cookieapp.database.Recipe;
import de.cookieapp.database.User;

public class DummyDataCreator {

	private DataProvider dataProvider;

	private ArrayList<String> mailAdresses = new ArrayList<String>();
	private ArrayList<String> userNames = new ArrayList<String>();
	private ArrayList<String> passwords = new ArrayList<String>();
	private ArrayList<String> recipeNames = new ArrayList<String>();
	private ArrayList<String> recipeDescription = new ArrayList<String>();
	private ArrayList<String> commentContents = new ArrayList<String>();

	/**
	 * Flag, that represents, if the Debug Dump should be written or not
	 */
	boolean debug = false;

	public DummyDataCreator(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
		fillArrayLists();
	}

	public void createDummyData() {
		createDummyUser();
		createDummyRecipe();
		createDummyFavorites();
		createDummyComment();
		printExtendedUserData();
		printExtendedRecipeData();
	}

	private void printExtendedUserData() {
		User user;
		for (int i = 0; i < mailAdresses.size(); i = i + 1) {
			user = dataProvider.getUser(dataProvider.getUserID(mailAdresses
					.get(i)));
			System.out.println();
			user.debugDumpExtended();
		}
	}

	private void printExtendedRecipeData() {
		Recipe recipe;
		for (int i = 0; i < recipeNames.size(); i = i + 1) {
			recipe = dataProvider.getRecipe(dataProvider
					.getRecipeID(recipeNames.get(i)));
			System.out.println();
			recipe.debugDumpExtended();
		}
	}

	private void createDummyUser() {
		User user = new UserImpl();
		for (int i = 0; i < mailAdresses.size(); i = i + 1) {
			user = user.createUser(userNames.get(i), passwords.get(i),
					mailAdresses.get(i), null, null, null);
			dataProvider.saveUser(user);
			if (debug) {
				user.debugDump();
			}
		}
	}

	private void createDummyRecipe() {
		if (recipeNames.size() == recipeDescription.size()) {
			Recipe recipe = new RecipeImpl();
			User user;
			for (int i = 0; i < userNames.size(); i = i + 1) {
				user = dataProvider.getUser(dataProvider.getUserID(mailAdresses
						.get(i)));
				for (int j = i * 2; j < (i * 2) + 2; j = j + 1) {
					recipe = recipe.createRecipe(recipeNames.get(j),
							recipeDescription.get(j), user);
					dataProvider.saveRecipe(recipe, user);
					if (debug) {
						recipe.debugDump();
					}
				}
			}
		}
	}

	private void createDummyFavorites() {
		for (int i = 0; i < mailAdresses.size(); i = i + 1) {
			Long user = dataProvider.getUserID(mailAdresses.get(i));
			int numberOfFavorites = (int) (Math.random() * recipeNames.size());
			for (int j = 0; j < numberOfFavorites; j = j + 1) {
				int favoriteRecipe = (int) (Math.random() * recipeNames.size());
				dataProvider.saveFavorite(dataProvider.getRecipeID(recipeNames
						.get(favoriteRecipe)), user);
			}
		}
	}

	private void createDummyComment() {
		/*
		 * ArrayList<String> reverseMailAdresses = new ArrayList<String>(); for
		 * (int i = mailAdresses.size(); i >= 0 ; i = i - 1) {
		 * reverseMailAdresses.add(mailAdresses.get(i)); }
		 */
		CommentImpl comment = new CommentImpl();
		long user;
		for (int i = 0; i < recipeNames.size(); i = i + 1) {
			int randomUser = (int) (Math.random() * mailAdresses.size());
			user = dataProvider.getUserID(mailAdresses.get(randomUser));
			for (int j = i * 2; j < (i * 2) + 2; j = j + 1) {
				comment = comment.createComment(commentContents.get(j),
						dataProvider.getUser(user), dataProvider
								.getRecipe(dataProvider.getRecipeID(recipeNames
										.get(i))));
				dataProvider.saveComment(commentContents.get(j), user,
						dataProvider.getRecipeID(recipeNames.get(i)));
				if (debug) {
					comment.debugDump();
				}
			}
		}
	}

	/**
	 * Fills the ArrayLists with some Data to be used by the create Dummy Data
	 * Methods
	 */
	private void fillArrayLists() {
		mailAdresses.add("Moritz.gabriel@gmx.de");
		mailAdresses.add("Heinrich.Braun@SAP.de");
		mailAdresses.add("Foo.Bar@4711.de");

		userNames.add("Moritz");
		userNames.add("Heinrich");
		userNames.add("Karl-Heinz");

		passwords.add("test1");
		passwords.add("test2");
		passwords.add("test3");

		recipeNames.add("Burger");
		recipeNames.add("Spaghetti");
		recipeNames.add("Lasagne");
		recipeNames.add("Pizza");
		recipeNames.add("French Fries");
		recipeNames.add("Monster Lasagne");

		recipeDescription.add("can be added by French Fries");
		recipeDescription.add("Some Meat");
		recipeDescription.add("More Meat");
		recipeDescription.add("Flour, Salami and Cheese");
		recipeDescription.add("French, Potatos and Fat");
		recipeDescription.add("Monster Flesh Muhaahaa");

		commentContents.add("Tastes good with Fries");
		commentContents.add("Fast food for the Win!");

		commentContents.add("Long Noodles");
		commentContents.add("Longest Noodles");

		commentContents.add("Gigantic Noodleplates and some good meet");
		commentContents.add("Tastes Delicious, dont forget the Cheese");

		commentContents.add("An Italian and a StoneOven. Superb");
		commentContents.add("Italien-Way of Life");

		commentContents.add("Fried for your delight");
		commentContents.add("Fried French, what could go wrong");

		commentContents.add("Bullshit, ich kann nichts hörn!!!");
		commentContents.add("Eat that Shit");
	}

}
