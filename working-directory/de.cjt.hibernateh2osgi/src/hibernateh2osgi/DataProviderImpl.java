package hibernateh2osgi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class DataProviderImpl {

	private EntityManager entityManager;

	public void main() {
		this.entityManager = EntityManagerUtil.getEntityManager();

		String mailadress = createDummyUser();

		createDummyRecipe(mailadress);

		User user;
		user = getUser(getUserID(mailadress));
		user.debugDump();

		Set<Recipe> recipes = user.getRecipes();
		Iterator<Recipe> recipeIterator = recipes.iterator();
		System.out.println(recipeIterator.hasNext());
		while (recipeIterator.hasNext()) {
			recipeIterator.next().debugDump();
		}

		Recipe temp = getRecipe(getRecipeID("Spaghetti"));
		System.out.println(temp.getCreator().getName());

		user = user.createUser("Maritz", "test123", mailadress, new Date(), new HashSet<Recipe>() /*, new HashSet<Recipe>()*/);

		// deleteUser(getUserID(mailadress));
		
		changePassword(getUserID(mailadress), "test123", "test1234");

		System.out.println(getUserID(mailadress));

		List<User> users = getUsers();
		for (int i = 0; i < users.size(); i++) {
			users.get(i).debugDump();
			System.out.println(users.get(i).getPassword());
		}
	}

	private void createDummyRecipe(String mailadress) {
		Recipe re = new Recipe();
		re = re.createRecipe("Lasagne", "blablabla", getUser(getUserID(mailadress)));
		Recipe ra = new Recipe();
		ra = ra.createRecipe("Spaghetti", "blobloblo", getUser(getUserID(mailadress)));
		Recipe ru = new Recipe();
		ru = ru.createRecipe("Frikadellen", "bliblibli", getUser(getUserID(mailadress)));

		saveRecipe(re);
		saveRecipe(ra);
		saveRecipe(ru);
	}

	private String createDummyUser() {
		User user = new User();
		String mailadress = "Moritz.gabriel@gmx.de";
		user = user.createUser("Moritz", "test1", mailadress, new Date() , new HashSet<Recipe>()/*, new HashSet<Recipe>()*/);
		saveUser(user);
		return mailadress;
	}

	public List<User> getUsers() {
		entityManager.getTransaction().begin();
		List<?> obj = entityManager.createQuery("from User").getResultList();
		List<User> users = new ArrayList<User>();
		for (Object object : obj) {
			if (object instanceof User) {
				users.add((User) object);
			}
		}
		entityManager.getTransaction().commit();
		return users;
	}

	public void saveUser(User user) {
		entityManager.getTransaction().begin();
		if (contains(user)) {
			System.err.println("User gibt es schon");
		} else {
			entityManager.persist(user);
		}
		entityManager.getTransaction().commit();
	}

	public void deleteUser(Long userID) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		if (user != null) {
			entityManager.remove(user);
		} 
		entityManager.getTransaction().commit();
	}

	public long getUserID(String eMail) {
		long id = 0;
		Query query = this.entityManager.createQuery("from User s where s.eMail='" + eMail + "'");
		List<?> usersFromQuery = query.getResultList();
		if (usersFromQuery.size() == 1 && usersFromQuery.get(0) instanceof User) {
			id = ((User) usersFromQuery.get(0)).getId();
		}
		return id;
	}

	public User getUser(long userID) {
		User user = entityManager.find(User.class, userID);
		return user;
	}

	public void changePassword(long userID, String oldPassword, String newPassword) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, userID);
		if (user.getPassword().equals(oldPassword) && newPassword != null && newPassword.length() > 0) {
			user.setPassword(newPassword);
			entityManager.merge(user);
			System.out.println("Password changed successfully");
		} else {
			System.err.println("Failed changing password");
		}
		entityManager.getTransaction().commit();
		// TODO add return value, Maybe just Boolean?
	}

	public List<Recipe> getRecipes() {
		entityManager.getTransaction().begin();
		List<?> obj = entityManager.createQuery("from Recipe").getResultList();
		List<Recipe> recipes = new ArrayList<Recipe>();
		for (Object object : obj) {
			if (object instanceof Recipe) {
				recipes.add((Recipe) object);
			}
		}
		entityManager.getTransaction().commit();
		return recipes;
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
		List<?> recipes = entityManager.createQuery("from Recipe s where s.name='" + rezeptName + "'").getResultList();
		if (recipes.size() == 1 && recipes.get(0) instanceof Recipe) {
			id = ((Recipe)recipes.get(0)).getId();
		}
		return id;
	}

	public Recipe getRecipe(long recipeID) {
		Recipe temp = new Recipe();
		temp = entityManager.find(Recipe.class, recipeID);
		return temp;

	}

	/*
	public void addRecipeToFavorites(Recipe recipe, User user){
		entityManager.getTransaction().begin();
		Recipe recipeTemp = entityManager.find(Recipe.class, recipe);
		User userTemp = entityManager.find(User.class, user);

		entityManager.getTransaction().commit();
	}
	*/

	public boolean login(String eMail, String password) {
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, getUserID(eMail));
		entityManager.getTransaction().commit();
		return user.getPassword().equals(password);
	}

}
