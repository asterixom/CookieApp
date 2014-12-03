package de.cookieapp.dataimpl;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Recipes {
	
	@OneToMany
	private Recipe allRecipes;
	
	public Recipes(Recipe allRecipes) {
		this.allRecipes = allRecipes;
	}

	public Recipe getAllRecipes() {
		return allRecipes;
	}

	public void setAllRecipes(Recipe allRecipes) {
		this.allRecipes = allRecipes;
	}	
	
}


