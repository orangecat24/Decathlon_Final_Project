package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/TC01_SearchAndOpenProduct.feature",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/TC01.html"}
)
public class TC01_CucumberRunner extends AbstractTestNGCucumberTests {
}
