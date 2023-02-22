package com.researchecosystems.todoapp.controller;

import com.researchecosystems.todoapp.model.request.auth.LoginRequest;
import com.researchecosystems.todoapp.model.request.auth.RegisterRequest;
import com.researchecosystems.todoapp.model.response.login.LoginResponse;
import com.researchecosystems.todoapp.service.AuthenticationService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {
        authenticationService.register(registerRequest);
    }
}
