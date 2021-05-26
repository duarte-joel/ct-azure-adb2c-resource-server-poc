# 2. Create Service Template

Date: 2020-04-30

## Status

Accepted

## Context

At ConsumerTech we are creating multiple new services. We want to share the same development and deployment 
workflows across all services to make the development faster and focus on solving problems that add business value, 
instead of repeatedly copying boilerplate code.

## Decision

Create a GitHub template repository that holds a basic service, with CI/CD pipelines. 
All common code and shared resources (like logging or database configuration) should be kept in separate projects 
to enhance modularity and independent evolution.  

## Consequences

All new services must use this repo to get started.
