import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.dataprovider.CourierProvider;
import ru.yandex.praktikum.pojo.CreateCourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

public class CourierLoginTest {
    CourierClient courierClient = new CourierClient();
    private Integer id;

    @DisplayName("Проверка, что курьер может авторизоваться")
    @Description("Должен вернуться статус код 200 и должен вернуться id курьера")
    @Test
    public void courierShouldBeLogin() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        courierClient.createCourier(createCourierRequest);
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        id = courierClient.loginCourier(loginCourierRequest)
                .statusCode(200)
                .extract().jsonPath().get("id");
    }

    @DisplayName("Проверка что курьер не может авторизоваться с неверным паролем")
    @Description("Должна вернуться ошибка 404 'Учетная запись не найдена'")
    @Test
    public void courierNoShouldBeLoginInvalidPassword() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.createCourier(createCourierRequest);
        createCourierRequest.setPassword("invalid_password");
        courierClient.loginCourier(LoginCourierRequest.from(createCourierRequest))
                .statusCode(404)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Проверка, что курьер не может авторизоваться с неправильным логином или несуществующим пользователем")
    @Description("Должна вернуться ошибка 404 'Учетная запись не найдена'")
    @Test
    public void courierNoShouldBeLoginInvalidLogin() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.createCourier(createCourierRequest);
        createCourierRequest.setLogin("invalid_login");
        courierClient.loginCourier(LoginCourierRequest.from(createCourierRequest))
                .statusCode(404)
                .body("message", Matchers.equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Проверка, что запрос возвращает ошибку если авторизовываться без логина")
    @Description("Должна вернуться ошибка 400 'Недостаточно данных для входа'")
    @Test
    public void courierNoShouldBeLoginNoLogin() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.createCourier(createCourierRequest);
        createCourierRequest.setLogin("");
        courierClient.loginCourier(LoginCourierRequest.from(createCourierRequest))
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Проверка, что запрос возвращает ошибку если авторизовываться без пароля")
    @Description("Должна вернуться ошибка 400 'Недостаточно данных для входа'")
    @Test
    public void courierNoShouldBeLoginNoPassword() {
        CreateCourierRequest createCourierRequest = new CourierProvider().getRandomCreateCourierRequest();
        LoginCourierRequest loginCourierRequest = LoginCourierRequest.from(createCourierRequest);
        courierClient.createCourier(createCourierRequest);
        createCourierRequest.setPassword("");
        courierClient.loginCourier(LoginCourierRequest.from(createCourierRequest))
                .statusCode(400)
                .body("message", Matchers.equalTo("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.deleteCourier(id)
                    .statusCode(200);
        }
    }
}
