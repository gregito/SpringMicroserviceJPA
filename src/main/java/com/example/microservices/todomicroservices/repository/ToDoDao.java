package com.example.microservices.todomicroservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.microservices.todomicroservices.entities.ToDo;

public interface ToDoDao extends JpaRepository<ToDo, Integer> {

    List<ToDo> findByUser(String email);

}
