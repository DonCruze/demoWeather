package com.example.demoWeather.repository;

import java.util.List;
import java.util.Optional;

import com.example.demoWeather.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findAllByDeletedFalseAndId(Long id);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	List<User> findAllByDeletedFalse();
}
