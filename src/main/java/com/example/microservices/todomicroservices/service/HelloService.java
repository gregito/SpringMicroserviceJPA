package com.example.microservices.todomicroservices.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class HelloService {

    public String sayHello() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        int hour = Integer.parseInt(sdf.format(new Date()).split(":")[0]);
        String hiMessage;
        if (hour > 4 && hour < 12) {
            hiMessage = "Good morning!";
        } else if (hour >= 12 && hour < 20) {
            hiMessage = "Good afternoon!";
        } else {
            hiMessage = "Good evening!";
        }
        return hiMessage;
    }

}
