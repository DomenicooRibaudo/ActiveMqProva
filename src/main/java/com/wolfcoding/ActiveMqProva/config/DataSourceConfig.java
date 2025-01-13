//package com.wolfcoding.ActiveMqProva.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import javax.sql.DataSource;
//@Configuration
//public class DataSourceConfig {
//    @Value("${datasource.url}")private String url;
//    @Value("${datasource.username}")private String username;
//    @Value("${datasource.password}")private String password;
//    @Value("${datasource.driver-class-name}")private String driverClassName;
//
//
//    @Bean
//    public DataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(this.url);
//        config.setUsername(this.username);
//        config.setPassword(this.password);
//        config.setDriverClassName(this.driverClassName);
//        return new HikariDataSource(config);
//    }
//
//    @Bean
//    public NamedParameterJdbcTemplate getJdbcTemplate() {
//        return new NamedParameterJdbcTemplate(dataSource());  // using the datasource bean here
//    }
//
//}
