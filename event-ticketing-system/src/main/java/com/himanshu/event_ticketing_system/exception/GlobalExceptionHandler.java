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
                .status(HttpStatus.BAD_REQUEST) // 400
                .build();
        ApiResponse<?> response = new ApiResponse<>(apiError); // this wraps the ApiError inside ApiResponse
        return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialsExceptionException(InvalidCredentialsException invalidCredentialsException){
        ApiError apiError = ApiError.builder()
                .message(invalidCredentialsException.getMessage())
                .status(HttpStatus.UNAUTHORIZED) // 401
                .build();
        return new ResponseEntity<>(new ApiResponse<>(apiError) , HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        ApiError apiError = ApiError.builder()
                .message(userNotFoundException.getMessage())
                .status(HttpStatus.NOT_FOUND)// 404
                .build();
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse , HttpStatus.NOT_FOUND);
    }

}
