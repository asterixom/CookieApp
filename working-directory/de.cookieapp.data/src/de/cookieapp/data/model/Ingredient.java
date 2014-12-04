package de.cookieapp.data.model;


public interface Ingredient {
	
	public Long getNameId();
	public void setNameId(Long id);
	
	public Double getQuantity();
	public void setQuantity(Double quantity);
	
	public Units getUnit();
	public void setUnit(Units unit);
	
	public Nutrient getNutrient();
	public void setNutrient(Nutrient nutrient);
}
