import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierLoginTest {
    private Courier specificCourier;
    private Courier courierWithoutNecessaryField;
    private Courier courierWithNotRightLogin;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        specificCourier = CourierGenerator.getSpecificCourier();
        courierWithoutNecessaryField = CourierGenerator.getWithoutNecessaryField();
        courierWithNotRightLogin = CourierGenerator.getSpecificCourierWithNotRightLogin();
        courierClient = new CourierClient();
    }

    @Test
    public void testCourierCanBeLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(specificCourier));
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode); //  Переиспользовать код статуса из интерфейса HttpStatus
    }

    @Test
    public void testCourierCantBeLoginWithoutNecessaryField() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierWithoutNecessaryField));
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    public void testCourierCantBeLoginWithNotRightLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courierWithNotRightLogin));
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
    }

}
