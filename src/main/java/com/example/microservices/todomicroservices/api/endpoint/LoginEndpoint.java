package com.example.microservices.todomicroservices.api.endpoint;

import com.example.microservices.todomicroservices.utilities.JsonResponseBody;
import org.springframework.http.ResponseEntity;

public interface LoginEndpoint {

    ResponseEntity<JsonResponseBody> login(String email, String password);

}
