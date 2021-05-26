package com.carlsberg.consumertech.service.template.beverage;

import com.carlsberg.consumertech.core.logging.Log;
import com.carlsberg.consumertech.core.logging.correlation.CorrelationId;
import com.carlsberg.consumertech.service.template.beverage.config.BeverageServiceProperties;
import com.carlsberg.consumertech.service.template.brand.exception.BrandNotAvailableException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class BeverageHandler {

    private final WebClient webClient;
    private final CorrelationId correlationId;

    public BeverageHandler(BeverageServiceProperties beverageServiceProperties,
                           WebClient.Builder webClientBuilder,
                           CorrelationId correlationId
    ) {
        this.webClient = webClientBuilder
                .baseUrl(beverageServiceProperties.getUrl().toString())
                .build();
        this.correlationId = correlationId;
    }

    public Mono<ServerResponse> checkBeverages(ServerRequest request) {
        return httpGet("/brands/u1")
                .flatMap(spec -> spec
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, err -> Mono.error(new BrandNotAvailableException()))
                        .bodyToMono(String.class))
                .flatMap(response -> ok().bodyValue(response))
                .doOnEach(Log.onNext(response -> log.info("Beverage service response: {} ", response.statusCode())));
    }

    private Mono<WebClient.RequestHeadersSpec<?>> httpGet(final String uri) {
        return Mono.just(webClient.get().uri(uri))
                .flatMap(correlationId::forward);
    }
}
