package com.researchecosystems.todoapp.controller;

import com.researchecosystems.todoapp.entity.Block;
import com.researchecosystems.todoapp.entity.DatasetAccessType;
import com.researchecosystems.todoapp.entity.Record;
import com.researchecosystems.todoapp.model.request.consent.CreateConsentRequest;
import com.researchecosystems.todoapp.model.request.consent.UpdateConsentRequest;
import com.researchecosystems.todoapp.service.AuthenticationService;
import com.researchecosystems.todoapp.service.BlockchainService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Record blockchainCreatePermission(@RequestBody CreateConsentRequest createConsentRequest) {
        return blockchainService.blockchainCreatePermission(createConsentRequest);
    }

    @PutMapping("/{id}")
    public Record blockchainChangeConsentPermission(@RequestBody UpdateConsentRequest updateConsentRequest){
        return blockchainService.blockchainChangeConsentPermission(updateConsentRequest);
    }



}
