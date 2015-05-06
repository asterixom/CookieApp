package de.cookieapp.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INGREDIENT")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INGREDIENTID")
	private Long id;

	@Column(name = "QUANTITY")
	private double quantity;

	@Column(name = "UNIT")
	private String unit;

	@OneToMany
	private Set<Nutrient> nutrients = new HashSet<Nutrient>();

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Nutrient> getNutrients() {
		return nutrients;
	}

	public void setNutrients(Set<Nutrient> nutrients) {
		this.nutrients = nutrients;
	}

	public Ingredient(Long id, double quantity, String unit,
			Set<Nutrient> nutrients) {
		this.id = id;
		this.quantity = quantity;
		this.unit = unit;
		this.nutrients = nutrients;
	}

}
