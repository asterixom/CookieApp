package de.cookieapp.control.model;

import java.util.Collection;
import java.util.HashMap;

import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Units;

public class IngredientInfo {

	private String name;
	private Collection<String> names;
	private Units unit;
	private Double quantity;
	
	public IngredientInfo(Ingredient ingredient){
		HashMap<Long, String> namemap = ingredient.getNutrient().getNames();
		name = namemap.get(ingredient.getNameId());
		this.names = namemap.values();
		this.names.remove(name);
		quantity = ingredient.getQuantity();
		unit = ingredient.getUnit();
	}

	public String getName() {
		return name;
	}

	public Collection<String> getNames() {
		return names;
	}

	public Units getUnit() {
		return unit;
	}

	public Double getQuantity() {
		return quantity;
	}
	
}
