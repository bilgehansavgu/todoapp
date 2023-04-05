package com.researchecosystems.todoapp.service;

import com.researchecosystems.todoapp.entity.DatasetAccessType;
import com.researchecosystems.todoapp.entity.Block;
import com.researchecosystems.todoapp.entity.Record;
import com.researchecosystems.todoapp.model.request.consent.CreateConsentRequest;
import com.researchecosystems.todoapp.model.request.consent.UpdateConsentRequest;
import com.researchecosystems.todoapp.service.client.BlockchainClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class BlockchainService {

    private BlockchainClient blockchainClient;

    public List<Block> blockchainQueryAllConsents(){
        return blockchainClient.queryAllConsents();
    }

    public Record blockchainCreatePermission(CreateConsentRequest createConsentRequest) {
        return blockchainClient.createConsent(createConsentRequest);
    }

    public Record blockchainChangeConsentPermission(UpdateConsentRequest updateConsentRequest){
        return blockchainClient.changeConsentPermission(updateConsentRequest);
    }
}
