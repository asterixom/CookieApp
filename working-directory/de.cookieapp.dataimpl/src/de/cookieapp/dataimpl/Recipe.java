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

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Recommendation;
import de.cookieapp.data.model.Ingredient;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Recipe implements de.cookieapp.data.model.Recipe {
	
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
	
	@OneToMany
	private ArrayList<Recommendation> recommendation;
	
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
	public Long getId() {
		return id;
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
	public void removeComment(Comment comment) {
		this.comments.add(comment);
	}
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
	public void removeIngredient(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
	}
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
	public void setRecommendation(ArrayList<Recommendation> recommendation) {
		this.recommendation = recommendation;
	}
	public void addRecommendation(Recommendation recommendation) {
		this.recommendation.add(recommendation);
	}
	public ArrayList<Recommendation> getRecommendations() {
		return recommendation;
	}
	public void removeRecommendation(Recommendation recommendation) {
		this.recommendation.remove(recommendation);	
	}
}
