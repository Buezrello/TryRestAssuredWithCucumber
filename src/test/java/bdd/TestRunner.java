package bdd;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/features",
        glue = "stepdefinitions",
        plugin = "json:target/jsonReports/cucumber-report.json",
        tags = {"@DeletePlace"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
