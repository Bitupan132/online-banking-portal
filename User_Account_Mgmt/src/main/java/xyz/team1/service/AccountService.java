package xyz.team1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.team1.constants.Constants;
import xyz.team1.model.Account;
import xyz.team1.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public String updateBalanceForAccountId(Account account) {
        Optional<Account> optionalAccountFromDb = accountRepository.findById(account.getAccountId());
        if(optionalAccountFromDb.isEmpty()){
            return Constants.failureMessage;
        }
        Account accountFromDb = optionalAccountFromDb.get();
        accountFromDb.setBalance(account.getBalance());
        accountRepository.save(accountFromDb);
        return Constants.successMessage;
    }

    public String updateBalanceForAccountId(Long accountId, Double transferAmmount, boolean isAddition) {
        Optional<Account> optionalAccountFromDb = accountRepository.findById(accountId);
        if(optionalAccountFromDb.isEmpty()){
            return Constants.failureMessage;
        }
        Account accountFromDb = optionalAccountFromDb.get();
        if (isAddition) {
            accountFromDb.setBalance(accountFromDb.getBalance() + transferAmmount);
        } else {
            Double finalBalance = accountFromDb.getBalance() - transferAmmount;
            if(finalBalance<0){
                return Constants.failureMessage;
            }
            accountFromDb.setBalance(finalBalance);
        }
        accountRepository.save(accountFromDb);
        return Constants.successMessage;
    }


}
