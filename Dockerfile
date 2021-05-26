# Dockerfile to build and run this service locally without having maven or java installed

# Dockerfile standards are enforced by Hadolint
# Run hadolint with "docker run --rm -i hadolint/hadolint < Dockerfile" to ensure you are writing
# safe, clean and well structured dockerfiles that adhere to best practices.

# build container
FROM maven:3.6.3-adoptopenjdk-11 as build

RUN apt-get -yqq update && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p /opt/carlsberg/service/
RUN mkdir -p /opt/carlsberg/service/service-api/
RUN mkdir -p /opt/carlsberg/service/service-app/
WORKDIR /opt/carlsberg/service

ARG CX_NEXUS_USERNAME
ARG CX_NEXUS_PASSWORD

ENV CX_NEXUS_USERNAME $CX_NEXUS_USERNAME
ENV CX_NEXUS_PASSWORD $CX_NEXUS_PASSWORD

# cache the pom and get the deps
COPY .m2/m2settings.xml /root/.m2/settings.xml

# cache the pom and get the deps
COPY pom.xml ./
COPY service-api/pom.xml ./service-api/
COPY service-app/pom.xml ./service-app/
RUN mvn dependency:go-offline

# build code.
# used by git-commit-id-plugin
COPY .git/ ./.git/

COPY owasp-suppressions.xml ./

COPY service-api/owasp-suppressions.xml ./service-api/
COPY service-api/src/ ./service-api/src/

COPY service-app/owasp-suppressions.xml ./service-app/
COPY service-app/src/ ./service-app/src/

RUN mvn clean install -DskipTests

# runtime container
FROM cxconsumertech/alpine-adoptopenjdk11:jre-11.0.4_11
EXPOSE 8080
EXPOSE 8085

RUN mkdir -p /opt/carlsberg/service/
WORKDIR /opt/carlsberg/service/

COPY --from=build /opt/carlsberg/service/service-app/target/*.jar ./
COPY --from=build /opt/carlsberg/service/service-app/target/dependency/*.jar ./

USER nobody

ENTRYPOINT ["java","-javaagent:./io.projectreactor.reactor-tools-3.3.5.RELEASE.jar","-Djava.security.egd=file:/dev/urandom","-cp","./*", "com.carlsberg.consumertech.service.Application"]

