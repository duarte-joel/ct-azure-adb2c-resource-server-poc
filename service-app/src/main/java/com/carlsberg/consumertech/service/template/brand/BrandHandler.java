package com.carlsberg.consumertech.service.template.brand;

import com.carlsberg.consumertech.core.logging.Log;
import com.carlsberg.consumertech.service.template.api.BrandDto;
import com.carlsberg.consumertech.service.template.brand.exception.BrandNotAvailableException;
import com.carlsberg.consumertech.service.template.brand.model.Brand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class BrandHandler {

    private final BrandService brandService;

    public BrandHandler(BrandService brandService) {
        this.brandService = brandService;
    }

    public Mono<ServerResponse> listAllBrands(ServerRequest request) {
        final var brandDtoFlux = brandService.getAllBrands()
                .map(this::toBrandDto);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(brandDtoFlux, BrandDto.class);
    }

    public Mono<ServerResponse> findOneBrandByID(ServerRequest request) {
        final String uuid = request.pathVariable("uuid");
        return brandService.findBrandById(uuid)
                .doOnEach(Log.onNext(brand -> log.info("Brand with UUID found: " + brand)))
                .doOnEach(Log.onError(throwable -> log.error("Brand not found.", throwable)))
                .flatMap(brand -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(brand))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> findBeveragesByBrandID(ServerRequest request) {
        return Mono.error(new BrandNotAvailableException());
    }

    private BrandDto toBrandDto(final Brand brand) {
        return BrandDto.builder()
                .uuid(brand.getUuid())
                .name(brand.getName())
                .build();
    }

}
