package com.carlsberg.consumertech.service.spring.config.exception.mappers;

import com.carlsberg.consumertech.service.spring.config.exception.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.common.HttpStatusAdapter;

@Component
public class UnsupportedMediaTypeStatusExceptionMapper implements ExceptionMapper<UnsupportedMediaTypeStatusException> {

    @Override
    public Problem map(final UnsupportedMediaTypeStatusException throwable) {
        return Problem.builder()
                .withTitle("Unsupported Media Type")
                .withStatus(toStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
                .withDetail("Content type '" + throwable.getContentType() + "' not supported")
                .build();
    }

    private StatusType toStatus(final HttpStatus status) {
        return new HttpStatusAdapter(status);
    }
}
