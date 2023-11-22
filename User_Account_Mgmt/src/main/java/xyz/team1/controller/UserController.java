package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.constants.Constants;
import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Account;
import xyz.team1.model.User;
import xyz.team1.repository.AccountRepository;
import xyz.team1.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private AccountRepository Repo;

	@Autowired
	private FeignClientInterface feign;

	@GetMapping("/getAll")
	private List<User> getAllUser(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
			String jwtToken = authorizationHeader.substring(7);
			String s = feign.validateToken(jwtToken);
			if ("Token is valid".equals(s)) {
				return userService.getAllUser();
			}
			throw new Exception(Constants.tokenInvalidString);
		}catch(Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}
	}

	@PostMapping("/add")
	private String addUser(@RequestBody User user) {

		Account existingAccount = Repo.findByAccountNo(user.getAccount()).orElse(null);
		if (existingAccount == null) {
			return "Account Doesn't exist";
		} else {
			if (userService.checkUser(existingAccount.getAccountNo())) {
				user.setAccount(existingAccount.getAccountNo());
				return userService.addUser(user);
				
			} else {
				return "User already exists for account no: "+existingAccount.getAccountNo();
			}
		}
	}

	@GetMapping("/getUser/{username}")
	public User findUser(@PathVariable String username) {
		return userService.getUser(username);

	}
}
