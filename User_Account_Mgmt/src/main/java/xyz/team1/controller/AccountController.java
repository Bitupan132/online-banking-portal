package xyz.team1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.model.Account;
import xyz.team1.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/getAll")
    private List<Account> getAllAccount() {
        return accountService.getAll();
    }

    @PostMapping("/add")
    private Account addAccount(@RequestBody Account account) {
        return accountService.addAccount(account);
    }

    @GetMapping("/getAccountForId")
    private Account getAccountForId() {
        return accountService.getAccountById(Long.valueOf(1));
    }

    @PostMapping("/updateBalanceForTransaction")
    private ResponseEntity<String> updateBalanceForTransaction(@RequestBody Map<String, Object> requestData) {
        Long senderAccountId = Long.valueOf(requestData.get("senderAccountId").toString());
        Long receiverAccountId = Long.valueOf(requestData.get("receiverAccountId").toString());
        Double transferAmount = Double.valueOf(requestData.get("transferAmount").toString());

        try {
            accountService.updateBalanceForTransaction(senderAccountId, receiverAccountId, transferAmount);
            return ResponseEntity.ok("Balance updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating balance. " + e.getLocalizedMessage());
        }
    }
}
