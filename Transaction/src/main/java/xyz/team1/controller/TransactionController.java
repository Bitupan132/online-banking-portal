package xyz.team1.controller;

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
import xyz.team1.model.Transaction;
import xyz.team1.service.TransactionService;

@RestController
@RequestMapping("/transaction")
@CrossOrigin("*")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/getAll")
	private Object getAllTransaction(@RequestHeader("Authorization") String authorizationHeader) {
		if (transactionService.validateToken(authorizationHeader)) {
			return transactionService.getAllTransaction();
		}
		return Constants.tokenInvalidString;
	}

	@GetMapping("/getAll/{accountNo}")
	private Object getAccountTransaction(@PathVariable String accountNo,
			@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		if (transactionService.validateToken(authorizationHeader)) {
			return transactionService.getTransactionForAccount(accountNo);
		}
		return Constants.tokenInvalidString;
	}

	@PostMapping(value = "/add")
	private String addTransaction(@RequestBody Transaction transaction,
			@RequestHeader("Authorization") String authorizationHeader) throws RuntimeException{
		if (transactionService.validateToken(authorizationHeader)) {
			return transactionService.addTransaction(transaction, authorizationHeader.substring(7));
		}
		return Constants.tokenInvalidString;

	}
}
