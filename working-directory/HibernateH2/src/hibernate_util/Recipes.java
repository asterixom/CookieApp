package hibernate_util;

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
import javax.persistence.Table;

@Entity
@Table(name="Recipes")
public class Recipes {
	
	@OneToMany
	@Column(name="RECIPES")
	private Recipe allRecipes;

	public Recipe getAllRecipes() {
		return allRecipes;
	}

	public void setAllRecipes(Recipe allRecipes) {
		this.allRecipes = allRecipes;
	}

	public Recipes(Recipe allRecipes) {
		this.allRecipes = allRecipes;
	}
	
	
	
	
}


