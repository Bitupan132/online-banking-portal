package xyz.team1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import xyz.team1.model.Loan;
import xyz.team1.service.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {
	
	@Autowired
	private LoanService loanService;
	
	@GetMapping("/getAllLoans")
	private List<Loan> getAllLoans() {
		return loanService.getAllLoans();
	}
	
	@PostMapping("/applyForLoan")
	private Loan applyLoan(@RequestBody Loan loan) {
		return loanService.applyForLoan(loan);
	}
	
	@GetMapping("/getLoanById/{loanId}")
	private Loan getLoanById(@PathVariable Long loanId) {
		return loanService.getLoanById(loanId);
	}
	
	
	// complete this end point.
	
	@GetMapping("/findAllByAccountNo/{accountNo}")
	private List<Loan> findAllByAccountNo(@PathVariable String accountNo) {
		return loanService.findAllByAccountNo(accountNo);
	}

}
