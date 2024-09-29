package basics;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.AddPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .extract().response().asString();
        System.out.println(response);

        JsonPath jsonPath = ReUsableMethods.rawToJson(response);
        String placeId = jsonPath.getString("place_id");
        System.out.println(placeId);

        given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"" + placeId + "\":\"4cd322a1c5c532519f9536a7336cca3d\",\n" +
                        "\"address\":\"70 Summer walk, USA\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200);

        String address = "29, side layout, cohen 09";

        String putResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("address", equalTo(address))
                .extract().body().asString();

        JsonPath jsonPath2 = ReUsableMethods.rawToJson(putResponse);
        String responseAddress = jsonPath2.getString("address");
        System.out.println(responseAddress);

        Assert.assertEquals(responseAddress, address);
    }
}
