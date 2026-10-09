package com.example.swiggy.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.swiggy.entity.User;
import com.example.swiggy.repository.UserRepository;
import com.example.swiggy.request.LoginRequest;
import com.example.swiggy.request.RegisterRequest;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImplementation(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public String register(RegisterRequest request) {

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public String login(LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        return jwtService.generateToken(
                request.getUsername()
        );
    }
}