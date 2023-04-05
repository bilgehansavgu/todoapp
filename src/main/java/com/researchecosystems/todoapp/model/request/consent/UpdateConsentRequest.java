package com.researchecosystems.todoapp.model.request.consent;

import com.researchecosystems.todoapp.entity.DatasetAccessType;
import lombok.Data;

@Data
public class UpdateConsentRequest {

    private int consentNumber;
    private DatasetAccessType permissionType;
}
