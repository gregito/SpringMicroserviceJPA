package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class HelloService {

    @Autowired
    private UserDao userDao;

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

    public User getRandomUser() {
        List<User> users = userDao.findAll();
        return users.isEmpty() ? null : users.get(ThreadLocalRandom.current().nextInt(users.size()) % users.size());
    }

}
