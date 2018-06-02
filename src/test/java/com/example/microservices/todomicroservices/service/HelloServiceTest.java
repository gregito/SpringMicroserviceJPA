package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.repository.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServiceTest {

    @InjectMocks
    private HelloService underTest;

    @Mock
    private UserDao userDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHiMessage() {
        int hour = Integer.parseInt(new SimpleDateFormat("HH:mm:ss").format(new Date()).split(":")[0]);
        String expected;
        if (hour > 4 && hour < 12) {
            expected = "Good morning!";
        } else if (hour >= 12 && hour < 20) {
            expected = "Good afternoon!";
        } else {
            expected = "Good evening!";
        }

        Assert.assertEquals(expected, underTest.sayHello());
    }

}