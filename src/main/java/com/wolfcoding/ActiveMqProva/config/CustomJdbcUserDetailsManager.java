package com.wolfcoding.ActiveMqProva.config;

import com.wolfcoding.ActiveMqProva.repository.UserRepository;
import com.wolfcoding.ActiveMqProva.repository.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CustomJdbcUserDetailsManager(DataSource dataSource, UserRowMapper userRowMapper) {
        super();
        this.setDataSource(dataSource);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findUserByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Utente non trovato: " + username, e);
        }
    }
}
