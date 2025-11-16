package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Schema(description = "Custom error response for all APIs")
public class ErrorResponse implements Serializable {
    @Schema(description = "Error response message")
    private String message;

    @Schema(description = "HTTP status")
    private int status;

    public ErrorResponse() {
    }
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
