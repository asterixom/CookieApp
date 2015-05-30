package de.cookieapp.database.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Ingredient;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;

@Proxy
@Entity
@Table(name = "RECIPE")
public class RecipeImpl implements Recipe {

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

	@ManyToOne(targetEntity = UserImpl.class)
	@JoinColumn(name = "USERID")
	private User creator;

	@OneToMany(targetEntity=IngredientImpl.class, mappedBy = "recipe")
	private Set<Ingredient> ingredients;

	@OneToMany(targetEntity = CommentImpl.class, mappedBy = "recipeComment")
	private Set<Comment> recipeComments;

	public Set<Comment> getRecipeComments() {
		return recipeComments;
	}

	public void setRecipeComments(Set<Comment> recipeComments) {
		this.recipeComments = recipeComments;
	}

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

	public void addComment(Comment comment) {
		this.recipeComments.add(comment);
	}

	public void removeComment(Comment comment) {
		this.recipeComments.remove(comment);
	}

	public void setComments(Set<Comment> recipeComments) {
		this.recipeComments = recipeComments;
	}

	public Set<Comment> getComments() {
		return recipeComments;
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


	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void removeIngredient(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
	}

	public Set<Ingredient> getIngredients() {
		return this.ingredients;
	}

	public RecipeImpl(Long id, String name, String description, Date created,
			User creator) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.creator = creator;
		this.recipeComments = new HashSet<Comment>();
		this.ingredients = new HashSet<Ingredient>();
	}

	public RecipeImpl() {
		// TODO Auto-generated constructor stub
	}

	public static Recipe createRecipe(String name, String description, User creator, Set<Ingredient> ingredients) {
		RecipeImpl recipe = new RecipeImpl();
		recipe.setName(name);
		recipe.setDescription(description);
		recipe.setCreated();
		recipe.setCreator(creator);

		recipe.setComments(new HashSet<Comment>());
		if (ingredients != null) { 
			recipe.setIngredients(ingredients); 
		} else { 
			recipe.setIngredients(new HashSet<Ingredient>()); 
		}
		return recipe;
	}

	public void debugDump() {
		System.out.println("Debug: Recipe: RecipeName: [" + this.name
				+ "] + Description: [" + this.description + "] + ID: ["
				+ this.id + "]");
	}

	public void debugDumpExtended() {
		System.out.println("Debug: Recipe: RecipeName: [" + this.name
				+ "] + Description: [" + this.description + "] + ID: ["
				+ this.id + "]");
		System.out.print("\t and was created by:");
		this.creator.debugDump();
		System.out.println("\t and has ingredients");
		Iterator<Ingredient> ingredients = this.getIngredients().iterator();
		if (ingredients.hasNext()) {
			System.out.println("\t and has ingredients");
			while (ingredients.hasNext()) {
				ingredients.next().debugDump();
			}
		}
		Iterator<Comment> comments = this.getComments().iterator();		
		if (comments.hasNext()) {
			System.out.print("\t and has comments:");
			while (comments.hasNext()) {
				comments.next().debugDump();
			}
		}
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
		if (object != null && object instanceof RecipeImpl) {
			RecipeImpl recipe = (RecipeImpl) object;
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
