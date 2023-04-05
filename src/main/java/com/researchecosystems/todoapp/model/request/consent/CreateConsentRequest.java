package com.researchecosystems.todoapp.model.request.consent;

import com.researchecosystems.todoapp.entity.DatasetAccessType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateConsentRequest {

    private String userName;
    private int consentNumber;
    private DatasetAccessType permissionType;
}
