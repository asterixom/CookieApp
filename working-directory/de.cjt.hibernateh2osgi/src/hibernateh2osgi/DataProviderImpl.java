package hibernateh2osgi;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

//EclipseLink JPA With H2 Example

public class DataProviderImpl {

	private EntityManager entityManager;

	public void main() {
		this.entityManager = EntityManagerUtil.getEntityManager();

		User mo = new User();
		mo = mo.createUser("Moritz", "test", "Moritz.gabriel@gmx.de", new Date() , new HashSet<Recipe>()/*, new HashSet<Recipe>()*/);
		saveUser(mo);
		/*
		Recipe re = new Recipe();
		re = re.createRecipe("Lasagne", "blablabla",
				cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de")));
		Recipe ra = new Recipe();
		ra = ra.createRecipe("Spaghetti", "blablabla",
				cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de")));
		Recipe ru = new Recipe();
		ru = ru.createRecipe("Frikadellen", "blablabla",
				cookie.getUser(cookie.getUserID("Moritz.gabriel@gmx.de")));
		

		cookie.saveRecipe(re);
		cookie.saveRecipe(ra);
		cookie.saveRecipe(ru);
*/
		 User temp = getUser(getUserID("Moritz.gabriel@gmx.de"));
		 temp.debugDump();
		 /*
		 Set<Recipe> recipes = temp.getRecipes();
		 Iterator<Recipe> iter = recipes.iterator();
		 while (iter.hasNext()) {
		 System.out.println(iter.next().getName());
		 }
*/
//		Recipe temp = cookie.getRecipe(cookie.getRecipeID("Spaghetti"));
//		System.out.println(temp.getCreator().getName());

		// User ma = new User(); ma = ma.createUser("Maritz", "test123",
		// "maritz.gabriel@gmx.de", new Date(), new HashSet<Recipe>(), new
		// HashSet<Recipe>());

		// cookie.deleteUser(cookie.getUserID("Moritz.gabriel@gmx.de"));
		// cookie.aenderePasswort(cookie.getUserID("Moritz.gabriel@gmx.de"),
		// "test1234");
		// "booya");

		// System.out.println(cookie.getUserID("Moritz.gabriel@gmx.de"));

//		 List<User> users = cookie.listAllUsers();
//		 for (int i = 0; i < users.size(); i++) {
//		 System.out.println(users.get(i).getPassword());
//		 }
	}

	public List<User> listAllUsers() {
		entityManager.getTransaction().begin();

		List<User> usertemp = entityManager.createQuery("from User").getResultList();

		entityManager.getTransaction().commit();

		return usertemp;
	}

	public boolean isUserAlreadySaved(User user) {
		@SuppressWarnings("unchecked")
		List<User> usertemp = entityManager.createQuery("from User s where s.eMail='" + user.geteMail() + "'").getResultList();
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
		Query tempQuery = this.entityManager.createQuery("from User s where s.eMail='" + eMail + "'");
		List<User> usertemp = tempQuery.getResultList();
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
		List<Recipe> recipetemp = entityManager.createQuery("from Recipe").getResultList();
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

	public void saveRecipe(Recipe recipe) {
		entityManager.getTransaction().begin();
		if (isRecipeAlreadySaved(recipe)) {
			System.out.println("Rezept gibt es schon");
		} else {
			entityManager.persist(recipe);
		}
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

	public Recipe getRecipe(long recipeID) {
		Recipe temp = new Recipe();
		temp = entityManager.find(Recipe.class, recipeID);
		return temp;

	}
	
	public void addRecipeToFavorites(Recipe recipe, User user){
		entityManager.getTransaction().begin();
		Recipe recipeTemp = new Recipe();
		User userTemp = new User();
		
		recipeTemp = entityManager.find(Recipe.class, recipe);
		userTemp = entityManager.find(User.class, user);
		
		
	}
	
	public boolean login(String eMail, String password) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, getUserID(eMail));
		entityManager.getTransaction().commit();
		return user.getPassword().equals(password);
	}

}
