package de.cookieapp.data.model;

import java.util.ArrayList;

public interface Ingredient {

	public ArrayList<String> getNames();
	public void addName(String name);
	public void removeName(String name);
	
	public Double getQuantity();
	public void setQuantity(Double quantity);
	
	public String getUnit();
	public void setUnit(String unit);
	
	public Nutrient getNutrient();
	public void setNutrient(Nutrient nutrient);
}
