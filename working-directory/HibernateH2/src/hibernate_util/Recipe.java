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
@Table(name = "Recipe")
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

	@OneToOne
	@Column(name = "CREATOR")
	private User creator;

	@OneToMany
	@Column(name = "COMMENTS")
	private Comment comments;

	@OneToMany
	@Column(name = "INGREDIENTS")
	private Ingredient ingredients;

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

	public Comment getComments() {
		return comments;
	}

	public void setComments(Comment comments) {
		this.comments = comments;
	}

	public Ingredient getIngredients() {
		return ingredients;
	}

	public void setIngredients(Ingredient ingredients) {
		this.ingredients = ingredients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe(Long id, String name, String description, Date created,
			User creator, Comment comments, Ingredient ingredients) {
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

}
