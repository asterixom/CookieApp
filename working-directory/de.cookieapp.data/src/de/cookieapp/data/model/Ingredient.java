package de.cookieapp.data.model;


public interface Ingredient {

	

	public abstract Recipe getRecipe();
	
	public abstract void setRecipe(Recipe recipe);

	public abstract double getQuantity();

	public abstract void setQuantity(double quantity);

	public abstract String getUnit();

	public abstract void setUnit(String unit);

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String getName();

	public abstract void setName(String name);
	
	public void debugDump();

}
