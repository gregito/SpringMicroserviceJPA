package com.example.microservices.todomicroservices.api.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;

public interface HelloEndpoint {

    @RequestMapping("/hello")
    String sayHello();

}
