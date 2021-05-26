# 5. Relational Database Access

Date: 2020-04-30

## Status

Accepted

## Context

Some services will need to store and fetch data from relational databases. 
Using JPA (or Hibernate) which is based on JDBC will turn the data access layer blocking 
and that comes with several drawbacks that neglect the benefits of a reactive programming model. 
Moreover, most times services will only need to fetch and store simple data, without the need for complex queries 
or to retrieve a huge graph of data. In those cases using JPA is overkill and adds performance penalties.

## Decision

Use MySQL as the relational database of choice.

Use Spring Data R2DBC as the primary object mapper to access database data. R2DBC provides reactive drivers to 
access MySQL turning the data layer non-blocking.

Implement a java database-r2dbc-starter library that should provide common configuration and util classes to start with
R2DBC. 


## Consequences

All new services that need to access relational databases should use 
[Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc) 
and include the [database-r2dbc-starter](https://github.com/CarlsbergGBS/cx-consumertech-java-commons) library as 
a dependency.

If there is a strong reason to use JPA the service will need to provide all boilerplate to adapt to the JDBC 
blocking nature.
