package xyz.team1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.team1.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
    
}
