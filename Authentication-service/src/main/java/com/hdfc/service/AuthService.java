package com.hdfc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.hdfc.constants.Constants;
import com.hdfc.dto.User;

@Component
public class AuthService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JwtService jwtService;

	@Autowired 
	private PasswordEncoder passwordEncoder;

	public String saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return restTemplate.postForObject(Constants.USER_URL_MS + "/add", user, String.class);
	}

	public String generateToken(String username) {
		return jwtService.generateToken(username);
	}

	public void validateToken(String token) {
		jwtService.validateToken(token);
	}
}
