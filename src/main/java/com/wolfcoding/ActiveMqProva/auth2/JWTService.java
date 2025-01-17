package com.wolfcoding.ActiveMqProva.auth2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTService {

    private SecretKey key;

    private static final int EXPIRATION_TIME = 86400000;

    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);


    public JWTService(@Value("${jwt.secret}") String secretBase64) throws NoSuchAlgorithmException {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(secretBase64);
        this.key = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }


    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String extractUserName(String token) {
        logger.info("Token ricevuto: {}", token);
        String username = extractClaims(token, Claims::getSubject);
        String role = extractClaims(token, claims -> claims.get("role", String.class));
        logger.info("Ruolo estratto dal token: {}", role);
        return username;

    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            logger.info("Token verificato con successo. Claims: {}", claims);
            return claimsResolver.apply(claims);
        } catch (SignatureException e) {
            logger.error("Algoritmo di firma non corrisponde: {}", e.getMessage());
            throw new SignatureException("Algoritmo di firma errato", e);
        }

    }


    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public SecretKey getKey() {
        return key;
    }
}
