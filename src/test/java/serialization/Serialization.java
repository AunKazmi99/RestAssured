package serialization;

import com.beust.ah.A;
import files.Payload;
import io.restassured.RestAssured;
import serialization.pojo.AddPlace;
import serialization.pojo.Locations;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Serialization {
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

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(addPlace)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .extract().response().asString();
        System.out.println(response);
    }
}
