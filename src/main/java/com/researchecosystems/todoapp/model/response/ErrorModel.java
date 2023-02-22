package com.researchecosystems.todoapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorModel {

    private ZonedDateTime timestamp;
    private int statusCode;
    private String errorCode;
    private String message;

}
