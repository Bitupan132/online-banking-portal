package xyz.team1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Loan_Table")
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_sequence")
    @SequenceGenerator(name = "loan_sequence", sequenceName = "loan_sequence", allocationSize = 1, initialValue = (int) 100000000000L)
	private Long loanId;
	
	// @Column(nullable = false, unique = true, length = 12)
	// private String loanAccountNo;
	
	@Column(nullable = false)
	private Double loanAmount;
	
	@Column(nullable = false)
	private String accountNo;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanType loanType;

	@Column(nullable = false)
	private Double interestRate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LoanStatus loanStatus;
	
	private LocalDateTime loanApplicationDateTime;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	// public String getLoanAccountNo() {
	// 	return loanAccountNo;
	// }

	// public void setLoanAccountNo(String loanAccountNo) {
	// 	this.loanAccountNo = loanAccountNo;
	// }

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	public LocalDateTime getLoanApplicationDateTime() {
		return loanApplicationDateTime;
	}

	public void setLoanApplicationDateTime(LocalDateTime loanApplicationDateTime) {
		this.loanApplicationDateTime = loanApplicationDateTime;
	}
	
	
	@PrePersist
	public void PrePersistAccountCreationDateTime() {
		loanApplicationDateTime = LocalDateTime.now();
		interestRate = 10.0;
		loanStatus=LoanStatus.PENDING;
	}
}
