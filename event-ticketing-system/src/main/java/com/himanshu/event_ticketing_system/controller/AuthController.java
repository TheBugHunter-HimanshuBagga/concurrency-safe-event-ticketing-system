package com.himanshu.event_ticketing_system.controller;

import com.himanshu.event_ticketing_system.dto.LoginRequest;
import com.himanshu.event_ticketing_system.dto.LoginResponse;
import com.himanshu.event_ticketing_system.dto.RegisterRequest;
import com.himanshu.event_ticketing_system.dto.UserResponse;
import com.himanshu.event_ticketing_system.entity.User;
import com.himanshu.event_ticketing_system.exception.ApiResponse;
import com.himanshu.event_ticketing_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@RequestBody @Valid RegisterRequest registerRequest){
        UserResponse userResponse = userService.registerUser(registerRequest);
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody @Valid LoginRequest loginRequest){
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(loginResponse);
        return ResponseEntity.ok(apiResponse);
    }

}
