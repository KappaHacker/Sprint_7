import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.OrderClient;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Успешное получение списка заказов")
    public void getOrderList() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse responseOrderList = orderClient.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());

    }
}
