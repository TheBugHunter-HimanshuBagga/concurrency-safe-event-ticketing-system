package com.himanshu.event_ticketing_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException runtimeException){
        ApiError apiError = ApiError.builder()
                .message(runtimeException.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        ApiResponse<?> response = new ApiResponse<>(apiError); // this wraps the ApiError inside ApiResponse
        return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
    }

}
