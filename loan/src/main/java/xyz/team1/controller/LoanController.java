package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.team1.constants.Constants;
import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Loan;
import xyz.team1.service.LoanService;

@RestController
@RequestMapping("/loan")
@CrossOrigin("*")
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
			if (Constants.tokenValidString.equals(s)) {
				return loanService.getAllLoans();
			}
			throw new Exception(Constants.tokenInvalidString);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}

	}

	@PostMapping("/applyForLoan")
	private Object applyLoan(@RequestBody Loan loan, 
			@RequestHeader("Authorization") String authorizationHeader)
	{
		if (loanService.validateToken(authorizationHeader)) {
			return loanService.applyForLoan(loan);
		}
		return Constants.tokenInvalidString;
	}

	@GetMapping("/getLoanById/{loanId}")
	private Loan getLoanById(@PathVariable Long loanId, @RequestHeader("Authorization") String authorizationHeader)
			throws Exception {
		try {
			String jwtToken = authorizationHeader.substring(7);
			String s = feign.validateToken(jwtToken);
			if (Constants.tokenValidString.equals(s)) {
				return loanService.getLoanById(loanId);
			}
			throw new Exception(Constants.tokenInvalidString);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage());
		}

	}

	@DeleteMapping("/deleteLoan/{loanId}")
	private Object deleteLoan(@PathVariable Long loanId, 
			@RequestHeader("Authorization") String authorizationHeader)
	{
		if (loanService.validateToken(authorizationHeader)) {
			return loanService.delete(loanId);
		}
		return Constants.tokenInvalidString;
	}

	@GetMapping("/getAllByAccountNo/{accountNo}")
	private Object findAllByAccountNo(@PathVariable String accountNo,
			@RequestHeader("Authorization") String authorizationHeader){
		if (loanService.validateToken(authorizationHeader)) {
			return loanService.findAllByAccountNo(accountNo);
		}
		return Constants.tokenInvalidString;
	}

}
