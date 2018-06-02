package com.example.microservices.todomicroservices.controller.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;

public interface HelloEndpoint {

    @RequestMapping("/hello")
    String sayHello();

}
