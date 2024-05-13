FROM eclipse-temurin:21-alpine AS build

COPY gradle ./gradle
COPY --chmod=765 gradlew build.gradle settings.gradle ./
COPY src/main ./src/main

RUN --mount=type=cache,target=/root/.gradle ./gradlew --build-cache --parallel --no-daemon clean bootJar

FROM eclipse-temurin:21-jre-alpine AS run

WORKDIR /opt/app

COPY --from=build /build/libs/mathmarathon-latest.jar app.jar

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
