package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Transaction;
import xyz.team1.service.TransactionService;

@RestController
@RequestMapping("/transaction")
@CrossOrigin("*")
public class TransactionController {
	
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private FeignClientInterface feign;
    

	@GetMapping("/getAll")
    private List<Transaction> getAllTransaction(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return transactionService.getAllTransaction();
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
        
    }
	
	@GetMapping("/getAll/{accountNo}")
    private List<Transaction> getAccountTransaction(@PathVariable String accountNo,@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return transactionService.getTransactionForAccount(accountNo);
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
        
    }

    @PostMapping(value = "/add")
    private ResponseEntity<String> addTransaction(@RequestBody Transaction transaction,@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return transactionService.addTransaction(transaction,jwtToken);
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
        
        
    }
}
