package xyz.team1.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.team1.exceptions.UsernameInvalidException;
import xyz.team1.model.User;
import xyz.team1.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public String addUser(User user) {
        User userDb = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (Objects.isNull(userDb)) {
            userRepository.save(user);
            return "User Registered Successfully";
        }
        return "Username: " + user.getUsername() + " Already Taken";
    }

    public boolean checkUser(String account) {
        User user = userRepository.findByAccount(account).orElse(null);
        if (user == null) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public String getAccountNoForUsername(String username) throws RuntimeException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new UsernameInvalidException("Username Ivalid!");
        }
        return user.getAccount();
    }

}
