# Use Maven to build the project
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use JRE base image to run the app
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built JAR from the builder
COPY --from=builder /app/target/*.jar app.jar

# Expose app port (change this if your app uses a different port)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
