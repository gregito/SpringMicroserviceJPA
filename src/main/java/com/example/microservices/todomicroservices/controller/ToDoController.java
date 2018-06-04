package com.example.microservices.todomicroservices.controller;

import com.example.microservices.todomicroservices.api.endpoint.ToDoEndpoint;
import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.service.ToDoService;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ToDoController implements ToDoEndpoint {

    @Autowired
    private ToDoService toDoService;

    @Override
    @RequestMapping("/showToDos")
    public ResponseEntity<JsonResponseBody> getToDos(HttpServletRequest request) {
        return toDoService.getToDos(request);
    }

    @Override
    @RequestMapping(value = "/newToDo", method = POST)
    public ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, @Valid ToDo toDo, BindingResult result) {
        return toDoService.add(request, toDo, result);
    }

    @Override
    @RequestMapping("/deleteToDo/{id}")
    public ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Integer id) {
        return toDoService.delete(request, id);
    }

}
