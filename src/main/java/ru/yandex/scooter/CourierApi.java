package ru.yandex.scooter;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    public static Response successCreate(String login, String password, String firstName) {
        Courier courier = new Courier(login, password, firstName);
        return  given().header("Content-type", "application/json")
                .and().body(courier).when().post(ApiUrls.COURIER);
    }

    public static Response createCourierWithoutLoginField(String password) {
        Courier courier = new Courier(password);
        return  given().header("Content-type", "application/json")
                .and().body(courier).when().post(ApiUrls.COURIER);
    }

    public static Response successLogin(String login, String password) {
        Courier courier = new Courier(login, password);
        return given().header("Content-type", "application/json")
                .and().body(courier)
                .when().post(ApiUrls.LOGIN);
    }

    public static Response loginWithoutLoginField(String password) {
        Courier courier = new Courier(password);
        return given().header("Content-type", "application/json")
                .and().body(courier)
                .when().post(ApiUrls.LOGIN);
    }

    public static int getCourierId(String login, String password) {
        Response response = successLogin(login, password);
        return response.then().extract().body().path("id");
    }

    public static Response deleteCourier(String login, String password) {
        return given().header("Content-type", "application/json")
                .when().delete(ApiUrls.COURIER + "/" + getCourierId(login, password));
    }
}
