package de.cookieapp.control;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.SecurityClearance;
import de.cookieapp.data.model.User;
import de.cookieapp.repository.Repository;

public class DataService {

	private MessageDigest md = null;
	private Repository repository;

	public DataService() {
		/*
		try {
			md = MessageDigest.getInstance("SHA512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		*/
	}

	public User login(String userORmail, String password) {
		User user = null;
		/*
		 * Could be implemented differently, if different in Repository
		 */
		if (userORmail.contains("@")) {
			user = repository.getUser(userORmail, null);
		} 
		if (user == null) {
			user = repository.getUser(null, userORmail);
		}
		if (user != null && user.checkPassword(password)) {
			return user;
		} else {
			return null;
		}
	}

	public User register(String username, String password, String mail) {
		User user = null;
		if (repository != null) {
			user = repository.addUser(username, mail, password);
			if (user != null) {
				user.setSecurityClearance(SecurityClearance.USER);
			}
		}
		return user;
	}

	public String makeHash(String text) {
		byte[] hash;
		try {
			hash = md.digest(text.getBytes("UTF-16"));
			return DatatypeConverter.printHexBinary(hash);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Recipe getRecipe(Long recipeId) {
		return repository.getRecipe(recipeId);
	}

	public void setRepository(Repository repository) {
		if (repository != null) {
			this.repository = repository;
		}
	}

	public void unsetRepsoitory(Repository repository) {
		if (repository != null && this.repository.equals(repository)) {
			this.repository = null;
		}
	}
}
