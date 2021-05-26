package com.carlsberg.consumertech.service.spring.config.exception.mappers;

import com.carlsberg.consumertech.service.spring.config.exception.ExceptionMapper;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Component
public class ThrowableExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Problem map(Throwable throwable) {
        return Problem.builder()
                .withTitle("Internal Server Error")
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .build();
    }
}
