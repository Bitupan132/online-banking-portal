package xyz.team1.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import xyz.team1.constants.Constants;
import xyz.team1.model.Transaction;
import xyz.team1.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> addTransaction(Transaction transaction,String token) {
        try {
            updateBalanceForTransaction(transaction.getSenderAccountNo(), transaction.getReceiverAccountNo(),
                    transaction.getAmount(),token);
            LocalDateTime time = LocalDateTime.now();
            transaction.setTransactionDateTime(time);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("Transaction successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Transaction Failed! " + e.getMessage());
        }
    }

    public void updateBalanceForTransaction(String senderAccountNo, String receiverAccountNo,
            Double transferAmount,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+token);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("senderAccountNo", senderAccountNo);
        requestBody.put("receiverAccountNo", receiverAccountNo);
        requestBody.put("transferAmount", transferAmount);
        
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.postForEntity(Constants.userAccountMgmtUrl + "/account/updateBalanceForTransaction", requestEntity,
                String.class);
    }

    public List<Transaction> getTransactionForAccount(String AccountNo){
    	
    	List<Transaction> trans = transactionRepository.findBySenderAccountNo(AccountNo);
    	trans.addAll(transactionRepository.findByReceiverAccountNo(AccountNo));
    	Collections.sort(trans, Comparator.comparing(Transaction::getTransactionDateTime));
    	
    	return trans;
    }
}
