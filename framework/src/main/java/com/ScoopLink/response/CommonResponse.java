package com.ScoopLink.response;

public class CommonResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private long timestamp;

    public CommonResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public CommonResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
    }

    public CommonResponse(boolean success, String message, T data) {
        this(success, message);
        this.data = data;
    }

    // 静态方法用于创建成功的响应
    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(true, "操作成功");
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, "操作成功", data);
    }

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(true, message, data);
    }

    // 静态方法用于创建失败的响应
    public static <T> CommonResponse<T> error(String message) {
        return new CommonResponse<>(false, message);
    }

    public static <T> CommonResponse<T> error(String code, String message) {
        CommonResponse<T> response = new CommonResponse<>(false, message);
        // 这里可以扩展包含错误代码
        return response;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
