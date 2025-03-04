# Base image with OpenJDK 1.8
FROM openjdk:8-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package

# Create a new image for the runtime
FROM openjdk:8-jre-slim

# Set the working directory
WORKDIR /app

COPY target/study-3.10.0.war /app/study.war

ENTRYPOINT ["java", "-jar", "/app/study.war"]