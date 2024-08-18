package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.CreateCourierRequest;
import ru.yandex.praktikum.pojo.LoginCourierRequest;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    public static final String CREATE_URL = "/api/v1/courier";
    public static final String LOGIN_URL = "/api/v1/courier/login";
    public static final String DELETE_URL = "/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CreateCourierRequest createCourierRequest) {
        return given()
                .spec(getSpec())
                .body(createCourierRequest)
                .when()
                .post(CREATE_URL)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(LoginCourierRequest loginCourierRequest) {
        return given()
                .spec(getSpec())
                .body(loginCourierRequest)
                .when()
                .post(LOGIN_URL)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getSpec())
                .delete(DELETE_URL + id)
                .then();
    }
}