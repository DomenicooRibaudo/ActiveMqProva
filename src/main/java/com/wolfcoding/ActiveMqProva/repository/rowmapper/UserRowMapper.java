package com.wolfcoding.ActiveMqProva.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class UserRowMapper implements RowMapper<UserDetails> {

    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean enabled = rs.getBoolean("enabled");

        return User.builder()
                .username(username)
                .password(password)
                .disabled(!enabled)
                .authorities(new ArrayList<>()) // Aggiungi le autorità se necessario
                .build();
    }
}

