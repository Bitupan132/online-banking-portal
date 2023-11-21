package xyz.team1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.team1.model.Transaction;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySenderAccountNo(String senderAccountNo);
    List<Transaction> findByReceiverAccountNo(String receiverAccountNo);
}
