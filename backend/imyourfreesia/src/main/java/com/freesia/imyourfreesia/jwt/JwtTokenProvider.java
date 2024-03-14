package com.freesia.imyourfreesia.jwt;

import com.freesia.imyourfreesia.config.GlobalConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final String secret;
    private final int expirationTime;

    public JwtTokenProvider(GlobalConfig config) {
        this.secret = config.getSecret();
        this.expirationTime = config.getExpirationTime() * 1000;
    }

    public String generateToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(AUTHORIZATION_HEADER, accessToken);
    }
}
