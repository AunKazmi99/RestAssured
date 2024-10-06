package jiraBug;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;

public class BugTest {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://aunkazmi.atlassian.net";

        String createResponse = given().log().all().header("Accept", "*/*").header("Content-Type", "application/json")
                .header("Authorization", "Basic YXVua2F6bWk5OTY3NzJAZ21haWwuY29tOkFUQVRUM3hGZkdGMDhmTU9Ze" +
                        "kxnWHgwb2ZSRmZLRVhBTzNPQWtMVEFkVnRSajJtRXhFbzNldjVRemd4VWZYejlrWHp1a1VldkR3YjBGY0NWdDly" +
                        "dzd0X25mcDBTRUtKRnRtT0NnbzdURERJQlU1ekhnVUlPWEI2SEh3ODJqbTVMRENLX0h0THlVU0FsWFJTbHVWeUd" +
                        "KX2hFNERCRG1FTXRPT01BWjYzaVM3NFY2Z0JTT2hZb0ljaz1BOTE1RjdFNw==")
                .body(Payload.createBug("The second bug", "Description for the second bug"))
                .when().post("rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath jsonPath = ReUsableMethods.rawToJson(createResponse);
        String id = jsonPath.getString("id");

        String attachmentResponse = given().log().all().header("Accept", "*/*")
                .header("Authorization", "Basic YXVua2F6bWk5OTY3NzJAZ21haWwuY29tOkFUQVRUM3hGZkdGMDhmTU9Ze" +
                        "kxnWHgwb2ZSRmZLRVhBTzNPQWtMVEFkVnRSajJtRXhFbzNldjVRemd4VWZYejlrWHp1a1VldkR3YjBGY0NWdDly" +
                        "dzd0X25mcDBTRUtKRnRtT0NnbzdURERJQlU1ekhnVUlPWEI2SEh3ODJqbTVMRENLX0h0THlVU0FsWFJTbHVWeUd" +
                        "KX2hFNERCRG1FTXRPT01BWjYzaVM3NFY2Z0JTT2hZb0ljaz1BOTE1RjdFNw==")
                .header("X-Atlassian-Token", "no-check")
                .multiPart("file", new File("C:\\Users\\AunAbbas\\Downloads\\Cypress Certificate.jpg"))
                .pathParam("key", id)
                .when().post("rest/api/3/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200).extract().body().asString();
    }
}
