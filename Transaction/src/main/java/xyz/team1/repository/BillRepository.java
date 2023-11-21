package xyz.team1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.team1.model.BillPayments;
import java.util.List;


public interface BillRepository extends JpaRepository<BillPayments, Long>{
	
	List<BillPayments> findBySenderAccountNo(String senderAccountNo);

}
