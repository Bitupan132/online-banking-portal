package xyz.team1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.team1.constants.Constants;
import xyz.team1.exceptions.AccountNotFoundException;
import xyz.team1.exceptions.InsufficientBalanceException;
import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Account;
import xyz.team1.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
	private FeignClientInterface feign;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
    
    public void updateBalanceForTransaction(String senderAccountNo, String receiverAccountNo, Double transferAmount) throws RuntimeException{
        Optional<Account> receiverAccountOptional = accountRepository.findByAccountNo(receiverAccountNo);
        Optional<Account> senderAccountOptional = accountRepository.findByAccountNo(senderAccountNo);

        if(receiverAccountOptional.isEmpty()){
            throw new AccountNotFoundException("Incorrect Account no. Account not found for account no: " + receiverAccountNo);
        }
        if(senderAccountOptional.isEmpty()){
            throw new AccountNotFoundException("Incorrect Account no. Account not found for account no: " + senderAccountNo);
        }

        Account receiverAccount = receiverAccountOptional.get();
        Account senderAccount = senderAccountOptional.get();

        Double senderFinalBalance = senderAccount.getBalance() - transferAmount;

        if(senderFinalBalance<0){
            throw new InsufficientBalanceException(
                        "Insufficient balance for account no: " + senderAccount.getAccountNo());
        }

        receiverAccount.setBalance(receiverAccount.getBalance()+transferAmount);
        senderAccount.setBalance(senderFinalBalance);

        saveSenderAndReceiverAccounts(senderAccount,receiverAccount);
    }

    @Transactional
    public void saveSenderAndReceiverAccounts(Account senderAccount, Account receiverAccount) throws RuntimeException{
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
    }

    public Account getAccountForUsername(String username) throws RuntimeException{
        String accountNo = userService.getAccountNoForUsername(username);
        return accountRepository.findByAccountNo(accountNo).orElse(null);
    }
    
    public boolean validateToken(String authorizationHeader) {
		String jwtToken = authorizationHeader.substring(7);
		String validationStatus = feign.validateToken(jwtToken);
		if(Constants.tokenValidString.equals(validationStatus)){
			return true;
		}
		return false;
	}
}
