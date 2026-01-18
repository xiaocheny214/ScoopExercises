package com.ScoopLink.handle;


import com.ScoopLink.exception.BusinessException;
import com.ScoopLink.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.ScoopLink.controller") // 只处理controller包下的异常
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                "Business Exception",
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false).replace("uri=", "")
        );
        errorResponse.setSuccess(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 处理参数验证异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = new ErrorResponse(
                "参数验证失败",
                "Validation Failed",
                HttpStatus.BAD_REQUEST.value(),
                errors,
                request.getDescription(false).replace("uri=", "")
        );
        errorResponse.setSuccess(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 处理参数绑定异常
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindExceptions(BindException e, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // 添加对象级错误
        e.getBindingResult().getGlobalErrors().forEach(error ->
                errors.put(error.getObjectName(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = new ErrorResponse(
                "参数绑定失败",
                "Bind Exception",
                HttpStatus.BAD_REQUEST.value(),
                errors,
                request.getDescription(false).replace("uri=", "")
        );
        errorResponse.setSuccess(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 处理所有未捕获的异常 - 但排除静态资源相关异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e, WebRequest request) {
        // 如果是NoHandlerFoundException且是静态资源请求，不处理
        if (e instanceof NoHandlerFoundException) {
            String requestUri = request.getDescription(false);
            if (isStaticResourceRequest(requestUri)) {
                throw new RuntimeException(e);
            }
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
                "系统内部错误: " + e.getMessage(),
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false).replace("uri=", "")
        );
        errorResponse.setSuccess(false);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private boolean isStaticResourceRequest(String requestUri) {
        return requestUri.contains("/assets/") || requestUri.contains("/static/") || 
               requestUri.contains(".js") || requestUri.contains(".css") || 
               requestUri.contains(".png") || requestUri.contains(".jpg") || 
               requestUri.contains(".svg") || requestUri.contains(".ico") ||
               requestUri.contains("/vite.svg") || requestUri.contains("/favicon.ico");
    }
}