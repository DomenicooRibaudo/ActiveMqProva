package com.wolfcoding.ActiveMqProva.controller;

import com.wolfcoding.ActiveMqProva.auth2.JWTService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        logger.info("Tentativo di login per l'utente: {}", username);

        // Ottieni i dettagli dell'utente autenticato
        UserDetails userDetails = (UserDetails) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        ).getPrincipal();
        logger.info("Utente autenticato con successo: {}", userDetails.getUsername());

        String token = jwtService.generateToken(userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority());
        logger.info("Token JWT generato: {}", token);

        // Restituisci il token
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }


}


