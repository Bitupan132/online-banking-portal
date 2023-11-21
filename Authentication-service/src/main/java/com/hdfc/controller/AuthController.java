package com.hdfc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.dto.AuthRequest;
import com.hdfc.dto.User;
import com.hdfc.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;
	

	
	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		return authService.saveUser(user);
	}
	
	@PostMapping("/token")
	public String getToken(@RequestBody AuthRequest authRequest) throws Exception {
		Authentication authenticate;
		try {
			authenticate = authenticationManager.
				authenticate(new UsernamePasswordAuthenticationToken(
						authRequest.getUsername(), authRequest.getPassword()));
			System.out.println(authenticate.isAuthenticated());
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			// return e.getLocalizedMessage();
			throw new Exception(e.getLocalizedMessage());
		}
		
		if(authenticate.isAuthenticated()) {
			return authService.generateToken(authRequest.getUsername());
		}else {
			throw new RuntimeException("invalid access!");
		}
		
	}
	
	@GetMapping("/validate")
	public String validateToken(@RequestParam String token) {
		 try {
			authService.validateToken(token);
			 return "Token is valid";
		} catch (Exception e) {
			e.printStackTrace();
			return "Token is not validated due to "+e.getMessage();
		}
		
	}
	
	
}
