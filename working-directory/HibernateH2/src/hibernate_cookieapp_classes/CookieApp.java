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

		Recipe re = new Recipe();
		re = re.createRecipe("Lasagne", "blablabla",
				cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de")));
		Recipe ra = new Recipe();
		ra = ra.createRecipe("Spaghetti", "blablabla",
				cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de")));
		Recipe ru = new Recipe();
		ru = ru.createRecipe("Frikadellen", "blablabla",
				cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de")));
		 cookie.saveUser(mo);

		 cookie.saveRecipe(re, cookie.getUserID("Moritz.gabriel@gmx.de"));
		 cookie.saveRecipe(ra, cookie.getUserID("Moritz.gabriel@gmx.de"));
		 cookie.saveRecipe(ru, cookie.getUserID("Moritz.gabriel@gmx.de"));

		// cookie.listAllRecipe();

		User temp = cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de"));

		Set<Recipe> recipes = temp.getRecipes();
		Iterator<Recipe> iter = recipes.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next().getName());
		}

		// User ma = new User(); ma = ma.createUser("Maritz", "test123",
		// "maritz.gabriel@gmx.de", new Date(), new HashSet<Recipe>(), new
		// HashSet<Recipe>());

		// cookie.deleteUser(cookie.getUserID("Moritz.gabriel@gmx.de"));
		// cookie.aenderePasswort(cookie.getUserID("Moritz.gabriel@gmx.de"),
		// "test1234");
		// "booya");

		// System.out.println(cookie.getUserID("Moritz.gabriel@gmx.de"));

		// List<User> users = cookie.listAllUsers();
		// for (int i = 0; i < users.size(); i++) {
		// System.out.println(users.get(i).getPassword());
		// }

		// System.out.println(cookie.isUserAlreadySaved(mo));
	}

	public List<User> listAllUsers() {
		entityManager.getTransaction().begin();

		@SuppressWarnings({ "unchecked" })
		List<User> usertemp = entityManager.createQuery("from User")
				.getResultList();

		entityManager.getTransaction().commit();

		return usertemp;
	}

	public boolean isUserAlreadySaved(User user) {
		@SuppressWarnings("unchecked")
		List<User> usertemp = entityManager.createQuery(
				"from User s where s.eMail='" + user.geteMail() + "'")
				.getResultList();
		if (usertemp.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void saveUser(User user) {
		entityManager.getTransaction().begin();

		if (isUserAlreadySaved(user)) {
			System.out.println("User gibt es schon");
		} else {
			entityManager.persist(user);
		}
		entityManager.getTransaction().commit();
	}

	public void deleteUser(Long userID) {
		entityManager.getTransaction().begin();
		User tempuser = entityManager.find(User.class, userID);
		if (tempuser == null) {

		} else {
			entityManager.remove(tempuser);
		}
		entityManager.getTransaction().commit();
	}

	public long getUserID(String eMail) {
		long id = 0;
		@SuppressWarnings("unchecked")
		List<User> usertemp = entityManager.createQuery(
				"from User s where s.eMail='" + eMail + "'").getResultList();
		if (usertemp.size() == 1) {
			id = usertemp.get(0).getId();
		}

		return id;
	}

	public User getUser(long userID) {
		User temp = new User();
		temp = entityManager.find(User.class, userID);
		return temp;

	}

	public void aenderePasswort(long userID, String password) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		user.setPassword(password);
		entityManager.merge(user);
		System.out.println("Passwort erfolgreich geändert");
		entityManager.getTransaction().commit();
	}

	public List<Recipe> listAllRecipe() {
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<Recipe> recipetemp = entityManager.createQuery("from Recipe")
				.getResultList();
		entityManager.getTransaction().commit();
		System.out.println(recipetemp.get(0).getName());

		return recipetemp;
	}

	public boolean isRecipeAlreadySaved(Recipe recipe) {
		@SuppressWarnings("unchecked")
		List<Recipe> recipetemp = entityManager.createQuery(
				"from Recipe s where s.name='" + recipe.getName() + "'")
				.getResultList();
		if (recipetemp.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void saveRecipe(Recipe recipe, long userID) {
		entityManager.getTransaction().begin();
		if (isRecipeAlreadySaved(recipe)) {
			System.out.println("Rezept gibt es schon");
		} else {
			entityManager.persist(recipe);
		}
		User user = entityManager.find(User.class, userID);
		user.addRecipe(recipe);
		entityManager.merge(user);
		entityManager.getTransaction().commit();
	}

	public long getRecipeID(String rezeptName) {
		long id = 0;
		@SuppressWarnings("unchecked")
		List<Recipe> recipetemp = entityManager.createQuery(
				"from Recipe s where s.name='" + rezeptName + "'")
				.getResultList();
		if (recipetemp.size() == 1) {
			id = recipetemp.get(0).getId();
		}

		return id;
	}

}
