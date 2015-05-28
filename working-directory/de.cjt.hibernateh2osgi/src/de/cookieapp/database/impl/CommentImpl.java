package de.cookieapp.database.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.cookieapp.data.model.Comment;
import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;


@Entity
@Table(name = "COMMENT")
public class CommentImpl implements Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMMENTID")
	private Long id;

	@Column(name = "CONTENT", length = 1000)
	private String content;

	@Column(name = "CREATED", nullable = false)
	private Date created;
	
	@ManyToOne(targetEntity = UserImpl.class)
	//@JoinColumn(name = "USERID")
	private User commentCreator;
	
	@ManyToOne(targetEntity = RecipeImpl.class)
	@JoinColumn(name="RECIPEID")
	private Recipe recipeComment;

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setCreator(User creator) {
		this.commentCreator = creator;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public Date getCreated() {
		return created;
	}

	public User getCreator() {
		return commentCreator;
	}

	public Long getId() {
		return id;
	}
	
	

	public User getCommentCreator() {
		return commentCreator;
	}

	public void setCommentCreator(User commentCreator) {
		this.commentCreator = commentCreator;
	}

	public Recipe getRecipeComment() {
		return recipeComment;
	}

	public void setRecipeComment(Recipe recipeComment) {
		this.recipeComment = recipeComment;
	}

	public CommentImpl(Long id, String content, User creator, Date created) {
		this.id = id;
		this.content = content;
		this.commentCreator = creator;
		this.created = created;
	}

	public CommentImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentImpl createComment(String content, User creator, Recipe recipe){
		CommentImpl comment = new CommentImpl();
		comment.setContent(content);
		comment.setCreated(new Date());
		comment.setCreator(creator);
		comment.setRecipeComment(recipe);
		return comment;
	}
	
	public void debugDump() {
		System.out.println("Debug: Comment: Creator: [" + this.getCreator().getName() + "] + Created: [" + this.created + "] + Comment: [" + this.content + "]");
	}

}
