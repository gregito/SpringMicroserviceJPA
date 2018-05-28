package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.repository.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

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

    @Test
    public void testWhenGetRandomUserCalledThenItShouldReturnSomethingFromTheDatabase() {
        User expectedUser = new User("user1email@address.com", "first user name", "60D33EBBF3BC83A27814AF22E5B0604088A635E5DA0995F749289BF11D0EDCEA");
        List<User> usersFromDatabase = new ArrayList<>();
        usersFromDatabase.add(expectedUser);

        when(userDao.findAll()).thenReturn(usersFromDatabase);

        User resultUser = underTest.getRandomUser();

        Assert.assertEquals(expectedUser, resultUser);
        verify(userDao, times(1)).findAll();
    }

    @Test
    public void testWhenGetRandomUserCalledAndEmptyListReturnsFromDatabaseThenNullShouldReturn() {
        when(userDao.findAll()).thenReturn(Collections.emptyList());

        User resultUser = underTest.getRandomUser();

        Assert.assertNull(resultUser);
        verify(userDao, times(1)).findAll();
    }

}