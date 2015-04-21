package hibernate_util;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CookieApp_Main {
	private EntityManager entityManager = EntityManagerUtil.getEntityManager();
	
	public static void main(String[] args) {
		
		CookieApp_Main cookie = new CookieApp_Main();
		
		
		cookie.saveUser();
		
	}
	
	
	
	public String saveUser(){
		User moritz = new User();
		moritz.setName("Moritz");
		moritz.setCreated(Calendar.getInstance().getTime());
		moritz.seteMail("moritz.gabriel@gmx.de");
		moritz.setPassword("test");
		
		entityManager.getTransaction().begin();
		moritz = entityManager.merge(moritz);
		
		entityManager.getTransaction().commit();
		return "test";
	}

}
