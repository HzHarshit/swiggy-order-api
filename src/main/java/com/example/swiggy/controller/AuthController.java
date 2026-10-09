package com.example.swiggy.controller;

import org.springframework.http.HttpStatus;
import com.example.swiggy.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.swiggy.request.LoginRequest;
import com.example.swiggy.request.RegisterRequest;
import com.example.swiggy.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        String message = authService.register(request);

        return new ResponseEntity<>(
                message,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        LoginResponse response =
                new LoginResponse(
                        "Login successful",
                        token
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}