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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="INGREDIENTID")
	private Long id;
	
	@Column(name="QUANTITY")
	private double quantity;
	
	@Column(name="UNIT")
	private String unit;
	
	@OneToMany
	private Nutrient nutrients;
	
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
	public Nutrient getNutrients() {
		return nutrients;
	}
	public void setNutrients(Nutrient nutrients) {
		this.nutrients = nutrients;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Ingredient(Long id, double quantity, String unit, Nutrient nutrients) {
		this.id = id;
		this.quantity = quantity;
		this.unit = unit;
		this.nutrients = nutrients;
	}
	
	
	
	

}