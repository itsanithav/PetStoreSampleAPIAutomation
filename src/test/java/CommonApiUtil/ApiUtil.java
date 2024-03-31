package CommonApiUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ApiUtil {
    private static String jsonAsString;
    private static Response response;

    public static Response getRequestWithSuccessStausCode(RequestSpecification req, String resource) {
        response = given()
                .relaxedHTTPSValidation()
                .spec(req)
                .when()
                .get(resource)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();
        jsonAsString = response.asString();
        return response;
    }

    public static Response getRequestWithoutStausCodeValidation(RequestSpecification req, String resource) {
        response = given()
                .relaxedHTTPSValidation()
                .spec(req)
                .when()
                .get(resource)
                .then()
                .contentType(ContentType.JSON)
                .extract().response();
        jsonAsString = response.asString();
        return response;
    }

    public static Response postRequest(RequestSpecification req, String resource) {
        response = given()
                .relaxedHTTPSValidation()
                .spec(req)
                .when()
                .post(resource)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();
        jsonAsString = response.asString();
        return response;
    }


    public static Response deleteRequest(RequestSpecification req, String resource) {
        response = given()
                .relaxedHTTPSValidation()
                .spec(req)
                .when()
                .delete(resource)
                .then()
                .contentType(ContentType.JSON)
                .extract().response();
        jsonAsString = response.asString();
        return response;
    }

}
