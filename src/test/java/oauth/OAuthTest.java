package oauth;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.Courses;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {

    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String[] webAutomationCourseTitle = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        String tokenResponse = given()
                .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("oauthapi/oauth2/resourceOwner/token")
                .then().assertThat().statusCode(200).extract().response().asString();

        JsonPath jsonPath = ReUsableMethods.rawToJson(tokenResponse);
        String accessToken = jsonPath.getString("access_token");

        String getCourseDetails = given().queryParam("access_token", accessToken)
                .when().get("oauthapi/getCourseDetails")
                .then().statusCode(401).extract().body().asString();

        System.out.println(getCourseDetails);

        GetCourse getCourseDetailsObject = given().queryParam("access_token", accessToken)
                .when().get("oauthapi/getCourseDetails")
                .then().statusCode(401).extract().body().as(GetCourse.class);
        System.out.println(getCourseDetailsObject.getLinkedIn());
        System.out.println(getCourseDetailsObject.getExpertise());
        System.out.println(getCourseDetailsObject.getInstructor());
        System.out.println(getCourseDetailsObject.getServices());
        System.out.println(getCourseDetailsObject.getUrl());
        System.out.println(getCourseDetailsObject.getCourses().getApi().get(1).getCourseTitle());

        List<Api> courses = getCourseDetailsObject.getCourses().getApi();
        for (Api course : courses) {
            if (course.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(course.getPrice());
            }
        }

        List<WebAutomation> webAutomationCourses = getCourseDetailsObject.getCourses().getWebAutomation();
        ArrayList<String> actualWebAutomationCoursesTitle = new ArrayList<String>();
        for (WebAutomation course : webAutomationCourses) {
            actualWebAutomationCoursesTitle.add(course.getCourseTitle());
        }
        List<String> webAutomationCourseTitleList = Arrays.asList(webAutomationCourseTitle);
        Assert.assertTrue(actualWebAutomationCoursesTitle.equals(webAutomationCourseTitleList));
        Assert.assertEquals(actualWebAutomationCoursesTitle, webAutomationCourseTitleList);
    }
}
