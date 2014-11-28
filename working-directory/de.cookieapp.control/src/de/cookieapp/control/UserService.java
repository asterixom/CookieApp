package de.cookieapp.control;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import de.cookieapp.data.model.SecurityClearance;
import de.cookieapp.data.model.User;
import de.cookieapp.data.service.DataService;

public class UserService {
	
	private MessageDigest md = null;
	private DataService data = null;

	public UserService(DataService dataservice) {
		data = dataservice;
		try {
			md = MessageDigest.getInstance("SHA512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public User login(String userORmail, String password) {
		User user;
		if (userORmail.contains("@")) {
			user = data.getUserByMail(userORmail);
		} else {
			user = data.getUser(userORmail);
		}
		if (user == null) {
			return null;
		}
		if (user.checkPassword(password)) {
			return user;
		}
		return null;
	}

	public User register(String username, String password, String mail) {
		User user = data.createUser(username);
		user.setPassword(password);
		user.setMail(mail);
		user.setSecurityClearance(SecurityClearance.USER);
		return null;
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
}
