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

//EclipseLink JPA With H2 Example

public class CookieApp {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();

	public static void main(String[] args) {
		CookieApp cookie = new CookieApp();

		User mo = new User();
		mo = mo.createUser("Moritz", "test", "Moritz.gabriel@gmx,de",
				new Date(), new HashSet<Recipe>(), new HashSet<Recipe>());

		
		
		List<User> test = cookie.listAllUsers();
		
		System.out.println(test.get(0).getId());
		

	}

	public List<User> listAllUsers() {
		entityManager.getTransaction().begin();
		

		@SuppressWarnings({ "unused", "unchecked" })
		List<User> usertemp = entityManager.createQuery("from User")
				.getResultList();

		entityManager.getTransaction().commit();
		return usertemp;
	}

	public ArrayList<Recipe> listAllRecipe() {
		entityManager.getTransaction().begin();
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		for (Iterator<Recipe> iterator = recipes.iterator(); iterator.hasNext();) {
			recipes.add(iterator.next());
		}
		entityManager.getTransaction().commit();
		return recipes;
	}

	public void saveUser(User user) {
		entityManager.getTransaction().begin();
		entityManager.persist(user);
		entityManager.getTransaction().commit();
		System.out.println("Erfolg?");
	}

	/*public User getUser(User user) {
		entityManager.getTransaction().begin();

		return temp;
	}*/

	public void deleteUser(User user) {
		entityManager.getTransaction().begin();
		entityManager.remove(user);
		entityManager.getTransaction().commit();
	}

}
