package de.cookieapp.dataimpl;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class RecipesImpl {
	
	@OneToMany
	private RecipeImpl allRecipes;
	
	public RecipesImpl(RecipeImpl allRecipes) {
		this.allRecipes = allRecipes;
	}

	public RecipeImpl getAllRecipes() {
		return allRecipes;
	}

	public void setAllRecipes(RecipeImpl allRecipes) {
		this.allRecipes = allRecipes;
	}	
	
}


