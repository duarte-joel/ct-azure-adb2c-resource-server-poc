package com.carlsberg.consumertech.service.spring.config.exception;

import lombok.NonNull;
import org.zalando.problem.Problem;

@FunctionalInterface
public interface ExceptionMapper<T extends Throwable> {
    Problem map(final T throwable);
}
