package logintest;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


public class CookieAppSeleniumTest {

	private WebDriver driver;
	private String baseUrl, browserName, browserVersion;

	public void setUp() throws Exception {

		driver = new FirefoxDriver();
		baseUrl = "localhost:8080/cookieapp";
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		browserName = caps.getBrowserName();
		browserVersion = caps.getVersion();
		System.out.println("Running on " + browserName + " on version " + browserVersion);

	}

	public void tearDown() {
		driver.quit();
	}

	public void goToHomePage() {
		driver.get(baseUrl);
	}

	public void checkPageLogin() {
		driver.findElement(By.cssSelector("input[type=\"text\"]")).isDisplayed();
		driver.findElement(By.cssSelector("input[type=\"password\"]")).isDisplayed();
	}

	/**
	 * Enters the given String into the Textfield with given name
	 * @param stringToEnter the String, that should be entered
	 * @param textfieldName the Name of the Field, that should be filled
	 */
	public void enter(String stringToEnter, String textfieldName) {
		if (textfieldName != null && textfieldName.equals("Benutzername")) {   
			driver.findElement(By.cssSelector("input[type=\"text\"]")).clear();
			driver.findElement(By.cssSelector("input[type=\"text\"]")).sendKeys(stringToEnter);
		} else if (textfieldName != null && textfieldName.equals("Kennwort")){
			driver.findElement(By.cssSelector("input[type=\"password\"]")).clear();
			driver.findElement(By.cssSelector("input[type=\"password\"]")).sendKeys(stringToEnter);
		}
	}

	/**
	 * Press the Button with the given text
	 * @param buttonName
	 */
	public void press(String buttonName) {
	    driver.findElement(By.xpath("//div[2]/div[2]/div[2]/div/div")).click();		
	}

	/**
	 * Checks, if User is logged in as user with the given name
	 * @param username
	 */
	public void checkIfLoggedInAs(String username) {
		// TODO Auto-generated method stub
		
	}



}
