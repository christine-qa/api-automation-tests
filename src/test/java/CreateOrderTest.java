import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.Track;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateOrderTest {


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Check order creation with black color of scooter in body params")
    public void createOrderWithBlackColorTest() {
        File json = new File("src/test/resources/createorderWithBlackColors.json");

        Response response = given().header("Content-type", "application-json").and().body(json)
                .when().post("/api/v1/orders");
        response.then().assertThat().statusCode(201);
        int trackNumber = response.then().extract().body().path("track");

        given().header("Content-type", "application-json").and().queryParam("track", trackNumber)
                .when().put("/api/v1/orders/cancel").then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check order creation with grey color of scooter in body params")
    public void createOrderWithGreyColorTest() {
        File json = new File("src/test/resources/createorderWithGreyColors.json");

        Response response = given().header("Content-type", "application-json").and().body(json)
                .when().post("/api/v1/orders");
        response.then().assertThat().statusCode(201);
        int trackNumber = response.then().extract().body().path("track");

        given().header("Content-type", "application-json").and().queryParam("track", trackNumber)
                .when().put("/api/v1/orders/cancel").then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check order creation with no scooter color in body params")
    public void createOrderWithNoColorTest() {
        File json = new File("src/test/resources/createorderWithNoColor.json");

        Response response = given().header("Content-type", "application-json").and().body(json)
                .when().post("/api/v1/orders");
        response.then().assertThat().statusCode(201);
        int trackNumber = response.then().extract().body().path("track");

        given().header("Content-type", "application-json").and().queryParam("track", trackNumber)
                .when().put("/api/v1/orders/cancel").then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check order creation with all possible colors of scooter in body params")
    public void createOrderWithAllColorsTest() {
        File json = new File("src/test/resources/createorderWithAllColors.json");

        Response response = given().header("Content-type", "application-json").and().body(json)
                .when().post("/api/v1/orders");
        response.then().assertThat().statusCode(201);
        int trackNumber = response.then().extract().body().path("track");

        given().header("Content-type", "application-json").and().queryParam("track", trackNumber)
                .when().put("/api/v1/orders/cancel").then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check whether response body has track number")
    public void createOrderResponseShouldHaveTrackTest () {
        File json = new File("src/test/resources/createorderWithAllColors.json");

        Response response = given().header("Content-type", "application-json").and().body(json)
                .when().post("/api/v1/orders");
        response.body().as(Track.class);

        int trackNumber = response.then().extract().body().path("track");

        given().header("Content-type", "application-json").and().queryParam("track", trackNumber)
                .when().put("/api/v1/orders/cancel").then().assertThat().statusCode(200);
    }
}
