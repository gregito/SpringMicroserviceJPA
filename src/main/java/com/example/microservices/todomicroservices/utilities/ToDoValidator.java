package com.example.microservices.todomicroservices.utilities;

import com.example.microservices.todomicroservices.entities.ToDo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.example.microservices.todomicroservices.utilities.Priority.HIGH;
import static com.example.microservices.todomicroservices.utilities.Priority.LOW;

public class ToDoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ToDo toDo = ToDo.class.cast(o);
        if (!HIGH.getLevel().equalsIgnoreCase(toDo.getPriority()) && !LOW.getLevel().equalsIgnoreCase(toDo.getPriority())) {
            errors.rejectValue("priority", "Priority must be 'high' or 'low'");
        }
    }

}
