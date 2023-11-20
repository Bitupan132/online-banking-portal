package xyz.team1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.team1.model.User;



@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByAccount(String account);
}
