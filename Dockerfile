#=================================================================================
#  Dockerfile: Multi-Stage Build for Spring Boot Application
#
#  Author: Ochwada
#  Date: 2025-08-05
#
#  *** For AUTH Services ***
#=================================================================================

# ========================
# 1. BUILD STAGE
# Heavyweight stage using Maven wrapper to compile the app.
# ========================

# Use Eclipse Temurin JDK 17 as base image for building
FROM eclipse-temurin:17-jdk AS builder

  # Set the working directory inside the build container
WORKDIR /app

  # Copy Maven wrapper scripts and metadata first
  # Helps leverage Docker layer caching when dependencies haven't changed
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

  # Pre-fetch all dependencies for offline build
  RUN ./mvnw dependency:go-offline/

  # Copy the actual source code after dependencies to improve cache efficiency
COPY src ./src

  # Build the application using Maven wrapper
  # Skipping tests for faster build during development
  RUN ./mvnw clean package -DskipTests/

# ========================
# 2. RUN STAGE
# Lightweight runtime stage using only the JDK
# ========================

# Use Eclipse Temurin JDK 17 for running the built app
FROM eclipse-temurin:17-jdk

  # Set the working directory inside the runtime container
WORKDIR /app

  # Copy only the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

  # Expose the port your Spring Boot app will run on (change if needed)
EXPOSE 9080

  # Define the startup command for the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
