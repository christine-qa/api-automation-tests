import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.scooter.BaseSetUp;
import ru.yandex.scooter.OrderApi;
import ru.yandex.scooter.OrderList;

public class OrderListTest {

    @Before
    public void setUp() {
       BaseSetUp.setUp();
    }

    @Test
    @DisplayName("Check whether list of orders returns in response body")
    public void shouldReturnOrderList () {
        Response response = OrderApi.getOrderList();
        response.body().as(OrderList.class);
    }
}
