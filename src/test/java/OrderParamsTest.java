import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.pojo.CreateOrderRequest;

import java.time.LocalDate;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderParamsTest {

    private final CreateOrderRequest createOrderRequest;
    OrderClient orderClient = new OrderClient();

    public OrderParamsTest(CreateOrderRequest createOrderRequest) {
        this.createOrderRequest = createOrderRequest;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderRequest() {
        return new Object[][]{
                {new CreateOrderRequest("Влад", "Сентяков", "Проспект Ветеранов 132", "5", "+79234624214", 5, String.valueOf(LocalDate.now()), "с полным зарядом", List.of("GREY"))},
                {new CreateOrderRequest("Проект", "Спринтов", "Невский проспкт 1", "1", "+79789432345", 1, String.valueOf(LocalDate.now()), "хочу самокат", List.of("BLACK"))},
                {new CreateOrderRequest("Иван", "Иванов", "Арбат 13", "1", "+79124121124", 3, String.valueOf(LocalDate.now()), "желательно по быстрее", List.of("GREY", "BLACK"))},
                {new CreateOrderRequest("Тест", "Тестовый", "Рублёвское шоссе", "11", "+79456789323", 7, String.valueOf(LocalDate.now()), "по-быстрее", List.of())},
        };
    }

    @DisplayName("Параметризированная проверка создания заказа")
    @Description("Должен вернуться код 201 и тело ответа содержит track")
    @Test
    public void orderShouldBeCreated() {
        orderClient.createOrder(createOrderRequest)
                .statusCode(201)
                .body("track", Matchers.notNullValue());
    }
}
