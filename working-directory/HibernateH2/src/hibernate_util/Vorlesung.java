package hibernate_util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "VORLESUNG")
public class Vorlesung {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VORLESUNGID")
	private long vorlesungId;
	@Column(name = "VORLESUNGNAME")
	private String vorlesungName;
	@ManyToOne
	private Student student;
	
	

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public long getVorlesungId() {
		return vorlesungId;
	}

	public void setVorlesungId(long vorlesungId) {
		this.vorlesungId = vorlesungId;
	}

	public String getVorlesungName() {
		return vorlesungName;
	}

	public void setVorlesungName(String vorlesungName) {
		this.vorlesungName = vorlesungName;
	}

}
