package com.carlsberg.consumertech.service.template.beverage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class BeverageRouter {

    @Bean
    public RouterFunction<ServerResponse> beverageRoutes(BeverageHandler beverageHandler) {
        return RouterFunctions.route()
                .nest(accept(MediaType.APPLICATION_JSON), builder -> builder
                        .GET("/brands/{uuid}/beverages/check", beverageHandler::checkBeverages))
                .build();
    }

}
