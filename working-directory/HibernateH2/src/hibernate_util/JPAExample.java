package hibernate_util;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

//EclipseLink JPA With H2 Example

public class JPAExample {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();

	public static void main(String[] args) {
		JPAExample example = new JPAExample();

		/*
		 * example.listStudent();
		 * 
		 * System.out.println("After Sucessfully insertion "); Student student1
		 * = example.saveStudent("Sumith"); Student student2 =
		 * example.saveStudent("Anoop"); example.listStudent();
		 * System.out.println("After Sucessfully modification ");
		 * example.updateStudent(student1.getStudentId(), "Sumith Honai");
		 * example.updateStudent(student2.getStudentId(), "Anoop Pavanai");
		 * example.listStudent();
		 * System.out.println("After Sucessfully deletion ");
		 * example.deleteStudent(student2.getStudentId());
		 * example.listStudent();
		 */

		Student student1 = example.saveStudent("Moritz");
		Student student2 = example.saveStudent("Jan");

		Vorlesung vorlesung1 = example.saveVorlesung("Mathe");
		Vorlesung vorlesung2 = example.saveVorlesung("Best Practice");

		student1.addVorlesung(vorlesung1);
		student1.addVorlesung(vorlesung2);
		
		example.listAllVorlesungen(student1);

	}

	public String listAllVorlesungen(Student student) {
		String listVorlesungen = "";
		
		for(Iterator<Vorlesung> iterator = student.getVorlesung().iterator(); iterator.hasNext();){
			
		Vorlesung vorlesung = (Vorlesung) iterator.next();
		System.out.println(vorlesung.getVorlesungName());
			
		

		}

		return "";
	}

	public Student saveStudent(String studentName) {
		Student student = new Student();
		try {
			entityManager.getTransaction().begin();
			student.setStudentName(studentName);
			student = entityManager.merge(student);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
		return student;
	}

	public void listStudent() {
		try {
			entityManager.getTransaction().begin();
			@SuppressWarnings("unchecked")
			List<Student> Students = entityManager.createQuery("from Student")
					.getResultList();
			System.out.println("Listing Students...");
			for (Iterator<Student> iterator = Students.iterator(); iterator
					.hasNext();) {
				Student student = (Student) iterator.next();
				System.out.println(student.getStudentName());

			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	public void updateStudent(Long studentId, String studentName) {
		try {
			entityManager.getTransaction().begin();
			Student student = (Student) entityManager.find(Student.class,
					studentId);
			student.setStudentName(studentName);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	public void deleteStudent(Long studentId) {
		try {
			entityManager.getTransaction().begin();
			Student student = (Student) entityManager.find(Student.class,
					studentId);
			entityManager.remove(student);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
		}
	}

	public Vorlesung saveVorlesung(String vorlesungName) {
		Vorlesung vorlesung = new Vorlesung();
		entityManager.getTransaction().begin();
		vorlesung.setVorlesungName(vorlesungName);
		vorlesung = entityManager.merge(vorlesung);
		entityManager.getTransaction().commit();
		return vorlesung;
	}

}