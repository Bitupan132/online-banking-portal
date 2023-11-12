package xyz.team1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.team1.model.Transaction;
import xyz.team1.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction addTransaction(Transaction transaction) {
        //handle balance changes in the sender and receiver account.
        //and also what if save fails and you update the balance changes.
        return transactionRepository.save(transaction);
    }


}
