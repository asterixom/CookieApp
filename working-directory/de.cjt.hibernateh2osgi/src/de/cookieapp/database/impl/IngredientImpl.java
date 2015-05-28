package de.cookieapp.database.impl;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;


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
	private Recipe recipeIngredient;

	
	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#getRecipeIngredient()
	 */
	@Override
	public Recipe getRecipeIngredient() {
		return recipeIngredient;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#setRecipeIngredient(de.cookieapp.database.impl.RecipeImpl)
	 */
	@Override
	public void setRecipeIngredient(Recipe recipeIngredient) {
		this.recipeIngredient = recipeIngredient;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#getQuantity()
	 */
	@Override
	public double getQuantity() {
		return quantity;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#setQuantity(double)
	 */
	@Override
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#getUnit()
	 */
	@Override
	public String getUnit() {
		return unit;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#setUnit(java.lang.String)
	 */
	@Override
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#setName(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see de.cookieapp.database.impl.Ingredient#createIngredient(double, java.lang.String, java.lang.String)
	 */
	@Override
	public Ingredient createIngredient(double quantity, String unit, String name){
		Ingredient temp = new IngredientImpl();
		temp.setQuantity(quantity);
		temp.setUnit(unit);
		temp.setName(name);
		return temp;
	}
	
	
}
