package com.example.microservices.todomicroservices.service;

import com.example.microservices.todomicroservices.entities.User;
import com.example.microservices.todomicroservices.repository.UserDao;
import com.example.microservices.todomicroservices.utilities.AccessDeniedException;
import com.example.microservices.todomicroservices.utilities.EncryptionUtils;
import com.example.microservices.todomicroservices.utilities.JwtUtils;
import com.example.microservices.todomicroservices.utilities.UserNotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private static final long TOKEN_LIFESPAN = 300000L;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Autowired
    private JwtUtils jwtUtils;

    public User getUser(String email, String pwd) {
        Optional<User> user = userDao.findUserByEmail(email);
        if (!user.isPresent() || !encryptionUtils.encrypt(pwd).equals(user.get().getPassword())) {
            throw new AccessDeniedException("Invalid username or password!");
        }
        return user.get();
    }

    public String createJwt(String email, String name, Date date) {
        date.setTime(date.getTime() + TOKEN_LIFESPAN);
        try {
            return jwtUtils.generateJwt(email, name, date);
        } catch (UnsupportedEncodingException e) {
            throw new AccessDeniedException("Invalid token parameters!", e);
        }
    }

    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) {
        try {
            String jwt = Optional.ofNullable(jwtUtils.getJwtFromHttpRequest(request))
                    .orElseThrow(() -> new UserNotLoggedException("User has not logged in! Login first!"));
            return jwtUtils.jwt2Map(jwt);
        } catch (UnsupportedEncodingException e) {
            throw new AccessDeniedException("Unable to verify JWT token!", e);
        }
    }

}
