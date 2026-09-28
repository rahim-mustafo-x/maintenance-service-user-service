# syntax=docker/dockerfile:1

################################################################################
# Dependencies
################################################################################

FROM eclipse-temurin:25-jdk-jammy AS deps

WORKDIR /build

COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -DskipTests


################################################################################
# Build
################################################################################

FROM deps AS package

WORKDIR /build

COPY src/ src/

RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate \
        -Dexpression=project.artifactId \
        -q \
        -DforceStdout)-$(./mvnw help:evaluate \
        -Dexpression=project.version \
        -q \
        -DforceStdout).jar target/app.jar


################################################################################
# Runtime
################################################################################

FROM eclipse-temurin:25-jre-jammy AS final

ARG UID=10001

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

WORKDIR /app

COPY --from=package /build/target/app.jar app.jar

USER appuser

EXPOSE 7879

ENTRYPOINT ["java", "-jar", "app.jar"]