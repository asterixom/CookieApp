package hibernate_cookieapp_classes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	@ManyToOne
	private User creator;

	@Column(name = "CREATED", nullable = false)
	private Date created;

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setCreator(User creator) {
		this.creator = creator;
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
		return creator;
	}

	public Long getId() {
		return id;
	}

	public Comment(Long id, String content, User creator, Date created) {
		this.id = id;
		this.content = content;
		this.creator = creator;
		this.created = created;
	}

}
