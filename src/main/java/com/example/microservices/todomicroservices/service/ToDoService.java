package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.repository.ToDoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ToDoService {

    @Autowired
    private ToDoDao toDoDao;

    public String getDescriptionAndPriorityForToDo(Integer id) {
        String toRetrun;
        Optional<ToDo> toDo = toDoDao.findById(id);
        return toDo.isPresent()
                ? String.format("Description: %s, Priority: %s", toDo.get().getDescription(), toDo.get().getPriority())
                : "Access Denied!";
    }

    public String getDescriptionAndPriorityForRandomToDo() {
        List<ToDo> toDos = toDoDao.findAll();
        if (toDos.isEmpty()) {
            return null;
        } else {
            ToDo toDo = toDos.get(ThreadLocalRandom.current().nextInt(toDos.size()) % toDos.size());
            return String.format("Description: %s, Priority: %s", toDo.getDescription(), toDo.getPriority());
        }
    }

    public String toDoInput1(ToDo toDo) {
        return String.format("Description: %s, Priority: %s", toDo.getDescription(), toDo.getPriority());
    }

}
