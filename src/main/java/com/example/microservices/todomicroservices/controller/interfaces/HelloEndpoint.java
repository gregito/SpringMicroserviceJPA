package com.example.microservices.todomicroservices.controller.interfaces;

import com.example.microservices.todomicroservices.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;

public interface HelloEndpoint {

    @RequestMapping("/hello")
    String sayHello();

    @RequestMapping("/userInOutput")
    User giveMeAUser();

}
