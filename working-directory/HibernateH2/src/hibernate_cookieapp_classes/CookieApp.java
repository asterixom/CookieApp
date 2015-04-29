package hibernate_cookieapp_classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import hibernate_util.EntityManagerUtil;
import hibernate_util.Student;
import hibernate_util.Vorlesung;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;

//EclipseLink JPA With H2 Example

public class CookieApp {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();

	public static void main(String[] args) {
		CookieApp cookie = new CookieApp();

		User mo = new User();
		mo = mo.createUser("Moritz", "test", "Moritz.gabriel@gmx.de",
				new Date(), new HashSet<Recipe>(), new HashSet<Recipe>());
		/*
		 * User ma = new User(); ma = ma.createUser("Maritz", "test123",
		 * "maritz.gabriel@gmx.de", new Date(), new HashSet<Recipe>(), new
		 * HashSet<Recipe>());
		 */
		//cookie.saveUser(mo);

		 //cookie.deleteUser(cookie.getUserID("Moritz.gabriel@gmx.de"));
		cookie.aenderePasswort(cookie.getUserID("Moritz.gabriel@gmx.de"), "booya");
		 
		//System.out.println(cookie.getUserID("Moritz.gabriel@gmx.de"));
		

		List<User> users = cookie.listAllUsers();
		  for (int i = 0; i < users.size(); i++) {
		  System.out.println(users.get(i).getPassword()); }
		 

	}

	public List<User> listAllUsers() {
		entityManager.getTransaction().begin();

		@SuppressWarnings({ "unchecked" })
		List<User> usertemp = entityManager.createQuery("from User")
				.getResultList();

		entityManager.getTransaction().commit();

		return usertemp;
	}

	public List<Recipe> listAllRecipe() {
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Recipe> recipetemp = entityManager.createQuery("from Recipe")
				.getResultList();
		entityManager.getTransaction().commit();

		return recipetemp;
	}

	public void saveUser(User user) {
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();

		System.out.println("Erfolg?");
	}

	public void deleteUser(Long userID) {
		entityManager.getTransaction().begin();
		User tempuser = entityManager.find(User.class, userID);
		entityManager.remove(tempuser);
		entityManager.getTransaction().commit();
		System.out.println("Erfolg!");

	}

	public long getUserID(String eMail) {
		long id = 0;
		@SuppressWarnings("unchecked")
		List<User> usertemp = entityManager.createQuery(
				"from User s where s.eMail='" + eMail + "'").getResultList();
		if(usertemp.size()==1){
			id = usertemp.get(0).getId();
			System.out.println("User vorhanden!");
		}else{
			System.out.println("User nicht vorhanden");
		}
		return id;
	}
	
	public void aenderePasswort(long userID, String password){
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		user.setPassword(password);
		entityManager.merge(user);
		System.out.println("Passwort erfolgreich geändert");
		entityManager.getTransaction().commit();
	}
}
