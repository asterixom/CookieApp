package hibernate_util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "STUDENT")
public class Student implements java.io.Serializable {
	private static final long serialVersionUID = 1152163752535001241L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STUDENTID")
	private long studentId;
	@Column(name = "STUDENTNAME")
	private String studentName;
	@OneToMany
	private Set<Vorlesung> vorlesungen = new HashSet<Vorlesung>();

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public Set<Vorlesung> getVorlesung() {
		return vorlesungen;
	}

	public void setVorlesung(Set<Vorlesung> vorlesung) {
		this.vorlesungen = vorlesung;
	}
	
	public void besuchtVorlesung(Vorlesung vorlesung){
		
		}
	
	public void addVorlesung(Vorlesung vorlesung){
		vorlesungen.add(vorlesung);
	}
	
	public void deleteVorlesung(Vorlesung vorlesung){
		vorlesungen.remove(vorlesung);
	}

}