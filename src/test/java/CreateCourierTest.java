import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.Courier;
import ru.yandex.scooter.SuccessCourierCreationResp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {

    Courier courierDataForCreate = new Courier("driver7", "12345", "Anna");
    Courier courierDataForBadCreate = new Courier("driver7");
    Courier courierDataForId = new Courier("driver7", "12345");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @Test
    @DisplayName("Check status code when sending data with all required fields")
    public void shouldCreateCourierAndReturn201Test() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(201);


        int courierId = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login").then().extract().body().path("id");

        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check response body when sending data with all required fields")
    public void successCreationShouldReturnOkTest() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .body().as(SuccessCourierCreationResp.class);


        int courierId = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login").then().extract().body().path("id");


        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check error and body message when sending data without login")
    public void requestWithoutRequiredFieldShouldReturnError() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForBadCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(400)
                .and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check error and body message when sending data with already existed login")
    public void shouldNotLetCreateTwoCouriersWithTheSameLoginTest() {

        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(201);

        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(409)
                .and().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        int courierId = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login").then().extract().body().path("id");


        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }

}
