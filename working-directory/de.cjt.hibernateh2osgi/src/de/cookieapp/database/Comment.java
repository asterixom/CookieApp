package de.cookieapp.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENT")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMMENTID")
	private Long id;

	@Column(name = "CONTENT", length = 1000)
	private String content;

	@Column(name = "CREATED", nullable = false)
	private Date created;
	
	private User commentCreator;
	
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

	public Comment(Long id, String content, User creator, Date created) {
		this.id = id;
		this.content = content;
		this.commentCreator = creator;
		this.created = created;
	}

	public Comment() {
		// TODO Auto-generated constructor stub
	}

}
