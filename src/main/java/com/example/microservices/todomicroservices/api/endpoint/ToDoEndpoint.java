package com.example.microservices.todomicroservices.api.endpoint;

import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

public interface ToDoEndpoint {

    @RequestMapping("/showToDos")
    ResponseEntity<JsonResponseBody> getToDos(HttpServletRequest request);

    @RequestMapping(value = "/newToDo", method = POST)
    ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, ToDo toDo, BindingResult result);

    @RequestMapping("/deleteToDo/{id}")
    ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Integer id);

}
