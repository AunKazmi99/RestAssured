package dynamicJson;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJsonTest {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI= "http://216.10.245.166";
        String response = given().header("Content-Type","application/json")
                .body(Payload.addBook(isbn, aisle))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().body().asString();
        System.out.println(response);

        JsonPath jsonPath = ReUsableMethods.rawToJson(response);
        String id = jsonPath.getString("ID");
        System.out.println(id);
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        return new Object[][] {{"asasc", "134124"}, {"adsagfasd", "1241561"}, {"asdagqwr", "1241"}};
    }
}
