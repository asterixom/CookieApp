package de.cookieapp.control;

import java.util.HashMap;
import java.util.Random;

import de.cookieapp.control.exceptions.*;
import de.cookieapp.data.model.*;
import de.cookieapp.data.service.*;

public class ControlServiceImpl implements ControlService {
	
	private DataService dataService = null;
	private UserService userService = null;
	private Random random = null;
	private HashMap<Long,User> sessionMap = null;
	
	public ControlServiceImpl(){
		random = new Random();
		dataService = new DataServiceImpl();
		userService = new UserService(dataService);
		sessionMap = new HashMap<Long,User>();
	}

	@Override
	public Long createSession() throws CookieAppException {
		Long session;
		do{
		session = random.nextLong();
		}while(!sessionMap.containsKey(session));
		sessionMap.put(session, null);
		return session;
	}

	@Override
	public boolean createSession(Long sessionId) throws CookieAppException {
		if(sessionMap.containsKey(sessionId)){
			return false;
		}
		sessionMap.put(sessionId, null);
		return true;
	}
	
	@Override
	public boolean hasSession(Long sessionId) throws CookieAppException {
		return sessionMap.containsKey(sessionId);
	}
	
	@Override
	public boolean login(Long sessionId, String userORmail, String password)
			throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = userService.login(userORmail, password);
		if(user == null){
			return false;
		}else{
			sessionMap.put(sessionId, user);
			return true;
		}
	}

	@Override
	public void logout(Long sessionId) throws CookieAppException {
		sessionMap.put(sessionId, null);
	}

	@Override
	public boolean register(Long sessionId, String username, String password,
			String eMail) throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = userService.register(username, password, eMail);
		if(user == null){
			return false;
		}
		sessionMap.put(sessionId, user);
		return true;
	}

	@Override
	public SecurityClearance getSecurityClearance(Long sessionId)
			throws CookieAppException {
		if(!sessionMap.containsKey(sessionId)){
			throw new NoSessionException();
		}
		User user = sessionMap.get(sessionId);
		if(user == null){
			return SecurityClearance.GUEST;
		}
		return user.getSecurityClearance();
	}
	
}
