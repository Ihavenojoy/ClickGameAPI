# Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /build

# Copy everything and build only the API module
COPY . .
RUN mvn clean install -pl api -am -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the Spring Boot executable jar
COPY --from=builder /build/api/target/api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
