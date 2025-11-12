package it.adesso.awesomepizza.exception;

import it.adesso.awesomepizza.dto.ApiResponse;
import it.adesso.awesomepizza.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static it.adesso.awesomepizza.utility.Constants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(EXCEPTION_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.ok(ApiResponse.errorNoMessage(errorResponse));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFoundException(NotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION_ERROR_MSG, HttpStatus.OK.value());
        return ResponseEntity.ok(ApiResponse.errorNoMessage(errorResponse));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(ValidationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.OK.value());
        return ResponseEntity.ok(ApiResponse.error(errorResponse, VALIDATION_EXCEPTION_ERROR_MSG));
    }
}
