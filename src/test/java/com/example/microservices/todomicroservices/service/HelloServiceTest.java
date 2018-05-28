package com.example.microservices.todomicroservices.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServiceTest {

    private HelloService underTest;

    @Before
    public void setUp() {
        underTest = new HelloService();
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