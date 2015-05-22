package hibernateh2osgi;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Proxy
@Entity
@Table(name = "USER")
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6643972648923291606L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@OneToMany(mappedBy="creator")
	private Set<Recipe> recipes;

	/*
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="userFavorites")
 	Set<Recipe> favorites;
	 */
	
	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	/*
	public void addFavoriteRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	public void deleteFavoriteRecipe(Recipe recipe) {
		recipes.remove(recipe);
	}
	 */
	
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

	/*
	public Set<Recipe> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Recipe> favorite) {
		this.favorites = favorite;
	}

	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This Method is not for User/Developer use! It is called from the Database/Hibernate, to create a Userobject
	 */
	public User(Long id, String name, String password, String eMail,
			Date created ,Set<Recipe> recipe /*,Set<Recipe> favorites*/) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.eMail = eMail;
		this.created = created;
		this.recipes = recipe;
		//this.favorites = favorites;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This Method is for User/Developer use. It creates a new Userobject and returns the new Instance.
	 * @param name the Username of the User
	 * @param password the Password of the User
	 * @param eMail the Mailadress of the User
	 * @param created the date, in which the User was created. If null, the current Server time will be taken.
	 * @param recipe the List of recipes the User has created. If null, a new Empty Set will be created.
	 * @return
	 */
	public User createUser(String name, String password, String eMail,
			Date created , Set<Recipe> recipe /*, Set<Recipe> favorites*/) {
		User user = new User();
		user.seteMail(eMail);
		user.setName(name);
		user.setPassword(password);
		// user.setFavorites(favorites);
		if (recipe != null) {
			user.setRecipes(recipe);
		} else {
			user.setRecipes(new HashSet<Recipe>());
		}
		if (created != null) {
			user.setCreated(created);
		} else {
			user.setCreated(new Date(System.currentTimeMillis()));
		}
		return user;

	}

	public void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	public void deleteRecipe(Recipe recipe) {
		recipes.remove(recipe);
	}

	public void debugDump() {
		System.out.println("Debug: User: Username: [" + this.name + "] + eMail: [" + this.eMail + "] + ID: [" + this.id + "]");
	}
}
