# 6. Health Checks

Date: 2020-04-30

## Status

Accepted

## Context

Kubernetes health checks are divided into liveness and readiness probes.
The purpose of liveness probes are to indicate that the application is running (does not need to be restarted), so a simple HTTP response should be enough.

A readiness probe is used to indicate whether the application can handle requests.
Readiness probes make sure that pods are not sent traffic in the time between when they start up,
and when they are ready to serve traffic.

https://www.ianlewis.org/en/using-kubernetes-health-checks

## Decision

Implement liveness probe as a simple rest handler endpoint `/health/live` that returns a simple `{"status":"UP"}` json.

Use spring-boot-actuator health endpoint as the readiness probe (and served from `/health/ready`) 
and implement custom `ReactiveHealthIndicator`s to check any downstream services and/or databases that 
will prevent the application from serving traffic. spring-boot-actuator already provides a database probe 
as long as the application has a data source configured.

These probes should be served in a management port (different from the port used to serve the application endpoints). 

## Consequences

All new services must provide liveness and readiness probes served from a management port (e.g. 8085).
Liveness probe can also be exposed to the public internet in the service main port (e.g. 8080).