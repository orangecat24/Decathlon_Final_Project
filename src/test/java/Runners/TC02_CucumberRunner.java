package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/TC02_ProductCategoryFilters.feature",
        glue = {"stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/TC02.html"}
)
public class TC02_CucumberRunner extends AbstractTestNGCucumberTests {
}
