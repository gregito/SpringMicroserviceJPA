package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.ToDo;
import com.example.microservices.todomicroservices.repository.ToDoDao;
import com.example.microservices.todomicroservices.utilities.AuthenticationException;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import com.example.microservices.todomicroservices.utilities.ToDoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class ToDoService {

    @Autowired
    private ToDoDao toDoDao;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ToDoValidator toDoValidator;

    @Autowired
    private VerificationService verificationService;

    public ResponseEntity<JsonResponseBody> getToDos(HttpServletRequest request) {
        try {
            Map<String, Object> userData = loginService.verifyJwtAndGetData(request);
            return ResponseEntity.ok(new JsonResponseBody(OK.value(), toDoDao.findByUser(String.class.cast(userData.get("email")))));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(FORBIDDEN).body(new JsonResponseBody(FORBIDDEN.value(), e.getMessage()));
        }
    }

    public ResponseEntity<JsonResponseBody> add(HttpServletRequest request, ToDo toDo, BindingResult result) {
        toDoValidator.validate(toDo, result);
        if (!result.hasErrors()) {
            try {
                loginService.verifyJwtAndGetData(request);
                return ResponseEntity.ok(new JsonResponseBody(OK.value(), toDoDao.save(toDo)));
            } catch (AuthenticationException e) {
                return ResponseEntity.status(FORBIDDEN).body(new JsonResponseBody(FORBIDDEN.value(), e.getMessage()));
            }
        } else {
            return ResponseEntity.status(BAD_REQUEST).body(new JsonResponseBody(BAD_REQUEST.value(), String.format("Data is not valid: %s", result.toString())));
        }
    }

    public ResponseEntity<JsonResponseBody> delete(HttpServletRequest request, Integer id) {
        Optional<ToDo> toDo = toDoDao.findById(id);
        if (!toDo.isPresent()) {
            return ResponseEntity.status(FORBIDDEN).body(new JsonResponseBody(FORBIDDEN.value(), "Forbidden"));
        }
        try {
            verificationService.verifyUser(request, toDo.get().getUser());
            loginService.verifyJwtAndGetData(request);
            toDoDao.deleteById(id);
            return ResponseEntity.ok(new JsonResponseBody(OK.value(), "Success"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(FORBIDDEN).body(new JsonResponseBody(FORBIDDEN.value(), e.getMessage()));
        }
    }

}
