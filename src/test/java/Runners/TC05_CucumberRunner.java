package Runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/TC05_UpdateCartQuantities.feature",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/TC05.html"}
)
    public class TC05_CucumberRunner extends AbstractTestNGCucumberTests {
}

