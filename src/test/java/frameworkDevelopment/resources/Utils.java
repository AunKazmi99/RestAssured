package frameworkDevelopment.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification requestSpecification;

    public RequestSpecification requestSpecification() throws IOException {

        if (requestSpecification == null) {
            PrintStream logs = new PrintStream(new FileOutputStream("logs.txt"));

            requestSpecification = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURI"))
                    .addQueryParam("key", "qaclick123")
                    .setContentType(ContentType.JSON)
                    .addFilter(RequestLoggingFilter.logRequestTo(logs))
                    .addFilter(ResponseLoggingFilter.logResponseTo(logs))
                    .build();

            return requestSpecification;
        }
        return requestSpecification;
    }

    public String getGlobalValue(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\AunAbbas\\IdeaProjects" +
                "\\RestAssuredAutomation\\src\\test\\java\\frameworkDevelopment\\resources\\config.properties");
        properties.load(fileInputStream);
        return (String) properties.get(key);
    }

    public String getJsonPath(Response response, String key) {
        String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        return jsonPath.get(key).toString();
    }
}
