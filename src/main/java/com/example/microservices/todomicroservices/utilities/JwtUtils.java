package com.example.microservices.todomicroservices.utilities;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    public String generateJwt(String email, String name, Date date) throws java.io.UnsupportedEncodingException {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, "myPersonalSecretKey12345".getBytes("UTF-8"))
                .compact();

    }


    public Map<String, Object> jwt2Map(String jwt) throws UnsupportedEncodingException {
        Jws<Claims> claim = Jwts.parser()
                .setSigningKey("myPersonalSecretKey12345".getBytes("UTF-8"))
                .parseClaimsJws(jwt);

        String name = claim.getBody().get("name", String.class);

        Date expDate = claim.getBody().getExpiration();
        String email = claim.getBody().getSubject();

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("name", name);
        userData.put("exp_date", expDate);

        Date now = new Date();
        if (now.after(expDate)) {
            throw new ExpiredJwtException(null, null, "Session expired!");
        }

        return userData;
    }

    public String getJwtFromHttpRequest(HttpServletRequest request) {
        String jwt = null;
        if (request.getHeader("jwt") != null) {
            jwt = request.getHeader("jwt");
        } else if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
            }
        }
        return jwt;
    }

}
