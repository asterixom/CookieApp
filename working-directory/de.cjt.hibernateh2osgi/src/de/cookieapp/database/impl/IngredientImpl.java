package de.cookieapp.database.impl;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;

@Proxy
@Entity
@Table(name = "INGREDIENT")
public class IngredientImpl implements Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INGREDIENTID")
	private Long id;
	
	@Column(name="NAME")
	private String name;

	@Column(name = "QUANTITY")
	private double quantity;

	@Column(name = "UNIT")
	private String unit;
	
	@ManyToOne(targetEntity = RecipeImpl.class)
	@JoinColumn(name = "RECIPEID")
	private Recipe recipe;

	
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

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

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public IngredientImpl(Long id, double quantity, String unit) {
		{
			this.id = id;
			this.quantity = quantity;
			this.unit = unit;
		}
	}
	public IngredientImpl() {
		// TODO Auto-generated constructor stub
	}

	public IngredientImpl createIngredient(double quantity, String unit, String name){
		IngredientImpl temp = new IngredientImpl();
		temp.setQuantity(quantity);
		temp.setUnit(unit);
		temp.setName(name);
		return temp;
	}
	
	public IngredientImpl(Long id, String name, String unit, double quantity, Recipe recipe){
		this.id=id;
		this.name=name;
		this.unit=unit;
		this.quantity=quantity;
		this.recipe=recipe;
	}
	
	
}
