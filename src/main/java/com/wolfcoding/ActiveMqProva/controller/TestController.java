package com.wolfcoding.ActiveMqProva.controller;

import com.wolfcoding.ActiveMqProva.auth2.JWTService;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private JWTService jwtService;

    @PostMapping("/string")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getString() {
        return "Hello World!";
    }


    @PostMapping("/validate-token")
    public void validateTokenManually(@RequestParam String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtService.getKey())
                    .build()
                    .parseClaimsJws(token);
            logger.info("Token validato correttamente.");
        } catch (Exception e) {
            logger.error("Errore durante la validazione del token: {}", e.getMessage());
        }
    }
}
