package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/product_store.feature",



        glue = {"stepdefinitions"},

        plugin = {
                "pretty",
                "html:target/cucumber-reports/reporte.html",
                "json:target/cucumber-reports/reporte.json"
        },
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class CucumberTestRunner {
}
