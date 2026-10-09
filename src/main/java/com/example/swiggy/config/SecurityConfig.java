package com.example.swiggy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.swiggy.security.CustomAccessDeniedHandler;
import com.example.swiggy.security.CustomAuthenticationEntryPoint;
import com.example.swiggy.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

import com.example.swiggy.service.CustomUserDetailsService;


@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    //Constuctor Injection
    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler) {

        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            
            .cors(Customizer.withDefaults())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )
            
            .exceptionHandling(exception ->
            exception
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        )

        .httpBasic(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth

            	    .requestMatchers(
            	        "/swagger-ui/**",
            	        "/v3/api-docs/**",
            	        "/api/auth/register",
            	        "/api/auth/login"
            	    ).permitAll()

            	    .requestMatchers(
            	        HttpMethod.DELETE,
            	        "/api/orders/**"
            	    ).hasRole("ADMIN")

            	    .requestMatchers(
            	        HttpMethod.GET,
            	        "/api/orders/**"
            	    ).hasAnyRole("USER", "ADMIN")

            	    .requestMatchers(
            	        HttpMethod.POST,
            	        "/api/orders/**"
            	    ).hasAnyRole("USER", "ADMIN")

            	    .requestMatchers(
            	        HttpMethod.PUT,
            	        "/api/orders/**"
            	    ).hasAnyRole("USER", "ADMIN")

            	    .anyRequest().authenticated()
            	)

            .userDetailsService(userDetailsService)

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}