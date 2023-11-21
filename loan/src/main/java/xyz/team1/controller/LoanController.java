package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Loan;
import xyz.team1.service.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private FeignClientInterface feign;
	
	@GetMapping("/getAllLoans")
	private List<Loan> getAllLoans(@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return loanService.getAllLoans();
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
		
	}
	
	@PostMapping("/applyForLoan")
	private Loan applyLoan(@RequestBody Loan loan,@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return loanService.applyForLoan(loan);
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
		
	}
	
	@GetMapping("/getLoanById/{loanId}")
	private Loan getLoanById(@PathVariable Long loanId,@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return loanService.getLoanById(loanId);
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
		
		
	}
	
	@DeleteMapping("/deleteLoan/{loanId}")
	private void deleteLoan(@PathVariable Long loanId,@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			loanService.delete(loanId);
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
		
		 
	}
	
	
	@GetMapping("/findAllByAccountNo/{accountNo}")
	private List<Loan> findAllByAccountNo(@PathVariable String accountNo,@RequestHeader("Authorization") String authorizationHeader) throws Exception {
		try {
		String jwtToken = authorizationHeader.substring(7);
		String s = feign.validateToken(jwtToken);
		if ("Token is valid".equals(s)) 
		{
			return loanService.findAllByAccountNo(accountNo);
		}
			throw new Exception("Token is not valid");
		}catch(Exception e) {
			throw new Exception("Token is not valid");
		}
		
	}

}
