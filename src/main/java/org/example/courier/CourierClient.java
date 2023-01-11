package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String COURIER_CREATE_PATH = "/api/v1/courier"; // создание курьера
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login"; //логин курьера
    public static final String COURIER_DELETE_PATH = "/api/v1/courier/"; //удаление курьера (+id для удаления /api/v1/courier/:id)
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(credentials)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int сourierId) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .when()
                .delete(COURIER_DELETE_PATH + сourierId)
                .then();
    }
}
