package recipesearchtest;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;



@RunWith(Cucumber.class)

@CucumberOptions(
		features={"src/"},
				format={"pretty"})
public class CukeRunner {

}
