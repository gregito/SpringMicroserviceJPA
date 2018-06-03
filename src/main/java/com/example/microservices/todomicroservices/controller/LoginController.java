package com.example.microservices.todomicroservices.controller;

import com.example.microservices.todomicroservices.api.endpoint.LoginEndpoint;
import com.example.microservices.todomicroservices.service.LoginService;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginEndpoint {

    @Autowired
    private LoginService loginService;

    @Override
    public ResponseEntity<JsonResponseBody> login(String email, String password) {
        return loginService.login(email, password);
    }

}
