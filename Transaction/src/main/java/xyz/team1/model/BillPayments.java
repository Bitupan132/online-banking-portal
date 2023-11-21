package xyz.team1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transaction_Table")
public class BillPayments {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    
    private LocalDateTime billDateTime;

    @Column(nullable = false)
    private Double amount;

    private String summary;

    @Column(nullable = false)
    private String senderAccountNo;

    @Column(nullable = false)
    private BillType bill;

    private Double balance;
    
    public Long getBillId() {
		return billId;
	}


	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public LocalDateTime getBillDateTime() {
		return billDateTime;
	}

	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getSenderAccountNo() {
		return senderAccountNo;
	}


	public void setSenderAccountNo(String senderAccountNo) {
		this.senderAccountNo = senderAccountNo;
	}


	public BillType getBill() {
		return bill;
	}


	public void setBill(BillType bill) {
		this.bill = bill;
	}

	@PrePersist
    public void prePersistTransactionDateTime() {
        billDateTime = LocalDateTime.now();
    }


	public void setBillDateTime(LocalDateTime billDateTime) {
		this.billDateTime = billDateTime;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
