package com.researchecosystems.todoapp.controller;

import com.researchecosystems.todoapp.entity.Block;
import com.researchecosystems.todoapp.entity.DatasetAccessType;
import com.researchecosystems.todoapp.entity.Record;
import com.researchecosystems.todoapp.model.request.task.CreateTaskRequest;
import com.researchecosystems.todoapp.model.request.task.UpdateTaskRequest;
import com.researchecosystems.todoapp.model.response.task.TaskResponse;
import com.researchecosystems.todoapp.service.AuthenticationService;
import com.researchecosystems.todoapp.service.BlockchainService;
import com.researchecosystems.todoapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/blockchain")
public class BlockchainController {
    private final BlockchainService blockchainService;
    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public List<Block> blockchainQueryAllConsents(){
        return blockchainService.blockchainQueryAllConsents();
    }
/*
    public Record blockchainCreatePermission(@RequestBody String userName, int consentNumber, DatasetAccessType permissionType) {
        return blockchainService.blockchainCreatePermission(userName, consentNumber, permissionType);
    }
*/
/*
    public Record blockchainChangeConsentPermission(int consentNumber, DatasetAccessType permissionType){
        return blockchainService.blockchainChangeConsentPermission(consentNumber, permissionType);
    }

 */
}
