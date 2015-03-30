package hibernate.cookieapp.classes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Nutrient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="NUTRIENTID")
	private Long id;
	
	@Column(name="CALORIFICVALUE")
	private double calorificValue;
	
	@Column(name="CARBOHYDRATES")
	private double carbohydrates;
	
	@Column(name="SUGAR")
	private double sugar;
	
	@Column(name="PROTEIN")
	private double protein;
	
	@Column(name="FAT")
	private double fat;
	
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
	
	

}
