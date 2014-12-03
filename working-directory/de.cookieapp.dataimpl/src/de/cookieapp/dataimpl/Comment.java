package de.cookieapp.dataimpl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;


/*@Entity			Jede Klasse wird beim Generieren der Tabelle zu einer eigenen Entität
@SequenceGenerator(name="user_id", initialValue=1, allocationSize=1) Bestimmt den Primärschlüssel ; initialValue:= Primärschlüssel beginnt bei 1
																	Primärschlüssel wird immer um 1 hochgezählt (allocationSize)

 Zusätzlich erhält die getter Methode des Primärschlüssels noch ein @Id
 Mit @GeneratedValue wird die Strategie mit der der Primärschlüssel festgelegt wird strategy=GenerationType.auto -> Datenbank kümmert sich darum
 Annotation @Column(name="last_Name", nullable=false)
  mit nullable=false kann festgelegt werden, dass sie Spalten nicht leer sein dürfen.


@Table(name="users") //Legt den Namen der Tabelle fest, in der die Objekte gespeichert werden
*/

//@OneToOne Eins zu eins Beziehung


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// für jede Klasse wird eine neue Tabelle erstellt
public class Comment implements de.cookieapp.data.model.Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "COMMENTID")
	private Long id;
	
	@Column(name="TEXT", length=1000)
	private String text;
	
	@OneToMany
	private User creator;
	
	@Column(name="CREATED", nullable=false)
	private Date created;
	
	public Comment(Long id, String text, User creator, Date created) {
		this.id = id;
		this.text = text;
		this.creator = creator;
		this.created = created;
	}
	
	 public void setText(String text) {
		this.text = text;
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
	 public String getText() {
		return text;
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

	@Override
	public void setCreator(de.cookieapp.data.model.User user) {
		// TODO Auto-generated method stub
		
	}
	
}
