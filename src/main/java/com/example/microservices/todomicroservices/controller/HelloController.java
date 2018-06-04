package com.example.microservices.todomicroservices.controller;

import com.example.microservices.todomicroservices.api.endpoint.HelloEndpoint;
import com.example.microservices.todomicroservices.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloEndpoint {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String sayHello() {
        return helloService.sayHello();
    }

}
