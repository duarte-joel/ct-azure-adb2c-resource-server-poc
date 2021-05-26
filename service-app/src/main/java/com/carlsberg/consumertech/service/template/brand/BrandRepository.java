package com.carlsberg.consumertech.service.template.brand;

import com.carlsberg.consumertech.service.template.brand.model.Brand;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface BrandRepository extends ReactiveCrudRepository<Brand, String> {

}
