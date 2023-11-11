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
import jakarta.persistence.Table;

@Entity
@Table(name = "Account_Table")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true, length = 12)
    private String accountNo;

    @Column(nullable = false)
    private Double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
    
    private LocalDateTime accountCreationDateTime;

    // We may need to store account holder details in another table.
    @Column(nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false , length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private Long phoneNo;

    @Column(nullable = false)
    private String address;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getAccountCreationDate() {
        return accountCreationDateTime;
    }

    public void setAccountCreationDate(LocalDateTime accountCreationDateTime) {
        this.accountCreationDateTime = accountCreationDateTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }  

    @PrePersist
    public void prePersistAccountCreationDateTime() {
        // Set the account creation date to the current system date and time
        accountCreationDateTime = LocalDateTime.now();
    }
    
}
