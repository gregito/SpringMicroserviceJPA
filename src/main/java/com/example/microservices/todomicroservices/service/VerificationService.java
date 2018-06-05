package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.utilities.AuthenticationException;
import com.example.microservices.todomicroservices.utilities.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class VerificationService {

    private static final String AUTH_EXCEPT_MESSAGE = "Forbidden";

    private static final String EMAIL_KEY = "email";

    @Autowired
    private JwtUtils jwtUtils;

    public void verifyUser(HttpServletRequest request, String email) {
        Optional<String> jwt = jwtUtils.getJwtFromHttpRequest(request);
        if (jwt.isPresent()) {
            try {
                Map<String, Object> components = jwtUtils.jwt2Map(jwt.get());
                if (components.containsKey(EMAIL_KEY) && Objects.equals(components.get(EMAIL_KEY), email)) {
                    return;
                }
            } catch (UnsupportedEncodingException e) {
                throw new AuthenticationException(AUTH_EXCEPT_MESSAGE, e);
            }
        }
        throw new AuthenticationException(AUTH_EXCEPT_MESSAGE);
    }

}
