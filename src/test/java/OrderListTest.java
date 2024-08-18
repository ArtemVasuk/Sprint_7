import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import ru.yandex.praktikum.client.OrderClient;

public class OrderListTest {
    private OrderClient orderClient = new OrderClient();

    @DisplayName("Проверка получения списка заказов")
    @Description("Должен вернуться код 200 и в тело ответа должен вернуться список заказов")
    @Test
    public void getOrderListShouldBeVisible() {
        orderClient.getOrderList()
                .statusCode(200)
                .body("orders", Matchers.notNullValue());
    }
}
