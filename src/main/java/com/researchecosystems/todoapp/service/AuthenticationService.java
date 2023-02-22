package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.entity.User;
import com.researchecosystems.todoapp.exception.BusinessException;
import com.researchecosystems.todoapp.exception.ErrorCode;
import com.researchecosystems.todoapp.model.request.auth.LoginRequest;
import com.researchecosystems.todoapp.model.response.login.LoginResponse;
import com.researchecosystems.todoapp.repository.UserRepository;
import com.researchecosystems.todoapp.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        //TODO: remove for disabling login without verification
//        if (!user.isVerified()) {
//            throw new BusinessException(ErrorCode.validation, "User is not verified.");
//        }

        return LoginResponse.builder()
                .id(user.getId())
                .token(jwtService.createToken(user.getId()))
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public String getAuthenticatedUserId() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.equals("anonymousUser")) {
            throw new BusinessException(ErrorCode.unauthorized, "Unauthorized user!");
        }
        return principal;
    }
}