package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.model.Account;
import xyz.team1.model.User;
import xyz.team1.repository.AccountRepository;
import xyz.team1.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository Repo;

    @GetMapping("/getAll")
    private List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/add")
    private User addUser(@RequestBody User user) throws Exception {
        
        Account existingAccount = Repo.findByAccountNo(user.getAccount()).orElse(null);
        if(existingAccount==null) {
        	throw new Exception("Mentioned Account Doesn't exist");
        }else {
        user.setAccount(existingAccount.getAccountNo());
        userService.addUser(user);
        return user;
        }
    }
    
    @GetMapping("/get/{username}")
    public User findUser(@PathVariable String username) {
		return userService.getUser(username);
    	
    }
}
