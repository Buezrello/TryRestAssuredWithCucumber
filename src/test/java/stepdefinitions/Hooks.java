package stepdefinitions;

import io.cucumber.java.Before;

import java.io.FileNotFoundException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws FileNotFoundException {

        if (StepsDefinition.placeId==null) {
            StepsDefinition stepsDefinition = new StepsDefinition();
            stepsDefinition.addPlacePayloadWith("any name", "any language", "any place");
            stepsDefinition.userCallsWithHttpRequest("addPlaceAPI", "POST");
            stepsDefinition.verifyPlace_IdCreatedMapsToUsing("any name", "getPlaceAPI");
        }

    }
}
