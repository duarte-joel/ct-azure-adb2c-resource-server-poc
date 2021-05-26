package com.carlsberg.consumertech.service.spring.config.exception;

import com.carlsberg.consumertech.service.spring.config.exception.mappers.ThrowableExceptionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebExceptionHandler;

import java.util.List;

@Configuration
public class WebExceptionHandlingConfig {

    @Bean
    @Order(-2)
    // The handler must have precedence over WebFluxResponseStatusExceptionHandler and Spring Boot's ErrorWebExceptionHandler
    public WebExceptionHandler problemExceptionHandler(List<ExceptionMapper<?>> exceptionMappers, ThrowableExceptionMapper defaultExceptionHandler, ObjectMapper objectMapper) {
        return new GlobalWebExceptionHandler(exceptionMappers, defaultExceptionHandler, objectMapper);
    }

    @Bean
    public AddContextToRequestAttributesWebFilter addContextToRequestAttributesWebFilter() {
        return new AddContextToRequestAttributesWebFilter();
    }
}
