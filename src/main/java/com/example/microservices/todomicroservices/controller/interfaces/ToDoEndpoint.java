package com.example.microservices.todomicroservices.controller.interfaces;

import com.example.microservices.todomicroservices.entities.ToDo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
