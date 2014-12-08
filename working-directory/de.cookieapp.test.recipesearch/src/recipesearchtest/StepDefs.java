package recipesearchtest;

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

	@Given("^I am on the RecipeSearch Tab$")
	public void i_am_on_the_RecipeSearch_Tab() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		testScript.gotToRecipeSearchTab();
	}

	@When("^I enter \"(.*?)\" in the searchfield$")
	public void i_enter_in_the_searchfield(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		testScript.searchForRecipe(arg1);
	}

	@When("^I press the Suchen button$")
	public void i_press_the_Suchen_button() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		testScript.pressSearchButton();
	}

	@Then("^The Page will show \"(.*?)\" as Result$")
	public void the_Page_will_show_as_Result(String arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		//TODO implement this
	}

	@Then("^The Page will show \"(.*?)\" and \"(.*?)\" as Results$")
	public void the_Page_will_show_and_as_Results(String arg1, String arg2) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		//TODO implement this
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
