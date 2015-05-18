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

	/*
	@OneToMany(mappedBy="creator")
	private Set<Recipe> recipes;

	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="userFavorites")
	private Set<Recipe> favorites;

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

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

	public User(Long id, String name, String password, String eMail,
			Date created /*,Set<Recipe> recipe, Set<Recipe> favorites*/) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.eMail = eMail;
		this.created = created;
		//this.recipes = recipe;
		//this.favorites = favorites;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User createUser(String name, String password, String eMail,
			Date created /*, Set<Recipe> recipe, Set<Recipe> favorites*/) {
		User temp = new User();
		temp.seteMail(eMail);
		temp.setName(name);
		temp.setPassword(password);
		//temp.setFavorites(favorites);
		//temp.setRecipes(recipe);
		temp.setCreated(created);
		return temp;

	}
/*
	public void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	public void deleteRecipe(Recipe recipe) {
		recipes.remove(recipe);
	}
*/
	public void debugDump() {
		System.out.println("Debug: User: Username: [" + this.name + "] + eMail: [" + this.eMail + "] + ID: [" + this.id + "]");
	}
}
