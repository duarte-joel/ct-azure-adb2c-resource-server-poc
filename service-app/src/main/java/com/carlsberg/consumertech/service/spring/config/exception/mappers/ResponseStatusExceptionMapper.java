package com.carlsberg.consumertech.service.spring.config.exception.mappers;

import com.carlsberg.consumertech.service.spring.config.exception.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.common.HttpStatusAdapter;

@Component
public class ResponseStatusExceptionMapper implements ExceptionMapper<ResponseStatusException> {

    @Override
    public Problem map(final ResponseStatusException throwable) {
        final StatusType status = toStatus(throwable.getStatus());
        return Problem.builder()
                .withTitle(status.getReasonPhrase())
                .withStatus(status)
                .withDetail(throwable.getMessage())
                .build();
    }

    private StatusType toStatus(final HttpStatus status) {
        return new HttpStatusAdapter(status);
    }
}
