package de.cookieapp.dataimpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Nutrient implements de.cookieapp.data.model.Nutrient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="NUTRIENTID")
	private Long id;
	
	@Column(name="CALORIFICVALUE")
	private Double calorificValue;
	
	@Column(name="CARBON")
	private Double carbon;
	
	@Column(name="SUGAR")
	private Double sugar;
	
	@Column(name="PROTEIN")
	private Double protein;
	
	@Column(name="FAT")
	private Double fat;
	
	public Nutrient(Long id, Double calorificValue, Double carbon,
			Double sugar, Double protein, Double fat) {
		this.id = id;
		this.calorificValue = calorificValue;
		this.carbon = carbon;
		this.sugar = sugar;
		this.protein = protein;
		this.fat = fat;
	}
	
	public Double getCalorificValue() {
		return calorificValue;
	}
	public void setCalorificValue(Double calorificValue) {
		this.calorificValue = calorificValue;
	}
	public Double getCarbon() {
		return carbon;
	}
	public void setCarbon(Double carbon) {
		this.carbon = carbon;
	}
	public Double getSugar() {
		return sugar;
	}
	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}
	public Double getProtein() {
		return protein;
	}
	public void setProtein(Double protein) {
		this.protein = protein;
	}
	public Double getFat() {
		return fat;
	}
	public void setFat(Double fat) {
		this.fat = fat;
	}
}
