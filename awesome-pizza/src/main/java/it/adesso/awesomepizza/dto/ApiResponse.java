package it.adesso.awesomepizza.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T>  {
    private boolean success;
    private String message;
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
