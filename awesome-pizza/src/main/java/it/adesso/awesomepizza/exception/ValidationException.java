package it.adesso.awesomepizza.exception;

public class ValidationException extends RuntimeException {
    public ValidationException() {}

    public ValidationException(String message) {
        super(message);
    }
}
