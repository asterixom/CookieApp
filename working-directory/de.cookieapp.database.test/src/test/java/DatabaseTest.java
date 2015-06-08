package test.java;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cookieapp.data.model.User;
import de.cookieapp.database.impl.DataProviderImpl;
import de.cookieapp.database.impl.UserImpl;

public class DatabaseTest {

	private DataProviderImpl dataProvider;
	private String testUserName = "TestUser";
	private String testUserPassword = "TestPassword";
	private String testUserMail = "test@Mail.de";

	@Before
	public void setUp() throws InstantiationException, IllegalAccessException{
		dataProvider = new DataProviderImpl();
		assertFalse(dataProvider==null);
	}

	@Test
	public void fillDatabaseWithDummyData() {
		System.out.println("Testing 'Fill Database With DummyData'");
		assertFalse(dataProvider == null);
		assertTrue(dataProvider.getUsers().isEmpty());		
		assertTrue(dataProvider.getRecipes().isEmpty());
		dataProvider.createDummyData();
		assertFalse(dataProvider.getUsers().isEmpty());		
		assertFalse(dataProvider.getRecipes().isEmpty());
	}

	@Test
	public void createUser() {
		System.out.println("Testing 'create User'");
		assertFalse(dataProvider==null);
		User user = UserImpl.createUser(testUserName, testUserPassword, testUserMail, null, null, null);
		assertTrue(user != null);
		dataProvider.saveUser(user);
		assertTrue(dataProvider.getUserID(testUserMail) > 0);
		assertTrue(dataProvider.getUserID(testUserName) > 0);
	}
	
	@Test
	public void changePassword() {
		System.out.println("Testing 'change Password'");
		if (dataProvider.getUserID(testUserMail) < 1) {
			User user = UserImpl.createUser(testUserName, testUserPassword, testUserMail, null, null, null);
			dataProvider.saveUser(user);
		}
		User user = dataProvider.getUser(dataProvider.getUserID(testUserMail));
		assertTrue(user != null);
		assertTrue(dataProvider.contains(user));
		String newPassword = "TestPassword2";
		dataProvider.changePassword(user.getId(), user.getPassword(), newPassword);
		assertTrue(dataProvider.login(testUserMail, newPassword));
	}
	
	@Test
	public void deleteUser() {
		System.out.println("Testing 'delete User'");
		if (dataProvider.getUserID(testUserMail) < 1) {
			User user = UserImpl.createUser(testUserName, testUserPassword, testUserMail, null, null, null);
			dataProvider.saveUser(user);
		}
		assertFalse(dataProvider==null);
		User user = dataProvider.getUser(dataProvider.getUserID(testUserMail));
		assertTrue(user != null);
		assertTrue(dataProvider.getUserID(testUserMail) > 0);
		assertTrue(dataProvider.getUserID(testUserName) > 0);
		dataProvider.deleteUser(user.getId());
		assertFalse(dataProvider.contains(user));
	}
}
