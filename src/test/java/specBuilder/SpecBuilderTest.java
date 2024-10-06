package specBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialization.pojo.AddPlace;
import serialization.pojo.Locations;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SpecBuilderTest {
    public static void main(String[] args) {
        AddPlace addPlace = new AddPlace();
        Locations locations = new Locations();
        locations.setLat(-38.383494);
        locations.setLng(33.427362);
        addPlace.setAccuracy(50);
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setLanguage("French-IN");
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setWebsite("http://google.com");
        addPlace.setTypes(List.of(new String[]{"shoe park", "shop"}));
        addPlace.setLocation(locations);

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
        RequestSpecification reqSpec = given().spec(requestSpecification).body(addPlace);

        String res = reqSpec.when().post("maps/api/place/add/json")
                .then().spec(responseSpecification).body("scope", equalTo("APP"))
                .extract().response().asString();
        System.out.println(res);
    }
}

