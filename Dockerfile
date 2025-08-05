# ================================================================================
# Dockerfile: Multi-Stage Build for Spring Boot Auth Service
# Author: Ochwada
# Date: 2025-08-05
# ================================================================================

# ========================
# 1. BUILD STAGE
# ========================
FROM eclipse-temurin:17-jdk AS builder

# Set working directory inside build container
WORKDIR /app

# Copy Maven wrapper scripts and config for better layer caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Pre-fetch dependencies
RUN ./mvnw dependency:go-offline

# Copy application source code
COPY src/ ./src/

# Build the application (skipping tests for faster build)
RUN ./mvnw clean package -DskipTests

# ========================
# 2. RUN STAGE
# ========================
FROM eclipse-temurin:17-jdk

# Set working directory inside runtime container
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 9099

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
