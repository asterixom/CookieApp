package test.java;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.cookieapp.control.ControlService;
import de.cookieapp.control.ControlServiceImpl;
import de.cookieapp.control.exceptions.CookieAppException;
import de.cookieapp.data.model.SecurityClearance;

public class TestControl {

	private ControlService control;
	
	@Before
	public void setUp(){
		control = new ControlServiceImpl();
		assertFalse(control==null);
	}
	
	@Test
	public void sessionAndLoggingTest() {
		assertFalse(control==null);
		assertFalse(control.hasSession(null));
		Long session = control.createSession();
		assertTrue(control.hasSession(session));
		try {
			assertTrue(control.getSecurityClearance(session)==SecurityClearance.GUEST);
			
			//Only works if no such User exists
			control.register(session, "JUnit-User", "password", "jUnit@example.com");
			
			assertTrue(control.login(session, "junit@example.com", "password"));
			assertTrue(control.login(session, "junit-user", "password"));
			
			assertTrue("JUnit-User".equalsIgnoreCase(control.getCurrentUserName(session)));
			assertTrue("junit@example.com".equalsIgnoreCase(control.getCurrentUserMail(session)));
			
			assertTrue(control.getSecurityClearance(session)==SecurityClearance.USER);
			
			control.logout(session);
			
			assertTrue(control.getSecurityClearance(session)==SecurityClearance.GUEST);
		} catch (CookieAppException e) {
			fail(e.toString());
		}
	}

}
