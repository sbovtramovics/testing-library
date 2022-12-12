package restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class Test01_Library {

    public static String id;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:4567";
    }

    @Test
    public void postRequest() {
        Response response = given()
                .when()
                .post("/books?name=Hamlet&author=Shakespeare&year=1911&available=33")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotEquals(null, response.jsonPath().getString("id"));
        Assertions.assertEquals("Hamlet", response.jsonPath().getString("name"));
        Assertions.assertEquals("Shakespeare", response.jsonPath().getString("author"));
        Assertions.assertEquals("1911", response.jsonPath().getString("year"));
        Assertions.assertEquals("33", response.jsonPath().getString("available"));
    }

    @BeforeEach
    public void makeSureEverythingIsReady(){
        id = post("/books?name=KinLeaR&author=Shakespeare&year=1965&available=3").getBody().jsonPath().getString("id");
    }

    @Test
    public void getRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/books/" + id)
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(id, response.jsonPath().getString("id"));
        Assertions.assertEquals("KinLeaR", response.jsonPath().getString("name"));
        Assertions.assertEquals("Shakespeare", response.jsonPath().getString("author"));
        Assertions.assertEquals("1965", response.jsonPath().getString("year"));
        Assertions.assertEquals("3", response.jsonPath().getString("available"));
    }

    @Test
    public void getAllRequest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/books")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());

    }

    @Test
    public void putRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("/books/" + id + "?name=KinnnLeaR&author=Shakespeare&year=1958&available=18")
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("Book is updated", response.getBody().asString());
    }

    @Test
    public void deleteRequest() {
        Response response = given()
                .when()
                .delete("/books/" + id)
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("Book is removed", response.getBody().asString());
    }
}