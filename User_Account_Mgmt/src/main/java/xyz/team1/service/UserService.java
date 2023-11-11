package xyz.team1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.team1.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // public UserService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }
    
}
