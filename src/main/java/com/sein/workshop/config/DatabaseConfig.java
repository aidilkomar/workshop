package com.sein.workshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Getter @Setter
    @Value("${spring.datasource.main.driver-class-name}")
    private String driver;

    @Getter @Setter
    @Value("${spring.datasource.main.url}")
    private String url;

    @Getter @Setter
    @Value("${spring.datasource.main.username}")
    private String username;

    @Getter @Setter
    @Value("${spring.datasource.main.password}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {

        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
}
