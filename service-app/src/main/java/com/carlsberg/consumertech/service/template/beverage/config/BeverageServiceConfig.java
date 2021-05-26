package com.carlsberg.consumertech.service.template.beverage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class BeverageServiceConfig {

    @Bean
    public BeverageServiceProperties beverageServiceProperties(@Value("${app.services.beverage.url}") final URI url) {
        return BeverageServiceProperties.builder()
                .url(url)
                .build();
    }
}