package de.cookieapp.database.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.cookieapp.data.model.Nutrient;

@Entity
@Table(name = "NUTRIENT")
public class NutrientImpl implements Nutrient {

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
	private NutrientImpl nutrient;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NutrientImpl getNutrient() {
		return nutrient;
	}

	public void setNutrient(NutrientImpl nutrient) {
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

	public NutrientImpl(Long id, double calorificValue, double carbohydrates,
			double sugar, double protein, double fat) {
		this.id = id;
		this.calorificValue = calorificValue;
		this.carbohydrates = carbohydrates;
		this.sugar = sugar;
		this.protein = protein;
		this.fat = fat;
	}

	public NutrientImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Long addName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeName(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getCarbon() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCarbon(double carbon) {
		// TODO Auto-generated method stub
		
	}

}
