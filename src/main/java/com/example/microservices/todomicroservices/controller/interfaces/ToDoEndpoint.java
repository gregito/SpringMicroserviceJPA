package com.example.microservices.todomicroservices.controller.interfaces;

import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

public interface ToDoEndpoint {

    //@RequestMapping(value = "/todoInput/get/{id}", method = GET)
    //String getTodoInputById(@PathVariable("id") Integer id);

    @RequestMapping("/todoInput/random")
    String getRandomToDo();

    @RequestMapping(value = "/todoInput1", method = POST)
    String toDoInput1(ToDo toDo);

    @RequestMapping(value = "/todoInput2", method = POST)
    String toDoInput2(ToDo toDo);

    @RequestMapping(value = "/todoInput3", method = POST)
    String toDoInput3(ToDo toDo, BindingResult result);

    @RequestMapping("/showToDos")
    ResponseEntity<JsonResponseBody> getToDos(HttpServletRequest request);

    @RequestMapping(value = "/newToDo", method = POST)
    ResponseEntity<JsonResponseBody> newToDo(HttpServletRequest request, ToDo toDo, BindingResult result);

    @RequestMapping("/deleteToDo/{id}")
    ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name = "id") Integer id);

}
