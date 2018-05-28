package com.example.microservices.todomicroservices.controller.impl;

import com.example.microservices.todomicroservices.controller.interfaces.HelloEndpoint;
import com.example.microservices.todomicroservices.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController implements HelloEndpoint {

    @Autowired
    private HelloService helloService;

    public String sayHello() {
        return helloService.sayHello();
    }

}
