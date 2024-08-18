import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.dataprovider.CourierProvider;
import ru.yandex.praktikum.pojo.CreateCourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
    private int id;

    private CourierClient courierClient = new CourierClient();

    @DisplayName("Проверка создания курьера")
    @Description("Должен вернуться статус 200 и в теле сообщение 'ок : true' ")
    @Test
    public void courierShouldBeCreated() {
        CreateCourierRequest createCourierRequest = CourierProvider.getRandomCreateCourierRequest();
        courierClient.createCourier(createCourierRequest)
                .statusCode(201)
                .body("ok", Matchers.equalTo(true));

        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        id = courierClient.loginCourier(loginCourierRequest)
                .statusCode(200)
                .extract().jsonPath().get("id");
    }

    @DisplayName("Проверка, что нельзя создать двух одинаковых курьеров")
    @Description("Должна вернуться ошибка 409 'Этот логин уже используется. Попробуйте другой'")
    @Test
    public void repeatedCourierShouldBeCreated() {
        CreateCourierRequest createCourierRequest = CourierProvider.getRandomCreateCourierRequest();
        courierClient.createCourier(createCourierRequest);
        courierClient.createCourier(createCourierRequest)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Проверка создания курьера без логина")
    @Description("Должна вернуться ошибка 400 'Недостаточно данных для создания учетной записи'")
    @Test
    public void createCourierWithoutLogin() {
        CreateCourierRequest createCourierRequest = new CreateCourierRequest();
        createCourierRequest.setLogin("");
        createCourierRequest.setPassword("1235");
        createCourierRequest.setFirstName("йуйоа");
        courierClient.createCourier(createCourierRequest)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Проверка создания курьера без пароля")
    @Description("Должна вернуться ошибка 400 'Недостаточно данных для создания учетной записи'")
    @Test
    public void createCourierWithoutPassword() {
        CreateCourierRequest createCourierRequest = new CreateCourierRequest();
        createCourierRequest.setLogin("TestLogin");
        createCourierRequest.setPassword("");
        createCourierRequest.setFirstName("вцйпйй");
        courierClient.createCourier(createCourierRequest)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        if (id != 0) {
            courierClient.deleteCourier(id)
                    .statusCode(200);
        }
    }
}
