package xyz.team1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xyz.team1.model.BillPayments;
import xyz.team1.repository.BillRepository;

@Service
public class BillService {
	
	@Autowired
	private BillRepository billRepository;
	
	public List<BillPayments> getAccountBills(String AccountNo){
		return billRepository.findBySenderAccountNo(AccountNo);
	}
	
	@Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> addBill(BillPayments bill,String token) {
        try {
            Double balance = updateBalanceForBill(bill.getSenderAccountNo(),
                    bill.getAmount(),token);
            LocalDateTime time = LocalDateTime.now();
            bill.setBillDateTime(time);
            bill.setBalance(balance);
            billRepository.save(bill);
            return ResponseEntity.ok("Bill successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bill Failed! " + e.getMessage());
        }
    }

	private Double updateBalanceForBill(String senderAccountNo, Double amount, String token) {
		
		return null;
	} 
}
