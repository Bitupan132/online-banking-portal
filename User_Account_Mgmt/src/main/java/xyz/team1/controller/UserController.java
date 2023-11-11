package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.model.Account;
import xyz.team1.model.User;
import xyz.team1.service.AccountService;
import xyz.team1.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    // We don't need getAll (also get all returns password as plain text)
    @GetMapping("/getAll")
    private List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/add")
    private ResponseEntity<Object> addUser(@RequestBody User user) {
        // Should we return saved User or return a status message in the body 
        // or not return anything.
        Account existingAccount = accountService.getAccountById(user.getAccount().getAccountId());
        user.setAccount(existingAccount);
        userService.addUser(user);
        return ResponseEntity.ok("User Saved");
    }
}
