package com.example.microservices.todomicroservices.controller.impl;

import com.example.microservices.todomicroservices.controller.interfaces.ToDoEndpoint;
import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ToDoController implements ToDoEndpoint {

    @Autowired
    private ToDoService toDoService;

    //@Override
    public String getTodoInputById(Integer id) {
        return toDoService.getDescriptionAndPriorityForToDo(id);
    }

    @Override
    public String getRandomToDo() {
        return toDoService.getDescriptionAndPriorityForRandomToDo();
    }

    @Override
    public String toDoInput1(ToDo toDo) {
        return toDoService.toDoInput(toDo);
    }

    @Override
    public String toDoInput2(@Valid ToDo toDo) {
        return toDoService.toDoInput(toDo);
    }

    @Override
    public String toDoInput3(ToDo toDo, BindingResult result) {
        return toDoService.toDoInputWithBindingValidation(toDo, result);
    }


}
