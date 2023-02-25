package com.researchecosystems.todoapp.model.request.user;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class UpdateUserRequest {

    @Email(message = "Invalid email")
    @NotEmpty
    private String email;

    @NotEmpty(message = "name must be given.")
    private String name;

    @NotEmpty(message = "surname must be given.")
    private String surname;
}