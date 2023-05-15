package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String API_V1_COURIER_ENTRY_POINT = "/api/v1/courier";

    @Step("Выполнить запрос на создание курьера")
    public ValidatableResponse create(Courier courier) {

        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(API_V1_COURIER_ENTRY_POINT)
                .then().log().all();
    }

    @Step("Выполнить запрос для авторизацию курьера")
    public ValidatableResponse login(CourierCredentials credentials) {

        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(API_V1_COURIER_ENTRY_POINT + "/login")
                .then().log().all();
    }

    @Step("Выполнить запрос для удаления курьера")
    public ValidatableResponse delete(int id) {

        return given()
                .spec(getSpec())
                .when()
                .delete(API_V1_COURIER_ENTRY_POINT + '/' + id)
                .then();

    }

}
