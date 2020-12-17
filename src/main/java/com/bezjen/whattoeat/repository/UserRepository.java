package com.bezjen.whattoeat.repository;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsernameIgnoreCase(String username);
	User findByEmailIgnoreCase(String email);
	boolean existsByUsernameIgnoreCase(String username);
	boolean existsByEmailIgnoreCase(String email);
	boolean existsByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
}
