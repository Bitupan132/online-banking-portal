package xyz.team1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.team1.constants.Constants;
import xyz.team1.interceptor.FeignClientInterface;
import xyz.team1.model.Loan;
import xyz.team1.repository.LoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private FeignClientInterface feign;
	
	
	public Loan applyForLoan(Loan loan) {
		return loanRepository.save(loan);
		
	}
	
	
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
	}
	
	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId).orElse(null);
	}
	
	public List<Loan> findAllByAccountNo(String accountNo) {
		return loanRepository.findAllByAccountNo(accountNo);
	}

	public Object delete(Long loanId) {
		loanRepository.deleteById(loanId);
		return "Loan Application: "+ loanId + "Deleted.";
	}
	
	public boolean validateToken(String authorizationHeader) {
		String jwtToken = authorizationHeader.substring(7);
		String validationStatus = feign.validateToken(jwtToken);
		if(Constants.tokenValidString.equals(validationStatus)){
			return true;
		}
		return false;
	}

}
