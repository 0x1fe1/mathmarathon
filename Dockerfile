FROM eclipse-temurin:21-alpine AS build

COPY src/main ./src/main
COPY --chmod=765 gradlew build.gradle settings.gradle ./
COPY gradle ./gradle

RUN ./gradlew --no-daemon --parallel -Porg.gradle.caching=false --no-build-cache --no-configuration-cache clean bootJar

FROM eclipse-temurin:21-jre-alpine AS run

WORKDIR /opt/app

COPY --from=build /build/libs/mathmarathon-latest.jar app.jar

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
