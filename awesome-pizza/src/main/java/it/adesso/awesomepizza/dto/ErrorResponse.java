package it.adesso.awesomepizza.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ErrorResponse implements Serializable {
    private String message;
    private int status;

    public ErrorResponse() {
    }
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
