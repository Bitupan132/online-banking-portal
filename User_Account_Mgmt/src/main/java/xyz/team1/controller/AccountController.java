package xyz.team1.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.constants.Constants;
import xyz.team1.model.Account;
import xyz.team1.service.AccountService;

@RestController
@RequestMapping("/account")
@CrossOrigin("*")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/getAll")
	private Object getAllAccount(@RequestHeader("Authorization") String authorizationHeader) {
		if (accountService.validateToken(authorizationHeader)) {
			return accountService.getAll();
		}
		return Constants.tokenInvalidString;
	}

	@PostMapping("/add")
	private Object addAccount(@RequestBody Account account,
			@RequestHeader("Authorization") String authorizationHeader) {
		if (accountService.validateToken(authorizationHeader)) {
			return accountService.addAccount(account);
		}
		return Constants.tokenInvalidString;
	}

	@GetMapping("/getAccountForUsername/{username}")
	private Object getAccountForUsername(@PathVariable String username,
			@RequestHeader("Authorization") String authorizationHeader) throws RuntimeException {
		System.out.println("Hit getAccountForUsername");
		if (accountService.validateToken(authorizationHeader)) {
			Account account = accountService.getAccountForUsername(username);
			if (Objects.isNull(account)) {
				return "ACCOUNT_DOES_NOT_EXIST";
			}
			return account;
		}
		return Constants.tokenInvalidString;
	}

	@PostMapping("/updateBalanceForTransaction")
	private ResponseEntity<String> updateBalanceForTransaction(@RequestBody Map<String, Object> requestData,
			@RequestHeader("Authorization") String authorizationHeader)
			throws Exception {
		if (accountService.validateToken(authorizationHeader)) {
			String senderAccountNo = requestData.get("senderAccountNo").toString();
			String receiverAccountNo = requestData.get("receiverAccountNo").toString();
			Double transferAmount = Double.valueOf(requestData.get("transferAmount").toString());

			try {
				accountService.updateBalanceForTransaction(senderAccountNo, receiverAccountNo, transferAmount);
				return ResponseEntity.ok("Balance updated successfully!");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(e.getLocalizedMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Constants.tokenInvalidString);
	}
}
