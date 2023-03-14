package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.entity.DatasetAccessType;
import com.researchecosystems.todoapp.entity.Block;
import com.researchecosystems.todoapp.entity.Record;
import com.researchecosystems.todoapp.service.client.BlockchainClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BlockchainService {

    private final BlockchainClient blockchainClient;

    public List<Block> blockchainQueryAllConsents(){
        return blockchainClient.queryAllConsents();

    }

    public Record blockchainCreatePermissionTest(String userName, int consentNumber, DatasetAccessType permissionType) {
        return blockchainClient.createConsent(userName, consentNumber, permissionType);
    }


    public Record blockchainChangeConsentPermission(int consentNumber, DatasetAccessType permissionType){
        return blockchainClient.changeConsentPermission(consentNumber, permissionType);
    }
}
