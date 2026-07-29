package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/TC03_VerifyDescendingSort.feature",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/TC03.html"}
)
public class TC03_CucumberRunner extends AbstractTestNGCucumberTests {

}
