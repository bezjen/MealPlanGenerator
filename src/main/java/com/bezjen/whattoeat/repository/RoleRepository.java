package com.bezjen.whattoeat.repository;

import org.springframework.data.repository.CrudRepository;

import com.bezjen.whattoeat.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String name);
}
