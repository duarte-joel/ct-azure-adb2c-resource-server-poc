package com.carlsberg.consumertech.service.spring.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(FlywayAutoConfiguration.class)
public class FlywayConfig {

    //use a custom bean to avoid loading spring-jdbc which is required with auto-config
    @Bean(initMethod = "migrate")
    @ConditionalOnProperty(name = "spring.flyway.url")
    public Flyway flyway(
        @Value("${spring.flyway.url}") final String url,
        @Value("${spring.flyway.user}") final String username,
        @Value("${spring.flyway.password}") final String password,
        @Value("${spring.flyway.locations}") final String locations
    ) {
        return Flyway.configure()
            .dataSource(url, username, password)
            .locations(locations)
            .load();
    }
}
