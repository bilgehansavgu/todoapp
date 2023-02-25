package com.researchecosystems.todoapp.model.request.task;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;

@Data
@ToString
public class CreateTaskRequest {
    @NotEmpty(message = "description must be given.")
    private String description;

    private ZonedDateTime due;
}
