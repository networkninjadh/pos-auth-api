package com.howtech.posauthapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.howtech.posauthapi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsUserByUsername(String username);

	void deleteByUsername(String username);
}