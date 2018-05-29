package com.example.microservices.todomicroservices.utilities;

public class UserNotLoggedException extends AuthenticationException {

    public UserNotLoggedException(String message) {
        super(message);
    }

}
