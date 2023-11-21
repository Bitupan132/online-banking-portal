package xyz.team1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.team1.model.Loan;
import xyz.team1.repository.LoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepository;
	
	
	public Loan applyForLoan(Loan loan) {
		return loanRepository.save(loan);
		
	}
	
	
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
	}
	
	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId).orElse(null);
	}
	
	
	// to get loan for a single account number
	public List<Loan> findAllByAccountNo(String accountNo) {
		return loanRepository.findAllByAccountNo(accountNo);
	}


	public void delete(Long loanId) {
		loanRepository.deleteById(loanId);
	}

}
