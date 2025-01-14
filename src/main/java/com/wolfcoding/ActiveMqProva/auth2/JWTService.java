package com.wolfcoding.ActiveMqProva.auth2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTService {

    private SecretKey key;

    private static final int EXPIRATION_TIME = 86400000;


    public JWTService() throws NoSuchAlgorithmException {
        // Sostituisci questa stringa con la chiave Base64 generata dal passo precedente
        String secretBase64 = keyGenerator();
        byte[] decodedKey = java.util.Base64.getDecoder().decode(secretBase64);
        this.key = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }

    private String keyGenerator() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
        keyGen.init(512); // Genera una chiave di 512 bit
        SecretKey secretKey = keyGen.generateKey();
        String encodedKey = java.util.Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Chiave Base64: " + encodedKey);
        return encodedKey;
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
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }


    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
