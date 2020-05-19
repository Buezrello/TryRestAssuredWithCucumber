package testdata;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Helpers {

    private Helpers() {}


    public static Properties getPropsFromResources(String fileName) {

        ClassLoader loader = Helpers.class.getClassLoader();
        Properties prop = new Properties();
        try (InputStream inputStream = loader.getResourceAsStream(fileName)) {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }

    public static Object getJsonPathValue(Response response, String key) {
        String res = response.asString();
        JsonPath jsonPath = new JsonPath(res);
        return jsonPath.get(key);
    }
}
