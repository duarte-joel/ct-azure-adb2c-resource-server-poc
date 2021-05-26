package com.carlsberg.consumertech.service.template.brand;

import com.carlsberg.consumertech.service.template.brand.model.Brand;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Flux<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Mono<Brand> findBrandById(final String uuid) {
        return brandRepository.findById(uuid);
    }
}
