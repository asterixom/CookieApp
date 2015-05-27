package de.cookieapp.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NUTRIENT")
public class Nutrient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NUTRIENTID")
	private Long id;

	@Column(name = "CALORIFICVALUE")
	private double calorificValue;

	@Column(name = "CARBOHYDRATES")
	private double carbohydrates;

	@Column(name = "SUGAR")
	private double sugar;

	@Column(name = "PROTEIN")
	private double protein;

	@Column(name = "FAT")
	private double fat;
	
	@ManyToOne
	private Nutrient nutrient;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Nutrient getNutrient() {
		return nutrient;
	}

	public void setNutrient(Nutrient nutrient) {
		this.nutrient = nutrient;
	}

	public double getCalorificValue() {
		return calorificValue;
	}

	public void setCalorificValue(double calorificValue) {
		this.calorificValue = calorificValue;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public double getSugar() {
		return sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public Nutrient(Long id, double calorificValue, double carbohydrates,
			double sugar, double protein, double fat) {
		this.id = id;
		this.calorificValue = calorificValue;
		this.carbohydrates = carbohydrates;
		this.sugar = sugar;
		this.protein = protein;
		this.fat = fat;
	}

	public Nutrient() {
		// TODO Auto-generated constructor stub
	}

}
