package com.example.microservices.todomicroservices.utilities;

public class AccessDeniedException extends AuthenticationException {

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
