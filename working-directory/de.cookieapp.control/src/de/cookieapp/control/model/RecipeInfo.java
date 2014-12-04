package de.cookieapp.control.model;

import java.util.ArrayList;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Nutrient;
import de.cookieapp.data.model.Recipe;

public class RecipeInfo {
	
	private Long id;
	private String name;
	private String description;
	private ArrayList<PostInfo> comments;
	private ArrayList<IngredientInfo> ingredients;
	private Double carbon = 0.0, sugar = 0.0, protein = 0.0, fat = 0.0, calorificValue = 0.0;
	
	public RecipeInfo(Recipe recipe, double menge){
		comments = new ArrayList<>();
		ingredients = new ArrayList<>();
		
		
		for(Comment c : recipe.getComments()){
			comments.add(new PostInfo(c.getCreator().getName(), c.getCreated(), c.getText()));
		}
		
		
		id = recipe.getId();
		name = recipe.getName();
		description = recipe.getDescription();
		
		Nutrient nutrient = null;
		double to100g = 1.0;
		for(Ingredient i : recipe.getIngredients()){
			ingredients.add(new IngredientInfo(i));
			to100g = i.getUnit().get100Gramm() * i.getQuantity();
			nutrient = i.getNutrient();
			carbon += nutrient.getCarbon()*to100g;
			sugar += nutrient.getSugar()*to100g;
			protein += nutrient.getProtein()*to100g;
			fat += nutrient.getFat()*to100g;
			calorificValue += nutrient.getCalorificValue()*to100g;
		}
	}
	
	public RecipeInfo(Recipe recipe){
		this(recipe,1.0);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<PostInfo> getComments() {
		return comments;
	}

	public ArrayList<IngredientInfo> getIngredients() {
		return ingredients;
	}

	public Double getCarbon() {
		return carbon;
	}

	public Double getSugar() {
		return sugar;
	}

	public Double getProtein() {
		return protein;
	}

	public Double getFat() {
		return fat;
	}

	public Double getCalorificValue() {
		return calorificValue;
	}
	
	
	
}
