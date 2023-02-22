package com.researchecosystems.todoapp.model.response.user;

import com.researchecosystems.todoapp.entity.User;
import com.researchecosystems.todoapp.model.response.task.TaskResponse;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@Builder
public class UserResponse {

    private String id;
    private String name;
    private String surname;
    private String email;

    private List<TaskResponse> tasks;

    public static UserResponse fromEntity(User user) {

        List<TaskResponse> taskResponses = user.getTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .tasks(taskResponses)
                .build();
    }
}
