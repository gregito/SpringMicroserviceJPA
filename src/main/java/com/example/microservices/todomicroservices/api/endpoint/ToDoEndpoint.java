package com.example.microservices.todomicroservices.api.endpoint;

import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;

public interface ToDoEndpoint {

    ResponseEntity<JsonResponseBody> getToDos(HttpServletRequest request);

    ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, ToDo toDo, BindingResult result);

    ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, Integer id);

}
