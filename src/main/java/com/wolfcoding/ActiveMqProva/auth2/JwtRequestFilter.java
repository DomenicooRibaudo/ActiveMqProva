package com.wolfcoding.ActiveMqProva.auth2;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);


    @Autowired
    private JWTService service;


    public JwtRequestFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Estrarre il token dall'header "Authorization"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Rimuove "Bearer " dall'inizio
            try {
                // Estrai il nome utente dal token
                username = service.extractUserName(jwt);

                // Estrai il ruolo dal token
                String role = service.extractClaims(jwt, claims -> claims.get("role", String.class));
                logger.info("Ruolo estratto dal token: {}", role);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (!service.isTokenExpired(jwt)) {
                        // Crea l'autenticazione con il ruolo corretto
                        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (Exception e) {
                logger.error("Errore durante l'elaborazione del token JWT: {}", e.getMessage());
            }
        }

        // Continua con il prossimo filtro nella catena
        filterChain.doFilter(request, response);
    }


}
