package com.researchecosystems.todoapp.service.client;

import java.nio.file.Path;
import java.nio.file.Paths;


import com.researchecosystems.todoapp.entity.DatasetAccessType;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.researchecosystems.todoapp.entity.Block;
import com.researchecosystems.todoapp.entity.Record;
import com.researchecosystems.todoapp.model.request.consent.CreateConsentRequest;
import com.researchecosystems.todoapp.model.request.consent.UpdateConsentRequest;
import lombok.SneakyThrows;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;


@Service
public class BlockchainClient {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    private Contract contract;
    private Network network;
    private Gateway gateway;

    @PostConstruct
    @SneakyThrows
    @Autowired
    public void init() {
// Create a CA client for interacting with the CA.
        enrollAdmin();
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get("/Users/bilgehansavgu/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "admin").networkConfig(networkConfigPath).discovery(true);
        gateway = builder.connect();
        network = gateway.getNetwork("mychannel");
        contract = network.getContract("gbeacon");
    }

    @SneakyThrows
    public List<Block> queryAllConsents() {
        byte[] result;
        result = contract.evaluateTransaction("queryAllConsents");
        System.out.println(new String(result));
        //[{"key":"CONSENT0","consent":{"owner":"Tomoko","permissionType":"PUBLIC"}}]
        JSONArray jsonArray = new JSONArray(new String(result));
        List<Block> blocks = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            Block block = new Block();
            Record record = new Record();
            String key = jsonArray.getJSONObject(i).getString("key");
            String owner = jsonArray.getJSONObject(i).getJSONObject("record").getString("owner");
            String permissionType = jsonArray.getJSONObject(i).getJSONObject("record").getString("permissionType");
            block.setKey(key);
            record.setOwner(owner);
            record.setPermissionType(DatasetAccessType.valueOf(permissionType));
            block.setRecord(record);
            blocks.add(block);
        }
        return blocks;
    }

    @SneakyThrows
    public Record createConsent(CreateConsentRequest createConsentRequest) {
        byte[] result;
        String consentKey = ("CONSENT" + createConsentRequest.getConsentNumber());
        contract.submitTransaction("createConsent", consentKey, createConsentRequest.getPermissionType().toString(), createConsentRequest.getUserName());
        result = contract.evaluateTransaction("queryConsent", consentKey);
        System.out.println(new String(result));
        return Record.fromJson(new String(result));
    }

    @SneakyThrows
    public Record changeConsentPermission(UpdateConsentRequest updateConsentRequest) {
        byte[] result;
        String consentKey = ("CONSENT" + updateConsentRequest.getConsentNumber());
        contract.submitTransaction("changeConsentPermission", consentKey, updateConsentRequest.getPermissionType().toString());
        result = contract.evaluateTransaction("queryConsent", consentKey);
        System.out.println(new String(result));
        return Record.fromJson(new String(result));
    }

    @SneakyThrows
    private void enrollAdmin(){
        Properties props = new Properties();
        props.put("pemFile",
                "/Users/bilgehansavgu/Desktop/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
        props.put("allowAllHostNames", "true");
        HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
        CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
        caClient.setCryptoSuite(cryptoSuite);

        // Create a wallet for managing identities
        Wallet wallet = Wallets.newFileSystemWallet(Paths.get("wallet"));

        // Check to see if we've already enrolled the admin user.
        if (wallet.get("admin") != null) {
            System.out.println("An identity for the admin user \"admin\" already exists in the wallet");
            return;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
        Identity user = Identities.newX509Identity("Org1MSP", enrollment);
        wallet.put("admin", user);
        System.out.println("Successfully enrolled user \"admin\" and imported it into the wallet");
    }
}