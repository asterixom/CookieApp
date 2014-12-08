package logintest;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefs {
	
	private CookieAppSeleniumTest testScript;

	
	public void setupSeleniumTestParameters() throws Exception {
		testScript = new CookieAppSeleniumTest();
		testScript.setUp();
		testScript.goToHomePage();
	}

	public void tidyUp() {
		if (testScript != null) {
		testScript.tearDown();
		} else {
			System.err.println("testScript was null");
		}
	}	
	@Given("^I am on the CookieApp Homepage$")
	public void i_am_on_the_CookieApp_Homepage() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		setupSeleniumTestParameters();
	}

	@Given("^I am not logged in$")
	public void i_am_not_logged_in() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		testScript.checkPageLogin();
	}

	@When("^I enter \"(.*?)\" in the field \"(.*?)\"$")
	public void i_enter_in_the_field(String arg1, String arg2) throws Throwable {
	    testScript.enter(arg1, arg2);
	}

	@When("^I press the \"(.*?)\" button$")
	public void i_press_the_button(String arg1) throws Throwable {
		testScript.press(arg1);
	}

	@Then("^The Page will say Eingeloggt als \"(.*?)\"$")
	public void the_Page_will_say(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		testScript.checkIfLoggedInAs(arg1);
	}	
	
	@Then("^I am not logged in after$")
	public void i_am_not_logged_in_after() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		testScript.checkPageLogin();
	}

	@After
	public void tearDown() throws Exception {
		if (testScript != null) {
		testScript.tearDown();
		} else {
			System.err.println("testScript was null");
		}
	}
}
