package basics;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidationTest {

    @Test
    public void sumValidationTest() {
        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount = " + purchaseAmount);
        int sumAmount = 0;
        for(int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            System.out.println(jsonPath.getString("courses[" + i + "].title"));
            System.out.println(jsonPath.getInt("courses[" + i + "].price"));
        }
        for(int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            sumAmount += (jsonPath.getInt("courses[" + i + "].price") * jsonPath.getInt("courses[" + i + "].copies"));
        }
        Assert.assertEquals(sumAmount,purchaseAmount);
    }
}