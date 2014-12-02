package de.cookieapp.dataimpl;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import de.cookieapp.data.model.Nutrient;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ingredient implements de.cookieapp.data.model.Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="INGREDIENTID")
	private Long id;
	
	@Column(name="NAMES")
	private ArrayList<String> names;
	
	@Column(name="QUANTITY")
	private Double quantity;
	
	@Column(name="UNIT")
	private String unit;
	
	@OneToMany
	private Nutrient nutrient;
	
	public Ingredient(Long id, ArrayList<String> names, Double quantity, String unit, Nutrient nutrient) {
		this.id = id;
		this.names = names;
		this.quantity = quantity;
		this.unit = unit;
		this.nutrient = nutrient;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Nutrient getNutrient() {
		return nutrient;
	}
	public void setNutrient(Nutrient nutrient) {
		this.nutrient = nutrient;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public ArrayList<String> getNames() {
		return names;
	}

	@Override
	public void addName(String name) {
		names.add(name);
		
	}

	@Override
	public void removeName(String name) {
		names.remove(name);		
	}
	
}
