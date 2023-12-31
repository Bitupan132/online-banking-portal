package com.hdfc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.hdfc.constants.Constants;
import com.hdfc.dto.User;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = restTemplate.getForObject(Constants.USER_URL_MS+"/getUser/" + username, User.class);
		if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
		// System.out.println("Me");
		return new CustomUserDetails(user);
	}
	
	
	
}
