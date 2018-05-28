package com.example.microservices.todomicroservices.utilities;

public class UserNotLoggedException extends RuntimeException {

    public UserNotLoggedException(String message) {
        super(message);
    }
}
