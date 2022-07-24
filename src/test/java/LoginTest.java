import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.Courier;
import ru.yandex.scooter.CourierId;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    Courier courierDataForCreate = new Courier("driver8", "12345", "Anna");
    Courier courierDataForId = new Courier("driver8", "12345");
    Courier courierDataForBadLogin = new Courier("12345");
    Courier courierDataWithWrongPassword = new Courier("driver8", "1234567");
    Courier courierDataWithWrongLogin = new Courier("driver9", "12345");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Check whether success login returns courier id")
    public void successLoginReturnId() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(201);

        Response response = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login");

        response.body().as(CourierId.class);

        int courierId = response.then().extract().body().path("id");

        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check error when request body hasn't required field")
    public void loginWithoutRequiredFieldShouldReturnError() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(201);

        Response badResponse = given().header("Content-type", "application/json")
                .and().body(courierDataForBadLogin)
                .when().post("/api/v1/courier/login");

        badResponse.then().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(400);

        Response response = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login");

        int courierId = response.then().extract().body().path("id");

        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check error when request body has incorrect password")
    public void loginWithWrongPasswordShouldReturnError() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(201);

        Response badResponse = given().header("Content-type", "application/json")
                .and().body(courierDataWithWrongPassword)
                .when().post("/api/v1/courier/login");

        badResponse.then().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);

        Response response = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login");

        int courierId = response.then().extract().body().path("id");

        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check error when request body has incorrect login field")
    public void loginWithWrongLoginShouldReturnError() {
        given().header("Content-type", "application/json")
                .and().body(courierDataForCreate).when().post("/api/v1/courier")
                .then().assertThat().statusCode(201);

        Response badResponse = given().header("Content-type", "application/json")
                .and().body(courierDataWithWrongLogin)
                .when().post("/api/v1/courier/login");

        badResponse.then().body("message", equalTo("Учетная запись не найдена")).and().statusCode(404);

        Response response = given().header("Content-type", "application/json")
                .and().body(courierDataForId)
                .when().post("/api/v1/courier/login");

        int courierId = response.then().extract().body().path("id");

        given().header("Content-type", "application/json")
                .when().delete("/api/v1/courier/" + courierId).then().assertThat().statusCode(200);
    }




}
