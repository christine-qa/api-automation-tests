package ru.yandex.scooter;

import io.restassured.response.Response;
import java.io.File;

import static io.restassured.RestAssured.given;

public class OrderApi {

    public static Response createOrderWithAllRequiredFields(String filePath) {
        File file = new File(filePath);
        return given().header("Content-type", "application-json").and().body(file)
                .when().post(ApiUrls.ORDER);
    }

    public static int getOrderTrackNumber(String filePath) {
        Response response = OrderApi.createOrderWithAllRequiredFields(filePath);
        return response.then().extract().body().path("track");
    }

    public static Response deleteOrder(String filePath) {
        int trackNumber = OrderApi.getOrderTrackNumber(filePath);
        return given().header("Content-type", "application-json").and().queryParam("track", trackNumber)
                .when().put(ApiUrls.CANCEL_ORDER);
    }

    public static Response getOrderList() {
        return given().header("Content-type", "application-json")
                .when().get(ApiUrls.ORDER);
    }
}
