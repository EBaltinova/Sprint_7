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
    public void testCourierCanBeCreated() {
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier)); // для логина, чтоб получить id

        id = loginResponse.extract().path("id");  // Вытащить id, что передать его в cleanUpData, для удаления курьера после тестирования

        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode); //  Переиспользовать код статуса из интерфейса HttpStatus
    }

    @Test
    public void testCourierCantBeCreatedWithSameData() {
        ValidatableResponse responseFirstCourier = courierClient.create(courier);
        ValidatableResponse responseSecondCourier = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = responseSecondCourier.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
    }

    @Test
    public void testCourierCantBeCreatedWithoutNecessaryField() {
        ValidatableResponse response = courierClient.create(courierWithoutNecessaryField);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testCourierCantBeCreatedWithSameLogin() {
        ValidatableResponse responseFirstCourier = courierClient.create(courier);
        ValidatableResponse responseSecondCourierWithSameLogin = courierClient.create(courierWithSameLogin);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");

        int statusCode = responseSecondCourierWithSameLogin.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
    }

    @After
    public void cleanUpData() {
        courierClient.delete(id);
    }
}
