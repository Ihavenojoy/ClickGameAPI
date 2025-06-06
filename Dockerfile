# Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /build

COPY . .
RUN mvn clean install -pl api -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /build/api/target/*.jar app.jar
COPY .env /app/.env

ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--debug"]
