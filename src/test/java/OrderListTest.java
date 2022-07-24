import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.OrderList;

import static io.restassured.RestAssured.given;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Check whether list of orders returns in response body")
    public void shouldReturnOrderList () {
        Response response = given().header("Content-type", "application-json")
                .when().get("/api/v1/orders");
        response.body().as(OrderList.class);
    }

}
