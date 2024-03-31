package petStore.steps;

import dataProvider.ConfigFileReader;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks  {
    public static Scenario Scenario;

    @Before
    public void setUp(Scenario scenario) {
        Scenario = scenario;
        ConfigFileReader.ReadTestConfigurationProperties();
    }

}
