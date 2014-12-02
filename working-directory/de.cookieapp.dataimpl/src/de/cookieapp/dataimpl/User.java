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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// für jede Klasse wird eine neue Tabelle erstellt
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "USERID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String eMail;

	@Column(name = "CREATED")
	private Date created;

	@OneToMany
	private ArrayList<Recipe> recipe;

	@OneToMany
	private ArrayList<Recipe> favorites;
	/**
	 * 
	 * @param id
	 * @param name
	 * @param password
	 * @param eMail
	 * @param created
	 * @param recipe
	 * @param favorites
	 */
	public User(Long id, String name, String password, String eMail,
			Date created, ArrayList<Recipe> recipe, ArrayList<Recipe> favorites) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.eMail = eMail;
		this.created = created;
		this.recipe = recipe;
		this.favorites = favorites;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public ArrayList<Recipe> getRecipes() {
		return recipe;
	}

	public void addRecipe(Recipe recipe) {
		this.recipe.add(recipe);
	}

	public ArrayList<Recipe> getFavorites() {
		return favorites;
	}

	public void addFavorites(Recipe favorites) {
		this.favorites.add(favorites);
	}

	public Long getId() {
		return id;
	}

}
