package frameworkDevelopment.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/frameworkDevelopment/features",
        glue = {"frameworkDevelopment.stepDefinition"},
        tags = "@AddPlace or @DeletePlace",
        plugin = "json:target/jsonReports/cucumber-report.json"
)
public class TestRunner {
}
