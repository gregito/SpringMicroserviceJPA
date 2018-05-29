package com.example.microservices.todomicroservices.controller.interfaces;

import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface LoginEndpoint {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<JsonResponseBody> login(@RequestParam("email") String email, @RequestParam("password") String password);

}
