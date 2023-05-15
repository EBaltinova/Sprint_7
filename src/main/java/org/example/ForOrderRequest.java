package org.example;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ForOrderRequest extends Client {
    @Step("Создание заказа")
    public ValidatableResponse create(ForOrderData forOrderData) {
        return given()
                .spec(getSpec())
                .body(forOrderData).log().all()
                .when()
                .post("/api/v1/orders")
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public static ValidatableResponse getOrders() {
        return given()
                .spec(getSpec()).log().all()
                .when()
                .get("/api/v1/orders")
                .then().log().all();
    }

}
