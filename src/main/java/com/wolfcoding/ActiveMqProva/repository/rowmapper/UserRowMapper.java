package com.wolfcoding.ActiveMqProva.repository.rowmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRowMapper implements RowMapper<UserDetails> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRowMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean enabled = rs.getBoolean("enabled");

        List<GrantedAuthority> authorities = getAuthorities(rs.getInt("id"));

        return User.builder()
                .username(username)
                .password(password)
                .disabled(!enabled)
                .authorities(authorities) // Aggiungi le autorità se necessario
                .build();
    }

    private List<GrantedAuthority> getAuthorities(int userId) {
        String query = "SELECT a.name FROM authorities a " +
                "JOIN testdb.user_authorities ua ON a.id = ua.authority_id " +
                "WHERE ua.user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) ->
                new SimpleGrantedAuthority(rs.getString("name"))
        );
    }

}

