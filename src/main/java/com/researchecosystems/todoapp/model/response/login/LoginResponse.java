package com.researchecosystems.todoapp.model.response.login;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class LoginResponse {

    private String id;
    private String token;
    private String name;
    private String surname;
}
