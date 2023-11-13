package xyz.team1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Transaction addTransaction(Transaction transaction) {
        //handle balance changes in the sender and receiver account.
        //and also what if save fails and you update the balance changes.
        return transactionRepository.save(transaction);
    }

    public String getAccountFromUserAccountMgmt(Long id){
        String data = restTemplate.getForObject(Constants.userAccountMgmtUrl + "/account/getAccountForId", String.class);
        return data;
    }


}
