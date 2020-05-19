package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import testdata.APIResources;
import testdata.Helpers;
import testdata.Specifications;
import testdata.TestDataBuild;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class StepsDefinition {

    RequestSpecification res;
    Response response;
    static String placeId;

    @Given("Add Place Payload with {string} {string} {string}")
    public void addPlacePayloadWith(String arg0, String arg1, String arg2) throws FileNotFoundException {

        res = given()
                .spec(Specifications.requestSpecification())
                .body(TestDataBuild.addPlacePayload(arg0, arg1, arg2));
    }

    @Given("Delete Place Payload")
    public void deletePlacePayload() throws FileNotFoundException {
        res = given()
                .spec(Specifications.requestSpecification())
                .body(TestDataBuild.deletePlacePayload(placeId));
    }

    @When("user calls {string} with {string} http request")
    public void userCallsWithHttpRequest(String api, String request) {

        String resource = APIResources.valueOf(api).getResource();
        request = request.toLowerCase();

        switch (request) {
            case "post":
                response = res
                        .when()
                        .post(resource)
                        .then()
                        .spec(Specifications.responseSpecification())
                        .extract().response();
                break;
            case "get":
                response = res
                        .when()
                        .get(resource)
                        .then()
                        .spec(Specifications.responseSpecification())
                        .extract().response();
                break;
            case "delete":
                response = res
                        .when()
                        .delete(resource)
                        .then()
                        .spec(Specifications.responseSpecification())
                        .extract().response();
                break;
            default:
                throw new IllegalArgumentException(String.format("Incorrect request '%s'", request));
        }


    }

    @Then("the API call is success with status code {int}")
    public void theAPICallIsSuccessWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode);
    }

    @And("{string} in response body is {string}")
    public void inResponseBodyIs(String param, String value) {
        String expectedValue = (String) Helpers.getJsonPathValue(response, param);
        Assert.assertTrue(expectedValue.equalsIgnoreCase(value));
    }

    @And("verify place_Id created maps to {string} using {string}")
    public void verifyPlace_IdCreatedMapsToUsing(String expectedValue, String api) throws FileNotFoundException {
        placeId = (String) Helpers.getJsonPathValue(response, "place_id");

        res = given()
                .spec(Specifications.requestSpecification()).queryParam("place_id", placeId);

        userCallsWithHttpRequest(api, "GET");
        String actualValue = (String) Helpers.getJsonPathValue(response, "name");

        Assert.assertEquals(actualValue, expectedValue);
    }

}
