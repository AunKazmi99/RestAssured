package excelDriven;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class JsonToHashMapTest {

    @Test
    public void jsonToHashMapTest() throws IOException {
        ExcelDriven excelDriven = new ExcelDriven();
        ArrayList<String> data =  excelDriven.getData("Sheet1", "RestAddBook");
        HashMap<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", data.get(1));
        jsonAsMap.put("isbn", data.get(2));
        jsonAsMap.put("aisle", data.get(3));
        jsonAsMap.put("author", data.get(4));
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(jsonAsMap)
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(response);

        JsonPath jsonPath = ReUsableMethods.rawToJson(response);
        String id = jsonPath.getString("ID");
        System.out.println(id);
    }
}
