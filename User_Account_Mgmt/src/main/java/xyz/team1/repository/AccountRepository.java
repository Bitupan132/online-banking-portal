package xyz.team1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.team1.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    
}
