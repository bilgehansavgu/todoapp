package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.entity.User;
import com.researchecosystems.todoapp.exception.BusinessException;
import com.researchecosystems.todoapp.exception.ErrorCode;
import com.researchecosystems.todoapp.model.request.auth.LoginRequest;
import com.researchecosystems.todoapp.model.request.auth.RegisterRequest;
import com.researchecosystems.todoapp.model.response.login.LoginResponse;
import com.researchecosystems.todoapp.repository.UserRepository;
import com.researchecosystems.todoapp.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Service
@Transactional
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new BusinessException(ErrorCode.account_missing, "There are no user like that.");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.validation, "Wrong Password!");
        }


        return LoginResponse.builder()
                .id(user.getId())
                .token(jwtService.createToken(user.getId()))
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public void register(RegisterRequest registerRequest) {
        User existingUser = userRepository.findUserByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            throw new BusinessException(ErrorCode.forbidden, "There is already a registered email like that.");
        }

        User newUser = new User();

        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setSurname(registerRequest.getSurname());
        newUser.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setCreatedDate(ZonedDateTime.now());
        newUser.setModifiedDate(ZonedDateTime.now());

        userRepository.save(newUser);
    }

    public String getAuthenticatedUserId() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            throw new BusinessException(ErrorCode.unauthorized, "Unauthorized user!");
        }
        return principal;
    }
}