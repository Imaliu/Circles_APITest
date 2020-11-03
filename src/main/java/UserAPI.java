import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserAPI {

    Response response;

    String baseURI="https://jsonplaceholder.typicode.com/users/";

    public Response getalbums(int userid){

        response=
                given()
                        .header("Content-type", "application/json; charset=UTF-8")
                        .when()
                        .get(baseURI+userid+"/albums")
                        .then()
                        .extract().response();

        return response;
    }
}
