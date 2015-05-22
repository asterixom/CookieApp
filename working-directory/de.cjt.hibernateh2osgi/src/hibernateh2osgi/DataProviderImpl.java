package hibernateh2osgi;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

//EclipseLink JPA With H2 Example

public class DataProviderImpl {

	private EntityManager entityManager;

	public void main() {
		this.entityManager = EntityManagerUtil.getEntityManager();

		User user = new User();
		String mailadress = "Moritz.gabriel@gmx.de";
		user = user.createUser("Moritz", "test1", mailadress, new Date() , new HashSet<Recipe>()/*, new HashSet<Recipe>()*/);
		saveUser(user);

		Recipe re = new Recipe();
		re = re.createRecipe("Lasagne", "blablabla", getUser(getUserID(mailadress)));
		Recipe ra = new Recipe();
		ra = ra.createRecipe("Spaghetti", "blobloblo", getUser(getUserID(mailadress)));
		Recipe ru = new Recipe();
		ru = ru.createRecipe("Frikadellen", "bliblibli", getUser(getUserID(mailadress)));

		saveRecipe(re);
		saveRecipe(ra);
		saveRecipe(ru);

		user = getUser(getUserID(mailadress));
		user.debugDump();

		Set<Recipe> recipes = user.getRecipes();
		Iterator<Recipe> recipeIterator = recipes.iterator();
		System.out.println(recipeIterator.hasNext());
		while (recipeIterator.hasNext()) {
			recipeIterator.next().debugDump();
		}
		
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

	public void saveUser(User user) {
		entityManager.getTransaction().begin();

		if (contains(user)) {
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

	public void changePassword(long userID, String password) {
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

	/**
	 * Returns true, if the Database contains the Recipe
	 * @param recipe the recipe to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(Recipe recipe) {
		List<?> recipeList = entityManager.createQuery("from Recipe s where s.name='" + recipe.getName() + "'").getResultList();
		return !recipeList.isEmpty();
	}

	/**
	 * Returns true, if the Database contains the User
	 * @param user the user to look for
	 * @return true, if it is already in the Database, otherwise false
	 */
	public boolean contains(User user) {
		List<?> userList = entityManager.createQuery("from User s where s.eMail='" + user.geteMail() + "'").getResultList();
		return !userList.isEmpty();
	}

	public void saveRecipe(Recipe recipe) {
		entityManager.getTransaction().begin();
		if (contains(recipe)) {
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
