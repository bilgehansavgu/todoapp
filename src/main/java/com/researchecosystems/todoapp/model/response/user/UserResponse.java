package com.researchecosystems.todoapp.model.response.user;

import com.researchecosystems.todoapp.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class UserResponse {

    private String id;
    private String name;
    private String surname;
    private String email;

    public static UserResponse fromEntity(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }
}
