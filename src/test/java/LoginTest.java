import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.BaseSetUp;
import ru.yandex.scooter.CourierApi;
import ru.yandex.scooter.CourierId;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    String login = "driver8";
    String password = "12345";
    String firstName = "Lana";
    String wrongPassword = "123456";
    String wrongLogin = "wrongLogin";

    @Before
    public void setUp() {
        BaseSetUp.setUp();
        CourierApi.successCreate(login, password, firstName);
    }


    @Test
    @DisplayName("Check whether success login returns courier id")
    public void successLoginShouldReturnId() {
        Response response = CourierApi.successLogin(login, password);
        response.body().as(CourierId.class);
    }

    @Test
    @DisplayName("Check error when request body hasn't required field")
    public void loginWithoutRequiredFieldShouldReturnError() {
        Response response = CourierApi.loginWithoutLoginField(password);
        response.then().body("message", equalTo("Недостаточно данных для входа")).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check error when request body has incorrect password")
    public void loginWithWrongPasswordShouldReturnError() {
        Response response = CourierApi.successLogin(login, wrongPassword);
        response.then().body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check error when request body has incorrect login field")
    public void loginWithWrongLoginShouldReturnError() {
        Response response = CourierApi.successLogin(wrongLogin, password);
        response.then().body("message", equalTo("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @After
    public void tearDown() {
        try {
            CourierApi.deleteCourier(login, password).then().assertThat().statusCode(SC_OK);
        } catch (NullPointerException exception) {
            System.out.println("Невозможно удалить несуществующего клиента!");
        }
    }
}
