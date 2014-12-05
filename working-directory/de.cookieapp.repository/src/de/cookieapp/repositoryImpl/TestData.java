package de.cookieapp.repositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.cookieapp.data.model.Units;
import de.cookieapp.dataimpl.Ingredient;
import de.cookieapp.dataimpl.Nutrient;
import de.cookieapp.dataimpl.Recipe;
import de.cookieapp.dataimpl.User;

public class TestData {
	
	public void createTestData(ArrayList<User> userRepository, ArrayList<Recipe> recipeRepository) {
		createUsers(userRepository);
		createRecipes(recipeRepository, userRepository);
	};
	
	private void createUsers(ArrayList<User> userRepository) {
		Date date = new Date(System.currentTimeMillis());
		userRepository.add(new User(1L, "Chris", "Test1", "Test1@provider.de", date, null, null));
		date.setTime(date.getTime() + 1000L);
		userRepository.add(new User(2L, "Oliver", "Test2", "Test2@provider.de", date, null, null));
		date.setTime(date.getTime() + 1000L);
		userRepository.add(new User(3L, "Jan", "Test3", "Test3@provider.de", date, null, null));
		date.setTime(date.getTime() + 1000L);
		userRepository.add(new User(4L, "Moritz", "Test4", "Test4@provider.de", date, null, null));
	}
	
	private void createRecipes(ArrayList<Recipe> recipeRepository, ArrayList<User> userRepository) {
		Date date = new Date(System.currentTimeMillis());
		Units gram = Units.g;
		Units kilo = Units.kg;
		HashMap<Long, String> saltNames = new HashMap<Long, String>();
		saltNames.put(0L, "Salz");
		saltNames.put(1L, "Salt");
		saltNames.put(2L, "MeerSalz");
		Nutrient saltNutrient = new Nutrient(1L, saltNames, 24.0, 100.0, 0.0, 5.0, 1.0);
		ArrayList<de.cookieapp.data.model.Ingredient> ingredients = new ArrayList<de.cookieapp.data.model.Ingredient>();
		ingredients.add(new Ingredient(1L, 100.0, gram, saltNutrient));		
		HashMap<Long, String> shugarNames = new HashMap<Long, String>();
		shugarNames.put(0L, "Zucker");
		shugarNames.put(1L, "Salt");
		shugarNames.put(2L, "MeerSalz");
		Nutrient shugarNutrient = new Nutrient(1L, shugarNames, 25.0, 50.0, 100.0, 15.0, 12.0);
		ingredients.add(new Ingredient(1L, 100.0, kilo, saltNutrient));	
		ingredients.add(new Ingredient(1L, 100.0, gram, shugarNutrient));	
		recipeRepository.add(new Recipe(1L, "Lasagne", "Mach Lasagne", date, userRepository.get(0), null, ingredients));
		date.setTime(date.getTime() + 1000L);
		recipeRepository.add(new Recipe(4L, "Pizza", "Backe mir Pizza", date, userRepository.get(2), null, ingredients));

	}
}
