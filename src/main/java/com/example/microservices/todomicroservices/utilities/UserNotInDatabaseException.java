package com.example.microservices.todomicroservices.utilities;

public class UserNotInDatabaseException extends RuntimeException {

    public UserNotInDatabaseException(String message) {
        super(message);
    }

}
