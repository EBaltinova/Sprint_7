import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.ForOrderData;
import org.example.ForOrderRequest;
import org.example.OrderGenerator;
import org.example.ScooterColor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.example.ScooterColor.BLACK;
import static org.example.ScooterColor.GREY;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private ForOrderData forOrderData;
    private final List<ScooterColor> colors;
    private ForOrderRequest forOrderRequest;

    public CreateOrderTest(List<ScooterColor> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {List.of(GREY)},
                {List.of(BLACK,GREY)},
                {List.of()},
        };
    }

    @Before
    public void setUp() {
        forOrderData = OrderGenerator.getDefaultOrder().setColor(colors);
        forOrderRequest = new ForOrderRequest();
    }

    @Test
    @DisplayName("Тест что заказ может быть создан с использованием всех правильных полей")
    public void testOrderCanByCreatedWithAllCorrectField() {
        ValidatableResponse response = forOrderRequest.create(forOrderData);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
    }
}
