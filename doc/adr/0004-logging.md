# 4. Logging

Date: 2020-04-30

## Status

Accepted

## Context

We need to unify logging across different services and be able to correlate logs from the different services 
required to serve a request.

## Decision

Implement a java logging library that will provide common configuration and util classes to handle logging. 
This library should log only to stdout with a JSON format, provide a way of adding
MDC context and handle the generation and usage of a `correlationId` used to correlate logs from different services. 

## Consequences

All new services must use this library for logging. Special custom needs that only apply to a single service 
can be implemented directly in that service. Otherwise, they should be included in the library.
 
