package com.example.microservices.todomicroservices.controller;

import com.example.microservices.todomicroservices.api.endpoint.LoginEndpoint;
import com.example.microservices.todomicroservices.service.LoginService;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginEndpoint {

    @Autowired
    private LoginService loginService;

    @Override
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JsonResponseBody> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return loginService.login(email, password);
    }

}
