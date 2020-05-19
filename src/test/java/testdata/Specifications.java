package testdata;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Specifications {

    private static RequestSpecification requestSpecification;

    private Specifications() {
    }

    public static RequestSpecification requestSpecification() throws FileNotFoundException {

        if (requestSpecification == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            requestSpecification = new RequestSpecBuilder()
                    .setBaseUri(Helpers.getPropsFromResources("global.properties").getProperty("baseUri"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }

        return requestSpecification;
    }

    public static ResponseSpecification responseSpecification() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
    }
}
