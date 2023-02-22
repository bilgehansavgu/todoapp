package com.researchecosystems.todoapp.model.response.task;

import com.researchecosystems.todoapp.entity.Task;
import com.researchecosystems.todoapp.model.response.user.UserResponse;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class TaskResponse {

    private String Index;

    private String description;

    private ZonedDateTime due;

    private boolean completed;

    private UserResponse owner;

    public static TaskResponse fromEntity(Task task) {

        return TaskResponse.builder()
                .Index(task.getIndex())
                .description(task.getDescription())
                .due(task.getDue())
                .completed(task.isCompleted())
                .owner(UserResponse.fromEntity(task.getOwner()))
                .build();
    }
}
