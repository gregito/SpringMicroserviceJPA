package com.example.microservices.todomicroservices.controller;

import com.example.microservices.todomicroservices.api.endpoint.ToDoEndpoint;
import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.service.ToDoService;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ToDoController implements ToDoEndpoint {

    @Autowired
    private ToDoService toDoService;

    @Override
    public ResponseEntity<JsonResponseBody> getToDos(HttpServletRequest request) {
        return toDoService.getToDos(request);
    }

    @Override
    public ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, @Valid ToDo toDo, BindingResult result) {
        return toDoService.add(request, toDo, result);
    }

    @Override
    public ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, Integer id) {
        return toDoService.delete(request, id);
    }

}
