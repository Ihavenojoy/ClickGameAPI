# Use Maven with JDK 21 to build the app
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy all project files
COPY . .

# Build with Maven (skip tests for faster build)
RUN mvn clean package -DskipTests

# Use JRE base image for runtime (Java 21)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/api/target/*.jar app.jar

# Expose app port (adjust if needed)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
