package it.adesso.awesomepizza.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiResponseTest {

    @Test
    public void apiResponseTest_Success() {
        ApiResponse<OrderDTO> test = ApiResponse.success(new OrderDTO(), "msg test");

        Assertions.assertEquals("msg test", test.getMessage());
        Assertions.assertNotNull(test.getData());
        Assertions.assertTrue(test.isSuccess());
    }

    @Test
    public void apiResponseTest_SuccessNoMessage() {
        ApiResponse<OrderDTO> test = ApiResponse.successNoMessage(new OrderDTO());

        Assertions.assertNull(test.getMessage());
        Assertions.assertNotNull(test.getData());
        Assertions.assertTrue(test.isSuccess());
    }

    @Test
    public void apiResponseTest_Error() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("msg error test");

        ApiResponse<ErrorResponse> test = ApiResponse.error(errorResponse, "msg test");

        Assertions.assertEquals("msg test", test.getMessage());
        Assertions.assertFalse(test.isSuccess());
        Assertions.assertNotNull(test.getData());
        Assertions.assertEquals(errorResponse.getMessage(), test.getData().getMessage());
    }

    @Test
    public void apiResponseTest_ErrorNoMessage() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("msg error test");

        ApiResponse<ErrorResponse> test = ApiResponse.errorNoMessage(errorResponse);

        Assertions.assertNull(test.getMessage());
        Assertions.assertFalse(test.isSuccess());
        Assertions.assertNotNull(test.getData());
        Assertions.assertEquals(errorResponse.getMessage(), test.getData().getMessage());
    }
}
