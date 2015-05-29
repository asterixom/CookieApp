package de.cookieapp.control;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import de.cookieapp.data.model.Recipe;
import de.cookieapp.data.model.User;
import de.cookieapp.data.service.DataProvider;
import de.cookieapp.database.impl.UserImpl;

public class DataService {

	private MessageDigest md = null;
	private DataProvider dataProvider;

	public DataService(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
		/*
		try {
			md = MessageDigest.getInstance("SHA512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void setRepository(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public User login(String userOrMail, String password) {
		User user = null;
		/*
		 * Could be implemented differently, if different in Repository
		 */
		if (dataProvider.login(userOrMail, password)) {
			user = dataProvider.getUser(dataProvider.getUserID(userOrMail));
		}
		return user;
	}

	public User register(String username, String password, String mail) {
		User user = new UserImpl();
		if (dataProvider != null) {
			user = user.createUser(username, password, mail, null, null, null);
			dataProvider.saveUser(user);
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
		//TODO implement this
		return null;
	}
	
	public ArrayList<Recipe> getRecipesWithName(String name) {
		//TODO implement this
		return null;
	}
}
