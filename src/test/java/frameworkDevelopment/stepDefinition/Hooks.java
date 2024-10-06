package frameworkDevelopment.stepDefinition;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        StepDefinition stepDefinition = new StepDefinition();

        if (StepDefinition.placeId == null) {
            stepDefinition.add_place_payload("Aun", "Demo Address", "English");
            stepDefinition.user_calls_with_post_http_request("AddPlaceAPI", "POST");
            stepDefinition.verify_placeId_created_maps_to_using("Aun", "GetPlaceAPI");
        }
    }
}
