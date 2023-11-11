package xyz.team1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.team1.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
