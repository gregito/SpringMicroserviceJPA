package com.example.microservices.todomicroservices.controller.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public interface HelloEndpoint {

    @RequestMapping("/hello")
    @ResponseBody
    String sayHello();

}
