package com.example.microservices.todomicroservices.repository;

import com.example.microservices.todomicroservices.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {

    Optional<User> findUserByEmail(String email);

}
