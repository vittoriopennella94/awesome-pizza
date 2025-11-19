package it.adesso.awesomepizza.exception;

import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static it.adesso.awesomepizza.utility.Constants.EXCEPTION_ERROR_MSG;
import static it.adesso.awesomepizza.utility.Constants.NOT_FOUND_EXCEPTION_ERROR_MSG;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(classes = GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void handleExceptionTest() {
        RuntimeException runtimeException = new RuntimeException();

        ResponseEntity<ApiResponse<ErrorResponse>> test = this.globalExceptionHandler.handleException(runtimeException);

        Assertions.assertNotNull(test.getBody());
        Assertions.assertEquals(INTERNAL_SERVER_ERROR, test.getStatusCode());
        Assertions.assertNotNull(test.getBody().getData());
        Assertions.assertEquals(INTERNAL_SERVER_ERROR.value(), test.getBody().getData().getStatus());
        Assertions.assertEquals(EXCEPTION_ERROR_MSG, test.getBody().getData().getMessage());
    }


    @Test
    public void handleNotFoundExceptionTest() {
        NotFoundException notFoundException = new NotFoundException();

        ResponseEntity<ApiResponse<ErrorResponse>> test = this.globalExceptionHandler.handleNotFoundException(notFoundException);

        Assertions.assertNotNull(test.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
        Assertions.assertNotNull(test.getBody().getData());
        Assertions.assertEquals(NOT_FOUND.value(), test.getBody().getData().getStatus());
        Assertions.assertEquals(NOT_FOUND_EXCEPTION_ERROR_MSG, test.getBody().getData().getMessage());
    }


    @Test
    public void handleValidationExceptionTest() {
        ValidationException validationException = new ValidationException("CustomerName is required");

        ResponseEntity<ApiResponse<ErrorResponse>> test = this.globalExceptionHandler.handleValidationException(validationException);

        Assertions.assertNotNull(test.getBody());
        Assertions.assertEquals(BAD_REQUEST, test.getStatusCode());
        Assertions.assertNotNull(test.getBody().getData());
        Assertions.assertEquals(BAD_REQUEST.value(), test.getBody().getData().getStatus());
        Assertions.assertEquals("CustomerName is required", test.getBody().getData().getMessage());
    }
}
