package de.cookieapp.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Proxy
@Entity
@Table(name = "RECIPE")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECIPEID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATED")
	private Date created;

	@ManyToOne
	@JoinColumn(name = "USERID")
	private User creator;

	@ManyToMany(mappedBy = "favorites")
	private Set<Recipe> userFavorites;
	
	private Set<Comment> recipeComments;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}


	public void setCreated() {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		this.created = sqlDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Set<Recipe> getUserFavorites() {
		return userFavorites;
	}

	public void setUserFavorites(Set<Recipe> userFavorites) {
		this.userFavorites = userFavorites;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addRecipeToFavorites(Recipe favo) {
		userFavorites.add(favo);
	}

	public void deleteRecipeFromFavorites(Recipe favo) {
		userFavorites.remove(favo);
	}

	public Recipe(Long id, String name, String description, Date created,
			User creator) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.creator = creator;
	}

	public Recipe() {
		// TODO Auto-generated constructor stub
	}

	public Recipe createRecipe(String name, String description, User creator) {
		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setDescription(description);
		recipe.setCreated();
		recipe.setCreator(creator);
		return recipe;
	}

	public void debugDump() {
		System.out.println("Debug: Recipe: RecipeName: [" + this.name
				+ "] + Description: [" + this.description + "] + ID: ["
				+ this.id + "]");
		System.out.print("\t and was created by: ");
		this.creator.debugDump();
	}

	/**
	 * Equals Method for the Recipe, returns true, if the recipe consist the
	 * same data
	 * 
	 * @param object
	 *            the other recipe to be compared
	 * @return true if they consist the same data, false otherwise
	 */
	public boolean equals(Object object) {
		boolean flag = false;
		if (object != null && object instanceof Recipe) {
			Recipe recipe = (Recipe) object;
			if (this.getId().equals(recipe.getId())
					&& this.getName().equals(recipe.getName())
					&& this.getDescription().equals(recipe.getDescription())
					&& this.getCreated().equals(recipe.getCreated())) {
				flag = true;
			}
		}
		return flag;
	}
}
