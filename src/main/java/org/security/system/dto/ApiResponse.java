package org.security.system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {
	private boolean success;
    private int code; // HTTP status code hoặc mã lỗi nghiệp vụ
    private String message;
    private T data;  // Có thể là EntityModel<UserDto>, CollectionModel<EntityModel<UserDto>>, ...
    private Object meta;// Dữ liệu phụ trợ (paging, audit, trace...)
    
    public ApiResponse() {}

    public ApiResponse(boolean success, int code, String message, T data, Object meta) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.meta = meta;
    }
    // Static factory method
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, 200, message, data, null);
    }

    public static <T> ApiResponse<T> success(String message, T data, Object meta) {
        return new ApiResponse<>(true, 200, message, data, meta);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(false, code, message, null, null);
    }

    public static <T> ApiResponse<T> error(int code, String message, Object meta) {
        return new ApiResponse<>(false, code, message, null, meta);
    }
}
