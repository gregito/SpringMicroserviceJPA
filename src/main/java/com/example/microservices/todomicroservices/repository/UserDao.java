package com.example.microservices.todomicroservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservices.todomicroservices.entities.User;

public interface UserDao extends JpaRepository<User, String> {

    Optional<User> findUserByEmail(String email);

}
