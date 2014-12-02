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
public class Recipes {
	
	@OneToMany
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


