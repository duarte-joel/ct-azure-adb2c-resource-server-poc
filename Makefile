#
# Sevice Template's Makefile
#
#

.POSIX:

#
# Maven Arguments
#

MAVEN=mvn
MAVEN_SKIP_TESTS?=false
MAVEN_FLAGS=-U

#
# Service Arguments
#
APPLICATION_ENVIRONMENT?=carlsberg-dev-local
SERVICE_NAME=cx-consumertech-service-template

#
# Docker Arguments
#
DOCKER_RUN_ENV=--env SPRING_PROFILES_ACTIVE --env SERVICE_DATABASE_URL --env SERVICE_DATABASE_ADMIN_USERNAME --env SERVICE_DATABASE_ADMIN_PASSWORD --env SERVICE_DATABASE_USERNAME --env SERVICE_DATABASE_PASSWORD --env SERVICE_BEVERAGES_URL
DOCKER_REGISTRY=$(APPLICATION_ENVIRONMENT)/$(SERVICE_NAME)
SHA_SUM=$$(git rev-parse --short HEAD)

#
# Standard Targets
#

all:
	$(MAVEN) compile $(MAVEN_FLAGS)

help:
	@echo 'Makefile for Service Template                                       '
	@echo 'Usage:                                                              '
	@echo '   make                Compile the project.                         '
	@echo '   make verify         Run the tests.                               '
	@echo '   make clean          Clear out caches and temporary artifacts.    '
	@echo '   make docker-dist    Build the project and create a docker image. '
	@echo '   make docker-run     Run a previously created docker image.       '

verify:
	$(MAVEN) verify $(MAVEN_FLAGS)

clean:
	$(MAVEN) clean $(MAVEN_FLAGS)

deps:
	$(MAVEN) dependency:go-offline

install:
	$(MAVEN) install -DskipTests=$(MAVEN_SKIP_TESTS) $(MAVEN_FLAGS)

ecr-login:
	eval $$(aws --region eu-west-1 ecr get-login --no-include-email)

docker-lint:
	docker run --rm -i hadolint/hadolint < ./service-app/Dockerfile

docker-dist: clean install docker-lint
	docker build -t $(DOCKER_REGISTRY):$(SHA_SUM) ./service-app/

docker-build: docker-lint
	docker build -t $(DOCKER_REGISTRY):$(SHA_SUM) ./service-app/

docker-run: docker-lint
	docker run $(DOCKER_RUN_ENV) -p 8085:8085 -p 8080:8080 $(DOCKER_REGISTRY):$(SHA_SUM)

docker-compose-up:
	docker-compose up --build -d

docker-compose-down:
	docker-compose down

dependency-check:
	$(MAVEN) org.owasp:dependency-check-maven:aggregate $(MAVEN_FLAGS)