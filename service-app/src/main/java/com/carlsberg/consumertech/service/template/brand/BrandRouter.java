package com.carlsberg.consumertech.service.template.brand;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class BrandRouter {

    @Bean
    public RouterFunction<ServerResponse> brandRoutes(BrandHandler brandHandler) {
        return RouterFunctions.route()
                .nest(accept(MediaType.APPLICATION_JSON), builder -> builder
                        .GET("/brands", brandHandler::listAllBrands)
                        .GET("/brands/{uuid}", brandHandler::findOneBrandByID)
                        .GET("/brands/{uuid}/beverages", brandHandler::findBeveragesByBrandID))
                .build();
    }

}
