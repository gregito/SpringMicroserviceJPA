package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.repository.UserDao;
import com.example.microservices.todomicroservices.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@Service
public class LoginService {

    // 30 min
    private static final long TOKEN_LIFESPAN = 1800000L;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<JsonResponseBody> login(String email, String password) {
        Optional<User> user = userDao.findUserByEmail(email);
        if (!user.isPresent() || !encryptionUtils.decrypt(user.get().getPassword()).equals(password)) {
            return ResponseEntity.status(FORBIDDEN).body(new JsonResponseBody(FORBIDDEN.value(), "Invalid username or password!"));
        } else {
            String jwt = createJwt(email, user.get().getName(), new Date());
            return ResponseEntity.status(OK).header("jwt", jwt).body(new JsonResponseBody(OK.value(), "Success"));
        }
    }

    Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) {
        try {
            String jwt = jwtUtils.getJwtFromHttpRequest(request)
                    .orElseThrow(() -> new UserNotLoggedException("User has not logged in! Login first!"));
            return jwtUtils.jwt2Map(jwt);
        } catch (UnsupportedEncodingException e) {
            throw new AccessDeniedException("Unable to verify JWT token!", e);
        }
    }

    private String createJwt(String email, String name, Date date) {
        date.setTime(date.getTime() + TOKEN_LIFESPAN);
        try {
            return jwtUtils.generateJwt(email, name, date);
        } catch (UnsupportedEncodingException e) {
            throw new AccessDeniedException("Invalid token parameters!", e);
        }
    }

}
