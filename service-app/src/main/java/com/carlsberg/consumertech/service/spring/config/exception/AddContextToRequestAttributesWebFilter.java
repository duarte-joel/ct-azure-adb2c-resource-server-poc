package com.carlsberg.consumertech.service.spring.config.exception;

import lombok.NonNull;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Optional;

/**
 * Hack filter to be able to access context in the WebExceptionHandlers
 */
public class AddContextToRequestAttributesWebFilter implements WebFilter {
    public static final String REQUEST_ATTR_CONTEXT_KEY = "com.carlsberg.consumertech.hacks.request.attr.context";

    @Override
    public Mono<Void> filter(final ServerWebExchange serverWebExchange, final WebFilterChain chain) {
        return chain
                .filter(serverWebExchange)
                .subscriberContext(context -> {
                    serverWebExchange.getAttributes().put(REQUEST_ATTR_CONTEXT_KEY, context);
                    return context;
                });
    }

    public static Optional<Context> extractContextFromRequestAttributes(ServerWebExchange exchange) {
        return Optional.ofNullable(exchange.getAttribute(REQUEST_ATTR_CONTEXT_KEY));
    }
}