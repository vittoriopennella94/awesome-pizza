package it.adesso.awesomepizza.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Custom response for all APIs")
public class ApiResponse<T>  {
    @Schema(description = "Result of the request")
    private boolean success;

    @Schema(description = "Message")
    private String message;

    @Schema(description = "Response data")
    private T data;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> successNoMessage(T data) {
        return new ApiResponse<>(true, null, data);
    }

    public static <T> ApiResponse<T> error(T data, String message) {
        return new ApiResponse<>(false, message, data);
    }

    public static <T> ApiResponse<T> errorNoMessage(T data) {
        return new ApiResponse<>(false, null, data);
    }
}
