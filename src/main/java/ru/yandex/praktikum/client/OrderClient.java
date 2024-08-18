package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.CreateOrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    public static final String ORDER_URL = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(CreateOrderRequest createOrderRequest) {
        return given()
                .spec((getSpec()))
                .body(createOrderRequest)
                .when()
                .post(ORDER_URL)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER_URL)
                .then();
    }
}
