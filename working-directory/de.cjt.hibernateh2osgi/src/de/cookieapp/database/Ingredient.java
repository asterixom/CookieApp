package de.cookieapp.database;

import de.cookieapp.database.impl.RecipeImpl;

public interface Ingredient {

	public abstract RecipeImpl getRecipeIngredient();

	public abstract void setRecipeIngredient(RecipeImpl recipeIngredient);

	public abstract double getQuantity();

	public abstract void setQuantity(double quantity);

	public abstract String getUnit();

	public abstract void setUnit(String unit);

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract Ingredient createIngredient(double quantity,
			String unit, String name);

}