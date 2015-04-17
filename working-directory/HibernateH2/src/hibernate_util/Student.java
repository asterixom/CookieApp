package hibernate_util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

}