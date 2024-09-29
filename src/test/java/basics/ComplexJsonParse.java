package basics;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {
        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
        System.out.println("Number of Courses: " + jsonPath.getInt("courses.size()"));
        System.out.println("Purchase Amount: " + jsonPath.getInt("dashboard.purchaseAmount"));
        System.out.println("Title of First Course: " + jsonPath.getString("courses[0].title"));
        for(int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            System.out.println(jsonPath.getString("courses[" + i + "].title"));
            System.out.println(jsonPath.getInt("courses[" + i + "].price"));
        }
        for(int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            if(jsonPath.getString("courses[" + i + "].title").equalsIgnoreCase("Cypress")) {
                System.out.println(jsonPath.getString("courses[" + i + "].title"));
                System.out.println(jsonPath.getInt("courses[" + i + "].copies"));
                break;
            }
        }
    }
}
