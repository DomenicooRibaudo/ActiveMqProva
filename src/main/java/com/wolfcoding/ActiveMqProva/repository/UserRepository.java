package com.wolfcoding.ActiveMqProva.repository;

import com.wolfcoding.ActiveMqProva.repository.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRowMapper userRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    public UserDetails findUserByUsername(String username) {
        String query = "SELECT id, username, password, enabled FROM users WHERE username = :username";
        MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
        sqlParameters.addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(query, sqlParameters, userRowMapper);
    }
}
