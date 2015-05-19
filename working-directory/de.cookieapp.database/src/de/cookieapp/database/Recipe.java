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

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER", joinColumns = { @JoinColumn(name = "USERID") }, 
	inverseJoinColumns = { @JoinColumn(name = "RECIPEID") })
	private Set<Recipe> userFavorites;
	
	@OneToMany
	private Set<Comment> comments;

	@OneToMany
	private Set<Ingredient> ingredients;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addRecipeToFavorites(Recipe favo){
		userFavorites.add(favo);
	}
	
	public void deleteRecipeFromFavorites(Recipe favo){
		userFavorites.remove(favo);
	}

	public Recipe(Long id, String name, String description, Date created,
			User creator, Set<Comment> comments, Set<Ingredient> ingredients) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.creator = creator;
		this.comments = comments;
		this.ingredients = ingredients;
	}

	public Recipe() {
		// TODO Auto-generated constructor stub
	}

	public Recipe createRecipe(String name, String description, User creator) {
		Recipe temprecipe = new Recipe();
		temprecipe.setName(name);
		temprecipe.setDescription(description);
		temprecipe.setCreated();
		temprecipe.setCreator(creator);
		temprecipe.setComments(new HashSet<Comment>());
		temprecipe.setIngredients(new HashSet<Ingredient>());
		return temprecipe;
	}

	public void debugDump() {
		System.out.println("Debug: Recipe: RecipeName: [" + this.name
				+ "] + Description: [" + this.description + "] + ID: ["
				+ this.id + "]");
	}

}