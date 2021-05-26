# 3. Adopt Reactive Spring WebFlux

Date: 2020-04-30

## Status

Accepted

## Context

ConsumerTech services are latency-sensitive and need to serve a lot of concurrent requests.
We need to use a framework which handles low-latency and high-throughput workloads in an efficient manner 
(do more work with less resources). 
Traditional one thread per request, blocking I/O frameworks, like Spring MVC, don't scale well in these scenarios.
  

## Decision

Adopt Spring WebFlux, a non-blocking I/O framework, to build our new services. 

## Consequences

All new services should be built using Spring WebFlux. 
For getting started check [Spring Reactive](https://spring.io/reactive).  