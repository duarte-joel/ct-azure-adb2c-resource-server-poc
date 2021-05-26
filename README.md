# cx-consumertech-service-template

This repository will contain everything you need to create a new service. 

### The service template will:
- Start on port 8080
- Emit JSON formatted logs only to STDOUT
- Add a CorrelationId to every request or use one if it already exists
- Include standard CI/CD pipelines using Jenkins file
- Include a Makefile to build and test the service
- Include a docker-compose.yaml file to run the service locally with dependencies
- Serve the following responses on a different management port 8085 (which we don't want to expose to the public internet) 
    - 200 OK on /health/live (liveness probe that can be consumed by k8s)
    - 200 OK on /health/ready (readiness probe that can be consumed by k8s)  
    - 200 OK on /metrics with the prometheus format
  
### Service template folder structure

Service template is a multi-module maven project with the following modules:
- service-api
- service-app

# How to create a new service
  - Create a new repo based on this template (GitHub create from template)
  - Change the pom.xml files to reflect your new service name and dependencies.
  - Change the application.yaml to reflect your new service name.
  - Change the docker-compose.yml to reflect your new service needs.
  - Change the Jenkinsfile to reflect you new service name.
  - Change the SERVICE_NAME argument in the Makefile to reflect your new service name.
  - Change the DOCKER_RUN_ENV argument in the Makefile to reflect your new service environment arguments.
  - Change the banner.txt to reflect your new service name.
  - ADD STEPS THAT NEED TO BE TAKEN BEFORE DEPLOYING THE SERVICE IN AWS 
