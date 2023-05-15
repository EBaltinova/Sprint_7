import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {
    private Courier courier;
    private Courier courierWithoutNecessaryField;
    private Courier courierWithSameLogin;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDefault();
        courierWithoutNecessaryField = CourierGenerator.getWithoutNecessaryField();
        courierWithSameLogin = CourierGenerator.getDefaultWithSameLogin();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Тест успешного создания курьера")
    public void testCourierCanBeCreated() {
        ValidatableResponse createResponse = courierClient.create(courier);
        ExtractableResponse<Response> extractedCreateResponse = createResponse.extract();

        assertEquals(SC_CREATED, extractedCreateResponse.statusCode());
        boolean isCourierCreated = extractedCreateResponse.jsonPath().getBoolean("ok");

        assertTrue(isCourierCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        ExtractableResponse<Response> extractedLoginResponse = loginResponse.extract();
        id = extractedLoginResponse.jsonPath().getInt("id"); // Передать id в cleanUpData, для удаления курьера после тестирования
    }

    @Test
    @DisplayName("Тест на невозможность создания курьера с повторяющимеся данными")
    public void testCourierCantBeCreatedWithSameData() {
        ValidatableResponse responseFirstCourier = courierClient.create(courier);
        ValidatableResponse responseSecondCourier = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = responseSecondCourier.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);

        String message = responseSecondCourier.extract().jsonPath().getString("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }

    @Test
    @DisplayName("Тест на невозможность создания курьера без необходимого поля")
    public void testCourierCantBeCreatedWithoutNecessaryField() {
        ValidatableResponse response = courierClient.create(courierWithoutNecessaryField);
        int statusCode = response.extract().statusCode();

        assertEquals(SC_BAD_REQUEST, statusCode);

        String message = response.extract().jsonPath().getString("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Тест на невозможность создания курьера с повторяющимся логином")
    public void testCourierCantBeCreatedWithSameLogin() {
        ValidatableResponse responseFirstCourier = courierClient.create(courier);
        ValidatableResponse responseSecondCourierWithSameLogin = courierClient.create(courierWithSameLogin);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = responseSecondCourierWithSameLogin.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);

        String message = responseSecondCourierWithSameLogin.extract().jsonPath().getString("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
    }

    @After
    public void cleanUpData() {
        courierClient.delete(id);
    }
}
