package com.wolfcoding.ActiveMqProva.config;

import com.wolfcoding.ActiveMqProva.repository.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Component
public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UserRowMapper userRowMapper;


    @Autowired
    public CustomJdbcUserDetailsManager(DataSource dataSource, UserRowMapper userRowMapper) {
        super();
        this.setDataSource(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userRowMapper = userRowMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
            sqlParameters.addValue("username", username);
            return this.namedParameterJdbcTemplate.queryForObject("SELECT username, password, enabled FROM users WHERE username = :username", sqlParameters, userRowMapper);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Utente non trovato: " + username, e);
        }
    }
}
