package de.cookieapp.data.model;

import java.util.HashMap;

public interface Nutrient {
	
	public Long addName(String name);
	public void removeName(Long id);
	
	public double getCarbon();
	public void setCarbon(double carbon);
	
	public double getSugar();
	public void setSugar(double sugar);

	public double getProtein();
	public void setProtein(double Protein);
	
	public double getFat();
	public void setFat(double fat);
	
	public double getCalorificValue();
	public void setCalorificValue(double calorificValue);
	
}
