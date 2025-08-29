package com.neonets.Book.Security.jwt;

import com.neonets.Book.Security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;




@Service
public class TokenHelperService {
    @Value("${application.SECRET_KEY}")
    private String SECRET_KEY;

    @Value("${application.EXPIRATION_TIME}")
    private Integer EXPIRATION_TIME;

    Logger logger = LoggerFactory.getLogger(TokenHelperService.class);


    private Key signatureKey()
    {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateJjwt(CustomUserDetails customUserDetails, Map<String, Object> claims) {
        String username = customUserDetails.getUsername();
        return Jwts.builder()
                .setClaims(claims)
                .subject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signatureKey())
                .compact();
    }


    public Jws<Claims> parseJjwt(String token)
    {
        return Jwts.parser()
                .setSigningKey(signatureKey())
                .build()
                .parseSignedClaims(token);
    }

    public String getUsernameFromToken(String token)
    {
        return parseJjwt(token).getPayload().getSubject();
    }

    public Boolean  validateTokenExpiration(String token)
    {
        Date expiration = parseJjwt(token).getPayload().getExpiration();

        return  !expiration.before(new Date());
    }

}
