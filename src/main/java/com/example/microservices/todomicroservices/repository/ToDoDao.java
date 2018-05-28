package com.example.microservices.todomicroservices.repository;

import com.example.microservices.todomicroservices.entities.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoDao extends JpaRepository<ToDo, Integer> {

    List<ToDo> findByUser(String email);

}
