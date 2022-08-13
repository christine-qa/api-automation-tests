import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.BaseSetUp;
import ru.yandex.scooter.CourierApi;
import ru.yandex.scooter.SuccessCourierCreationResp;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {

    String login = "deliver0808";
    String password = "12345";
    String firstName = "deliver";

    @Before
    public void setUp() {
        BaseSetUp.setUp();
    }

    @Test
    @DisplayName("Check status code when sending data with all required fields")
    public void shouldCreateCourierAndReturn201Test() {
        Response response = CourierApi.successCreate(login, password, firstName);
        response.then().assertThat().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Check response body when sending data with all required fields")
    public void successCreationShouldReturnOkTest() {
        Response response = CourierApi.successCreate(login, password, firstName);
        response.body().as(SuccessCourierCreationResp.class);
    }

    @Test
    @DisplayName("Check error and body message when sending data without login")
    public void requestWithoutRequiredFieldShouldReturnError() {
        Response response = CourierApi.createCourierWithoutLoginField(password);
        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check error and body message when sending data with already existed login")
    public void shouldNotLetCreateTwoCouriersWithTheSameLoginTest() {
        CourierApi.successCreate(login, password, firstName);
        Response response = CourierApi.successCreate(login, password, firstName);
        response.then().assertThat().statusCode(SC_CONFLICT)
                .and().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
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
