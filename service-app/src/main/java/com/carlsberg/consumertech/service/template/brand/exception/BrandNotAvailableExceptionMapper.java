package com.carlsberg.consumertech.service.template.brand.exception;

import com.carlsberg.consumertech.service.spring.config.exception.ExceptionMapper;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Component
public class BrandNotAvailableExceptionMapper implements ExceptionMapper<BrandNotAvailableException> {

    @Override
    public Problem map(final BrandNotAvailableException throwable) {
        return Problem.builder()
                .withTitle("Brand Not Available")
                .withStatus(Status.NOT_FOUND)
                .withDetail(throwable.getMessage())
                .build();
    }

}
