package com.example.microservices.todomicroservices.controller.impl;

import com.example.microservices.todomicroservices.controller.interfaces.HelloEndpoint;
import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloEndpoint {

    @Autowired
    private HelloService helloService;

    public String sayHello() {
        return helloService.sayHello();
    }

    @Override
    public User giveMeAUser() {
        return helloService.getRandomUser();
    }

}
