package ru.yandex.scooter;

import io.restassured.RestAssured;

public class BaseSetUp {

    public static void setUp() {
        RestAssured.baseURI = ApiUrls.BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
