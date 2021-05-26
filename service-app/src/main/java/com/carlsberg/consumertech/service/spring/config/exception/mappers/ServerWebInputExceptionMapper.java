package com.carlsberg.consumertech.service.spring.config.exception.mappers;

import com.carlsberg.consumertech.service.spring.config.exception.ExceptionMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebInputException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Component
public class ServerWebInputExceptionMapper implements ExceptionMapper<ServerWebInputException> {

    @Override
    public Problem map(final ServerWebInputException exception) {
        return Problem.builder()
                .withStatus(Status.UNPROCESSABLE_ENTITY)
                .withTitle("Invalid request")
                .withDetail("Unable to process payload")
                .build();
    }
}
