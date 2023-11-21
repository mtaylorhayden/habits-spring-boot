package com.goalsapi.goalsapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.goalsapi.goalsapi.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}