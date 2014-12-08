package recipesearchtest;

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

	public void gotToRecipeSearchTab() {
	    driver.findElement(By.xpath("//div[3]/div/div[3]/div")).click();
	}

	public void pressSearchButton() {
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
	}

	public void searchForRecipe(String arg1) {
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(arg1);
	}



}
