package com.carlsberg.consumertech.service.spring.config.exception;

import com.carlsberg.consumertech.core.logging.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.spring.webflux.advice.utils.AdviceUtils;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.carlsberg.consumertech.service.spring.config.exception.AddContextToRequestAttributesWebFilter.extractContextFromRequestAttributes;

@Slf4j
public class GlobalWebExceptionHandler implements WebExceptionHandler, ProblemHandling {

    private final Map<? extends Class<?>, ExceptionMapper<?>> exceptionToProblemMappers;
    private final ExceptionMapper<?> defaultExceptionMapper;
    private final ObjectMapper objectMapper;

    public GlobalWebExceptionHandler(final List<ExceptionMapper<?>> exceptionMappers,
                                     final ExceptionMapper<?> defaultExceptionMapper,
                                     final ObjectMapper objectMapper) {
        this.exceptionToProblemMappers = exceptionMappers.stream()
                .collect(Collectors.toMap(GlobalWebExceptionHandler::getExceptionMapperParameterizedType, Function.identity()));
        this.defaultExceptionMapper = defaultExceptionMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(final ServerWebExchange exchange, final Throwable throwable) {
        final Problem problem = findExceptionMapperForClass(throwable.getClass(), defaultExceptionMapper)
                .map(getExceptionWithProperType(throwable));

        return create(throwable, problem, exchange)
                .doOnEach(Log.onNext(x -> log.error(problem.toString(), throwable)))
                .flatMap(entity -> AdviceUtils.setHttpResponse(entity, exchange, objectMapper))
                .subscriberContext(extractContextFromRequestAttributes(exchange)
                        .orElse(Context.empty()));
    }

    //ugly hack to extract exception mapper parameterized type automatically; only used during the init.
    private static Class<?> getExceptionMapperParameterizedType(ExceptionMapper<?> exceptionMapper) {
        final Type exceptionMapperParamType = ((ParameterizedType)exceptionMapper.getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
        if (exceptionMapperParamType instanceof Class) {
            return (Class<?>) exceptionMapperParamType;
        } else {
            log.error("ExceptionMapper only supports exception classes as parameterized types: {}", exceptionMapper);
            throw new RuntimeException("ExceptionMapper only supports exception classes as parameterized types.");
        }
    }

    private ExceptionMapper<?> findExceptionMapperForClass(@Nullable Class<?> clazz, ExceptionMapper<?> defaultExceptionMapper) {
        if (clazz == null) {
            return defaultExceptionMapper;
        }
        final ExceptionMapper<?> exceptionMapper = exceptionToProblemMappers.get(clazz);
        if (exceptionMapper == null) {
            log.debug("Could not find exception mapper to {}.", clazz);
            return findExceptionMapperForClass(clazz.getSuperclass(), defaultExceptionMapper);
        }
        return exceptionMapper;
    }

    @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
    private <T extends Throwable> T getExceptionWithProperType(Throwable throwable) {
        return (T) throwable;
    }

}
