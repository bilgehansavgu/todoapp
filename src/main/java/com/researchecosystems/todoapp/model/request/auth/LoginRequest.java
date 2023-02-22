package com.researchecosystems.todoapp.model.request.auth;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class LoginRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Length(min = 6)
    private String password;

}
