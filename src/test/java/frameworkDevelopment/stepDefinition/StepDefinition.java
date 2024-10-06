package frameworkDevelopment.stepDefinition;

import frameworkDevelopment.resources.ApiResources;
import frameworkDevelopment.resources.TestDataBuild;
import frameworkDevelopment.resources.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {

    public RequestSpecification reqSpec;
    public ResponseSpecification responseSpecification;
    public Response response;
    TestDataBuild testDataBuild = new TestDataBuild();
    static String placeId;

    @Given("Add Place Payload with {string}, {string}, and {string}")
    public void add_place_payload(String name, String address, String language) throws IOException {
        reqSpec = given().spec(requestSpecification()).body(testDataBuild.addPlacePayload(name, address, language));
    }

    private JsonPath rawToJson(String response) {
        return new JsonPath(response);
    }

    @When("User calls {string} with {string} HTTP Request")
    public void user_calls_with_post_http_request(String resource, String httpMethod) {
        ApiResources resourceApi = ApiResources.valueOf(resource);
        String resourceApiString = resourceApi.getResource();

        responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();

        if (httpMethod.equalsIgnoreCase("POST"))
            response = reqSpec.when().post(resourceApiString);
        else if (httpMethod.equalsIgnoreCase("GET"))
            response = reqSpec.when().get(resourceApiString);
    }

    @Then("The API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(int statusCode) {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        Assert.assertEquals(value, getJsonPath(response, key));
    }

    @And("Verify placeId created maps to {string} using {string}")
    public void verify_placeId_created_maps_to_using(String name, String resource) {
        placeId = getJsonPath(response, "place_id");
        reqSpec = given().spec(requestSpecification).queryParam("place_id", placeId);
        user_calls_with_post_http_request(resource, "GET");
        Assert.assertEquals(name, getJsonPath(response, "name"));
    }

    @Given("Delete Place payload")
    public void delete_place_payload() throws IOException {
        reqSpec = given().spec(requestSpecification()).body(testDataBuild.deletePlacePayload(placeId));
    }
}
