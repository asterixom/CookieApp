package de.cookieapp.data.model;

import java.util.HashMap;

public interface Nutrient {

//	public Double carbonhydrate = 0.0;
//	public Double sugar = 0.0;
//	public Double protein = 0.0;
//	public Double fat = 0.0;
//	public Double calorificValue = 0.0;
	
	public HashMap<Long, String> getNames();
	public Long addName(String name);
	public void removeName(Long id);
	
	public Double getCarbon();
	public void setCarbon(Double carbon);
	
	public Double getSugar();
	public void setSugar(Double sugar);

	public Double getProtein();
	public void setProtein(Double Protein);
	
	public Double getFat();
	public void setFat(Double fat);
	
	public Double getCalorificValue();
	public void setCalorificValue(Double calorificValue);
	
}
