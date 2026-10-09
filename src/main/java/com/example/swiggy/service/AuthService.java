package com.example.swiggy.service;

import com.example.swiggy.request.LoginRequest;

import com.example.swiggy.request.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    String login(LoginRequest request);
    
}