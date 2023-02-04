package com.example.demoWeather.repository;

import java.util.Optional;

import com.example.demoWeather.enums.ERole;
import com.example.demoWeather.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(ERole name);
}
