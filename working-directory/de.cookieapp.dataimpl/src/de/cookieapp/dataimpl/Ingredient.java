package de.cookieapp.dataimpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import de.cookieapp.data.model.Nutrient;
import de.cookieapp.data.model.Units;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ingredient implements de.cookieapp.data.model.Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="INGREDIENTID")
	private Long id;
	
	@Column(name="QUANTITY")
	private Double quantity;
	
	@Column(name="UNIT")
	private Units unit;
	
	@Column(name="NAMEID")
	private Long nameID;
	
	@OneToMany
	private Nutrient nutrient;
	
	public Ingredient(Long id, Double quantity, Units unit, Nutrient nutrient) {
		this.id = id;
		
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
	public Units getUnit() {
		return unit;
	}
	public void setUnit(Units unit) {
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
	public Long getNameId() {
		return nameID;
	}

	@Override
	public void setNameId(Long id) {
		nameID = id;
	}
	
}
