package petStore.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        features = "src/test/resources/features/",
        plugin = {"json:target/jsonReports/TEST-REPORT.json", "html:target/cucumber-reports.html"},
        glue = "petStore/steps",
        tags = "@inventory"
)

public class LocalTestRunner {
}
