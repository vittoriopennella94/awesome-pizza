package it.adesso.awesomepizza.exception;

import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static it.adesso.awesomepizza.utility.Constants.*;

/**
 * Global exception handler for the application.
 *
 * <p> This class handles all exceptions thrown by the REST controllers
 * and converts them into custom standard error responses.</p>
 *
 * <p>All errors are returned with HTTP 200 OK status and include error
 * details in the response body following the ApiResponse format.</p>
 *
 * @author vittorio
 * @see RestControllerAdvice
 * @see ExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * Handles all unexpected exceptions that are not explicitly caught.
     *
     * @param exception the exception that was thrown
     * @return a ResponseEntity containing a generic error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception exception) {
        LOGGER.warn(exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse(EXCEPTION_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(ApiResponse.errorNoMessage(errorResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles exceptions when a requested resource is not found.
     *
     * @param exception the NotFoundException that was thrown
     * @return a ResponseEntity containing a not found error response
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFoundException(NotFoundException exception) {
        LOGGER.warn(exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION_ERROR_MSG, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(ApiResponse.errorNoMessage(errorResponse), HttpStatus.NOT_FOUND);
    }


    /**
     * Handles validation exceptions.
     *
     * @param exception the ValidationException that was thrown, containing validation error details
     * @return a ResponseEntity containing a validation error response with the specific error message
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(ValidationException exception) {
        LOGGER.warn(exception.getMessage(), exception);
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(ApiResponse.error(errorResponse, VALIDATION_EXCEPTION_ERROR_MSG), HttpStatus.BAD_REQUEST);
    }
}
