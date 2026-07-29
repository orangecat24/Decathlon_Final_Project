package Runners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/TC04_AddToCartAndVerifyOrderTotal.feature",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/TC04.html"}
)
public class TC04_CucumberRunner extends AbstractTestNGCucumberTests {

}
