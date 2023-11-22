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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import xyz.team1.constants.Constants;
import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Transaction;
import xyz.team1.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeignClientInterface feign;

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public String addTransaction(Transaction transaction, String token){
        ResponseEntity<String> response = updateBalanceForTransaction(transaction.getSenderAccountNo(),
                transaction.getReceiverAccountNo(),
                transaction.getAmount(), token);
        if (response.getStatusCode().is4xxClientError()) {
            return response.getBody();
        }
        if (response.getStatusCode().is5xxServerError()) {
            return response.getBody();
        }
        LocalDateTime time = LocalDateTime.now();
        transaction.setTransactionDateTime(time);
        transactionRepository.save(transaction);
        return "Transaction successful!";
    }

    public ResponseEntity<String> updateBalanceForTransaction(String senderAccountNo, String receiverAccountNo,
            Double transferAmount, String token) throws RuntimeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("senderAccountNo", senderAccountNo);
        requestBody.put("receiverAccountNo", receiverAccountNo);
        requestBody.put("transferAmount", transferAmount);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
           return restTemplate.postForEntity(Constants.userAccountMgmtUrl +
                    "/account/updateBalanceForTransaction",
                    requestEntity,
                    String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    public List<Transaction> getTransactionForAccount(String AccountNo) {

        List<Transaction> trans = transactionRepository.findBySenderAccountNo(AccountNo);
        trans.addAll(transactionRepository.findByReceiverAccountNo(AccountNo));
        Collections.sort(trans, Comparator.comparing(Transaction::getTransactionDateTime).reversed());
        return trans;
    }

    public boolean validateToken(String authorizationHeader) {
        String jwtToken = authorizationHeader.substring(7);
        String validationStatus = feign.validateToken(jwtToken);
        if (Constants.tokenValidString.equals(validationStatus)) {
            return true;
        }
        return false;
    }
}
