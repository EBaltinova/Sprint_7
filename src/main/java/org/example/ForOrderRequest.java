package org.example;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class ForOrderRequest extends Client {

    public ValidatableResponse create(ForOrderData forOrderData) {
        return given()
                .spec(getSpec())
                .body(forOrderData).log().all()
                .when()
                .post("/api/v1/orders")
                .then().log().all();
    }

    public static ValidatableResponse getOrders() {
        return given()
                .spec(getSpec()).log().all()
                .when()
                .get("/api/v1/orders")
                .then().log().all();
    }

}
