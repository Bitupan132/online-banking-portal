package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.model.User;
import xyz.team1.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    private List<User> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/add")
    private User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
