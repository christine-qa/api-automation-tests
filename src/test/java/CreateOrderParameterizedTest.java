import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.scooter.BaseSetUp;
import ru.yandex.scooter.OrderApi;
import ru.yandex.scooter.Track;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final String filePath;

    public CreateOrderParameterizedTest(String filePath) {
        this.filePath = filePath;
    }

    @Parameterized.Parameters // добавили аннотацию
    public static Object[][] getJsonData() {
        return new Object[][] {
                {"src/test/resources/createorderWithGreyColors.json"},
                {"src/test/resources/createorderWithBlackColors.json"},
                {"src/test/resources/createorderWithNoColor.json"},
                {"src/test/resources/createorderWithAllColors.json"}
        };
    }

    @Before
    public void setUp() {
        BaseSetUp.setUp();
    }

    @Test
    @DisplayName("Check order creation with black color of scooter in body params")
    public void createOrderWithDifferentColorsTest() {
        Response response = OrderApi.createOrderWithAllRequiredFields(filePath);
        response.then().assertThat().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Check whether response body has track number")
    public void createOrderResponseShouldHaveTrackTest () {
        Response response = OrderApi.createOrderWithAllRequiredFields(filePath);
        response.body().as(Track.class);
    }

    @After
    public void tearDown() {
        OrderApi.deleteOrder(filePath).then().assertThat().statusCode(SC_OK);
    }
}
