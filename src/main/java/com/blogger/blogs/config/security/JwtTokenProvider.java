package com.blogger.blogs.config.security;

import java.util.Base64;
import java.util.Date;

import com.blogger.blogs.dto.UserContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 86400000; // 24 hours
    @Value("${jwt.secret}")
    private String secret;

    private String getEncodedSecret() {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }

    private Long getUserId(Claims claims) {
        return Long.parseLong(claims.getSubject());
    }

    private String getEmail(Claims claims) {
        return (String) claims.get("email");
    }

    private Claims getBody(String token) {
        return (Claims)Jwts.parser().setSigningKey(this.getEncodedSecret()).parse(token).getBody();
    }

    public UserContext getUserContext(String token) {
        try {
            Claims claims = (Claims) this.getBody(token);
            UserContext userDetail = new UserContext(this.getUserId(claims), this.getEmail(claims));
            return userDetail;
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

}