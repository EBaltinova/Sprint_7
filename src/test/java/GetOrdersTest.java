import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.example.ForOrderRequest.getOrders;
import static org.junit.Assert.assertEquals;

public class GetOrdersTest {

    @Test
    public void testOrdersCanBeReturn() {
        ValidatableResponse response = getOrders();
        int statusCode = response.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

}
