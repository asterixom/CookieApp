package de.cookieapp.dataimpl;


import java.util.ArrayList;
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
public class Recipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="RECIPEID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CREATED")
	private Date created;
	
	@OneToOne
	private User creator;
	
	@OneToOne
	private ArrayList<Comment> comments;
	
	@OneToMany
	private ArrayList<Ingredient> ingredients;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param created
	 * @param creator
	 * @param comments
	 * @param ingredients
	 */
	public Recipe(Long id, String name, String description, Date created,
			User creator, ArrayList<Comment> comments, ArrayList<Ingredient> ingredients) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.created = created;
		this.creator = creator;
		this.comments = comments;
		this.ingredients = ingredients;
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
	public void setCreated(Date created) {
		this.created = created;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public ArrayList<Comment> getComments() {
		return comments;
	}
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public void addIngredients(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
	public Long getId() {
		return id;
	}

}
