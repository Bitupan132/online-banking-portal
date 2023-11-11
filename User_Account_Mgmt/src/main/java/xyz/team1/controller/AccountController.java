package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.model.Account;
import xyz.team1.service.AccountService;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    // public AccountController(AccountService accountService) {
    //     this.accountService = accountService;
    // }

    @GetMapping("/accounts/getAll")
    private List<Account> getAllAccount(){
        return accountService.getAll();
    }
    @PostMapping("/accounts/add")
    private Account addAccount(@RequestBody Account account){
        return accountService.addAccount(account);
    }
}
