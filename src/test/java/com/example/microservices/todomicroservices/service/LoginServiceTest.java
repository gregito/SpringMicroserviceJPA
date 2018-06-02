package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.repository.UserDao;
import com.example.microservices.todomicroservices.utilities.EncryptionUtils;
import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import com.example.microservices.todomicroservices.utilities.JwtUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    private static final String TEST_JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb21lLmVtYWlsQHByb3ZpZGVyLmNvbSIsImV4cCI6M"
            + "TUyNzgwMzE5NCwibmFtZSI6IlN0ZXZlIEJyb25uZXIifQ.WFBwfLDW-ETOmMUhqILlB743PjLKfXxOkM0SZWOKQNg";

    private static final String TEST_EMAIL = "some@email.com";

    private static final String TEST_NAME = "Lindy";

    private static final String INVALID_PASSWORD_MESSAGE = "Invalid username or password!";

    @InjectMocks
    private LoginService underTest;

    @Mock
    private UserDao userDao;

    @Mock
    private EncryptionUtils encryptionUtils;

    @Mock
    private JwtUtils jwtUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginWhenUserExistsInDatabaseButItsPasswordDoesNotRightThenForbiddenResponseShouldCome() {
        String userEncryptedValue = "1231241";
        String someOtherEncryptedValue = "jgaifdgiv";
        User user = new User(TEST_EMAIL, TEST_NAME, userEncryptedValue);
        when(userDao.findUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
        when(encryptionUtils.decrypt(userEncryptedValue)).thenReturn("something completely different");

        ResponseEntity<JsonResponseBody> result = underTest.login(TEST_EMAIL, someOtherEncryptedValue);

        assertTrue(result.getHeaders().isEmpty());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals(INVALID_PASSWORD_MESSAGE, result.getBody().getResponse().toString());
        verify(userDao, times(1)).findUserByEmail(TEST_EMAIL);
        verify(encryptionUtils, times(1)).decrypt(userEncryptedValue);
    }

    @Test
    public void testLoginWhenUserExistsInDatabaseAndItsPasswordMatchesThenOkResponseShouldCome() throws UnsupportedEncodingException {
        String userPassword = "1231241";
        String userEncryptedValue = "1231241";
        User user = new User(TEST_EMAIL, TEST_NAME, userEncryptedValue);
        when(userDao.findUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));
        when(encryptionUtils.decrypt(userPassword)).thenReturn(userPassword);
        when(jwtUtils.generateJwt(TEST_EMAIL, TEST_NAME, new Date())).thenReturn(TEST_JWT);

        ResponseEntity<JsonResponseBody> result = underTest.login(TEST_EMAIL, userPassword);

        assertFalse(result.getHeaders().isEmpty());
        assertEquals(1, result.getHeaders().size());
        assertTrue(result.getHeaders().containsKey("jwt"));
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Success", result.getBody().getResponse().toString());
        verify(userDao, times(1)).findUserByEmail(TEST_EMAIL);
        verify(encryptionUtils, times(1)).decrypt(userPassword);
    }

    @Test
    public void testLoginWhenUserNotExistsInDatabaseAndItsPasswordDoesNotRightThenForbiddenResponseShouldCome() {
        String userEncryptedValue = "1231241";
        when(userDao.findUserByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        ResponseEntity<JsonResponseBody> result = underTest.login(TEST_EMAIL, "something completely different");

        assertTrue(result.getHeaders().isEmpty());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals(INVALID_PASSWORD_MESSAGE, result.getBody().getResponse().toString());
        verify(userDao, times(1)).findUserByEmail(TEST_EMAIL);
        verify(encryptionUtils, times(0)).decrypt(userEncryptedValue);
    }

}